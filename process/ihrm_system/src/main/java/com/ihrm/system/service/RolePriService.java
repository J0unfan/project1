package com.ihrm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ihrm.system.entity.RolePri;

import java.util.List;
import java.util.Map;

public interface RolePriService extends IService<RolePri> {
    /**
     * 根据角色ID，获取菜单ID列表
     */
    List<Integer> findAllPriByRoleId(String roleId);

    /**
     * 根据角色ID数组，批量删除
     */
    int deleteBatch(int[] roleIds);

    /**
     * 修改或者保存
     * @param params
     * @return
     */
    boolean saveOrUpdate(Map<String,Object> params);

    /**
     * 修改角色权限关系
     * @return
     */
    boolean update(int roleId,List<Integer> priIds);
}
