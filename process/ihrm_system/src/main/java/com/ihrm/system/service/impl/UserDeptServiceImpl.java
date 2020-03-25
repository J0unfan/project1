package com.ihrm.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ihrm.system.dao.UserDeptDao;
import com.ihrm.system.entity.UserDept;
import com.ihrm.system.service.UserDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDeptServiceImpl extends ServiceImpl<UserDeptDao, UserDept> implements UserDeptService {
    @Autowired
    private UserDeptDao userDeptDao;// 角色-部门关联表

    @Override
    public int insertUserDept(Integer userId, List<Integer> deptIds) {
        return userDeptDao.insertUserDept(userId,deptIds);
    }

    /**
     * 根据userId删除关联关系表
     * @param userId
     * @return
     */
    @Override
    public int deleteByUserId(Integer userId) {
        return userDeptDao.deleteByUserId(userId);
   }

}
