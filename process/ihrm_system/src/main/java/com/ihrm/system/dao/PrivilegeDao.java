package com.ihrm.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ihrm.system.entity.Privilege;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PrivilegeDao extends BaseMapper<Privilege> {
    /*根据父id删除子节点*/
    int deleteByParentId(int parentId);

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     */
    List<Privilege> queryListParentId(int parentId);

    List<Privilege> queryPage(String name,int page, int limit);

    /**
     * 根据角色id集合查询所有的权限
     * @param roleIds
     * @return
     */
    List<Privilege> findAlPrivilegeByRoleIds(@Param("roleIds") List<Integer> roleIds);
}
