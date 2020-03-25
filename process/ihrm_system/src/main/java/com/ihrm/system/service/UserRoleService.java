package com.ihrm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ihrm.system.entity.UserRole;

import java.util.List;
import java.util.Map;

public interface UserRoleService extends IService<UserRole> {
    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatch(int[] roleIds);

    boolean saveUserRole(Map<String,Object> params);

    boolean updateUserRole(Map<String,Object> params);

    boolean delete(int id);
    /**
     * 插入用户-角色关联表
     */
    int insertRelUserRole(Integer userId,List<Integer> roleIds);

    /**
     * 根据用户删除关联关系
     */
    int deleteByUserId(Integer userId);
}
