package com.ihrm.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ihrm.system.entity.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserRoleDao extends BaseMapper<UserRole> {
    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatch(int[] roleIds);

    /**
     * 根据用户id查询所有的权限
     * @return
     */
    List<String> findAllPermsByUserId(String userId);

    /**
     * 插入用户-角色关联表
     * @param userId
     * @param roleIds
     * @return
     */
    int insertRelUserRole(@Param("userId") Integer userId, @Param("roleIds") List<Integer> roleIds);

    /**
     * 根据用户id删除
     * @param userId
     * @return
     */
    int deleteByUserId(Integer userId);
}
