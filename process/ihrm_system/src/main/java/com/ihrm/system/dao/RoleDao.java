package com.ihrm.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ihrm.system.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleDao extends BaseMapper<Role> {
    List<Role> queryPage(String name, int page, int limit);

    //根据用户id查询该用户的权限
    List<Integer> findAllRoleIdByUserId(Integer userId);
}
