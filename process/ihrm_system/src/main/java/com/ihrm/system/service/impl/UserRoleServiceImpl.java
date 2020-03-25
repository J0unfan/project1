package com.ihrm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ihrm.system.dao.UserRoleDao;
import com.ihrm.system.entity.UserRole;
import com.ihrm.system.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao, UserRole> implements UserRoleService {
    @Autowired
    private UserRoleDao userRoleDao;// 用户-角色关联表

    @Override
    public int deleteBatch(int[] roleIds) {
        return baseMapper.deleteBatch(roleIds);
    }

    @Override
    public boolean saveUserRole(Map<String, Object> params) {
        Integer userId = Integer.valueOf(params.get("userId").toString());
        List<Integer> roleIds = (List<Integer>) params.get("roleIds");
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        for(int roleId : roleIds){
            userRole.setRoleId(roleId);
            save(userRole);
        }
        return true;
    }

    @Override
    public boolean updateUserRole(Map<String, Object> params) {
        Integer userId = Integer.valueOf(params.get("userId").toString());
        List<Integer> roleIds = (List<Integer>) params.get("roleIds");
        remove(new QueryWrapper<UserRole>().eq("user_id", userId));
        UserRole userRole = new UserRole();
        userRole.setUserId(userId);
        for(int roleId : roleIds){
            userRole.setRoleId(roleId);
            save(userRole);
        }
        return true;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    /**
     * 插入用户-角色关联表
     * @param userId
     * @param roleIds
     * @return
     */
    @Override
    public int insertRelUserRole(Integer userId, List<Integer> roleIds) {
        return userRoleDao.insertRelUserRole(userId, roleIds);
    }

    /**
     * 根据userId删除关联关系
     * @param userId
     * @return
     */
    @Override
    public int deleteByUserId(Integer userId) {
        return userRoleDao.deleteByUserId(userId);
    }
}
