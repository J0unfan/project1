package com.ihrm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ihrm.common.entity.Result;
import com.ihrm.system.entity.Privilege;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

public interface PrivilegeService extends IService<Privilege> {
    /*删除菜单及其子节点*/
    public Result delete(int id);

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     * @param menuIdList  用户菜单ID
     */
    List<Privilege> queryListParentId(int parentId, List<Integer> menuIdList);

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     */
    List<Privilege> queryListParentId(int parentId);

    /**
     * 获取用户菜单列表
     */
    List<Map<String,Object>> getUserMenuList(Long userId);


    Result queryPage(Map<String,Object> params);

    /*获取权限树*/
    List<Privilege> privilegeTree();

    /**
     * 根据角色id查询所有的权限
     * @param roleIds
     * @return
     */
    List<Privilege> findAlPrivilegeByUserId(List<Integer> roleIds);
}
