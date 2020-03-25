package com.ihrm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.system.dao.UserDao;
import com.ihrm.system.entity.RolePri;
import com.ihrm.system.entity.User;
import com.ihrm.system.entity.UserDept;
import com.ihrm.system.entity.UserRole;
import com.ihrm.system.service.UserDeptService;
import com.ihrm.system.service.UserRoleService;
import com.ihrm.system.service.UserService;
import com.ihrm.system.utils.PageQueryUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {
    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserDeptService userDeptService;

    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    @Override
    public Result queryPage(Map<String, Object> params) {
        PageQueryUtils init = PageQueryUtils.init(params);
        try {
            List<User> users = baseMapper.queryPage(init.getQuery(), init.getPageNum(), init.getPageSize());
            // 获取部门集合、角色集合
            for (User user : users) {
                List<Integer> depts = baseMapper.findAllDeptByUserId(user.getId() + "");
                List<Integer> roles = baseMapper.findAllRoleByUserId(user.getId() + "");
                user.setDepts(depts);
                user.setRoles(roles);
            }
            Result result = new Result(ResultCode.SUCCESS, users);
            result.setTotal(baseMapper.count() == null ? 0 : baseMapper.count());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(ResultCode.FAIL, null);
        }
    }


    @Override
    public List<Integer> queryMenuId(int userId) {
        return baseMapper.queryAllMenuId(userId);
    }

    /**
     * 修改用户密码
     *
     * @param userId
     * @param password
     * @param newPassword
     * @return
     */
    @Override
    public boolean updatePassword(int userId, String password, String newPassword) {
        password = new Md5Hash(password, "123", 3).toString();
        newPassword = new Md5Hash(newPassword, "123", 3).toString();
        User user = new User();
        user.setPassword(newPassword);
        return this.update(user,
                new QueryWrapper<User>().eq("id", userId).eq("password", password));
    }

    /**
     * 增加或者修改
     *
     * @param user
     * @return
     */
    @Override
    @Transactional
    public Result saveOrUpdateUser(User user) {
        boolean flag = false;
        if (user.getId() == null) {// 增加新的用户
            // 判断用户是否存在
            User user1 = baseMapper.queryByPhone(user.getPhone());
            if (user1 != null) {
                return new Result(ResultCode.FAIL, "电话号码已存在");
            }
            user.setPassword(new Md5Hash("123456", "123", 3).toString());
            flag = true;
        }
        saveOrUpdate(user);
        // 关联表数据的更新
        List<Integer> depts = user.getDepts();
        if (depts != null && !depts.isEmpty()) {// 更新部门表
            if (!flag) {// 增加用户
                // 删除关联表
                userDeptService.deleteByUserId(user.getId());
            }
            // 插入用户-部门关联表
            userDeptService.insertUserDept(user.getId(), user.getDepts());
        }
        // 角色关联表
        List<Integer> roles = user.getRoles();
        if (roles != null && !roles.isEmpty()) {// 更新角色表
            if (!flag) {// 增加用户
               userRoleService.deleteByUserId(user.getId());
            }
            // 插入用户-角色关联表
            userRoleService.insertRelUserRole(user.getId(), user.getRoles());
        }
        return new Result(ResultCode.SUCCESS);
    }

    /*
     * 删除用户及其关联表
     * */
    @Override
    public Result deleteRel(int id) {
        try {
            userDeptService.remove(new QueryWrapper<UserDept>().eq("user_id", id));
            userRoleService.remove(new QueryWrapper<UserRole>().eq("user_id", id));
            removeById(id);
            return new Result(ResultCode.SUCCESS);
        } catch (Exception e) {
            return new Result(ResultCode.FAIL);
        }
    }

    @Override
    public Result queryByUserId(int userId) {
        try {
            User user = this.getById(userId);
            if (user == null) {
                return new Result(ResultCode.FAIL, "查无此人");
            }
            List<Integer> depts = baseMapper.findAllDeptByUserId(user.getId() + "");
            List<Integer> roles = baseMapper.findAllRoleByUserId(user.getId() + "");
            user.setDepts(depts);
            user.setRoles(roles);
            return new Result(ResultCode.SUCCESS, user);
        } catch (Exception e) {
            return new Result(ResultCode.FAIL, "请重试");
        }
    }
}
