package com.ihrm.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ihrm.system.entity.RolePri;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RolePriDao extends BaseMapper<RolePri> {
    /**
     * 根据角色ID，获取菜单ID列表
     */
    List<Integer> queryMenuIdList(String roleId);

    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatch(int[] roleIds);

    /**
     * 根据角色id查询所有的权限id
     * @param roleId 角色id
     * @return
     */
    List<Integer> findAllPriByRoleId(String roleId);
}
