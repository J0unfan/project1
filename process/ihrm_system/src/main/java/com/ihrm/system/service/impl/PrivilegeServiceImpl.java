package com.ihrm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.system.dao.PrivilegeDao;
import com.ihrm.system.entity.Privilege;
import com.ihrm.system.entity.RolePri;
import com.ihrm.system.entity.User;
import com.ihrm.system.service.PrivilegeService;
import com.ihrm.system.service.RolePriService;
import com.ihrm.system.utils.PageQueryUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PrivilegeServiceImpl extends ServiceImpl<PrivilegeDao, Privilege> implements PrivilegeService {

    @Autowired
    private RolePriService rolePriService;
    @Autowired
    private PrivilegeDao privilegeDao;//权限相dao

    @Override
    public Result delete(int id) {
        rolePriService.remove(new QueryWrapper<RolePri>().eq("pri_id", id));
        this.removeById(id);
        baseMapper.deleteByParentId(id);
        return new Result(ResultCode.SUCCESS);
    }

    @Override
    public List<Privilege> queryListParentId(int parentId, List<Integer> menuIdList) {
        List<Privilege> menuList = queryListParentId(parentId);
        if (menuIdList == null) {
            return menuList;
        }

        List<Privilege> userMenuList = new ArrayList<>();
        for (Privilege menu : menuList) {
            if (menuIdList.contains(menu.getId())) {
                userMenuList.add(menu);
            }
        }
        return userMenuList;
    }

    @Override
    public List<Privilege> queryListParentId(int parentId) {
        return baseMapper.queryListParentId(parentId);
    }

    /**
     * 获取部门树
     * 根据叶子节点反向查询树结构
     *
     * @param userId
     * @return
     */
    @Override
    public List<Map<String, Object>> getUserMenuList(Long userId) {
        // 获取用户的角色
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        List<Privilege> privilegeList = privilegeDao.findAlPrivilegeByRoleIds(user.getRoles());
        List<Map<String, Object>> nodeList = new ArrayList<>();

        // 将每个单个的privilege对象转换成树
        for (Privilege p : privilegeList) {
            Map<String, Object> singleMap = new HashMap<>();
            singleMap.put("id", p.getId());
            singleMap.put("parentId", p.getParentId());
            singleMap.put("name", p.getName());
            singleMap.put("path", p.getPath());
            Map<String, Object> node = getNodeTree(singleMap);
            nodeList.add(node);
        }
        // nodeList只获取二级菜单，同时去除重复
        List<Map<String, Object>> maps1 = cleanerSame(nodeList);
        List<Map<String, Object>> maps = secondTreeConstractor(maps1);
        System.out.println(maps);
        // 根据用户id查询该用户所有的权限
//        List<Long> menuIdList = userService.queryMenuId(userId);
        // 递归找出每一节点id是否有父节点
//        return getAllMenuList(menuIdList);
        return maps;
    }

    /**
     * 去除重复
     */
    private List<Map<String, Object>> cleanerSame(List<Map<String, Object>> nodeList) {
        List<Map<String, Object>> newList = new ArrayList<>();
        List<String> existList = new ArrayList<>();
        for (Map<String, Object> m : nodeList) {
            List<Map<String, Object>> tempList = (List<Map<String, Object>>) m.get("children");
            if (tempList != null) {
                Map<String, Object> tempMap = tempList.get(0);
                if (tempMap != null) {
                    String id = tempMap.get("id").toString();
                    if (id != null && existList.contains(id)) {
                        continue;
                    } else if (id != null) {
                        existList.add(id);
                        newList.add(m);
                    }
                }
            }
        }
        return newList;
    }

    /**
     * 根据节点id，递归查询到父节点 id=0
     */
    public Map<String, Object> getNodeTree(Map<String, Object> pmap) {
        Map<String, Object> map = new HashMap<>();
        String parentId = pmap.get("parentId").toString();
        if (parentId != null && !"0".equals(parentId)) {
            // 根据节点id查询父节点
            Privilege parentPrivilege = privilegeDao.selectById(parentId);
            map.put("id", parentPrivilege.getId());
            map.put("name", parentPrivilege.getName());
            map.put("parentId", parentPrivilege.getParentId());
            map.put("path", parentPrivilege.getPath());
            if (parentPrivilege.getPath() == null || "".equals(parentPrivilege.getPath())) {
                List<Map<String, Object>> l = new ArrayList<>();
                l.add(pmap);
                map.put("children", l);
            }
            return getNodeTree(map);
        } else {
            return pmap;
        }
    }

    /**
     * 生成二级树
     *
     * @param nodeList
     */
    private List<Map<String, Object>> secondTreeConstractor(List<Map<String, Object>> nodeList) {
        List<Map<String, Object>> newMapList = new ArrayList<>();

        for (Map<String, Object> node : nodeList) {
            Map<String, Object> map = isContains(newMapList, node);
            if (map != null) {// 存在该节点
                // 获取node2中的children 节点
                List<Map<String, Object>> children = (List<Map<String, Object>>) map.get("children");
                children.addAll((List<Map<String, Object>>) node.get("children"));
            } else {
                newMapList.add(node);
            }
        }
        return newMapList;
    }

    public Map<String, Object> isContains(List<Map<String, Object>> newMapList, Map<String, Object> node) {
        for (Map<String, Object> node2 : newMapList) {
            if (node.get("id").toString().equals(node2.get("id").toString())) {// 节点存在
                return node2;
            }
        }
        return null;
    }

    /**
     *  分页查询
     * @param params
     * @return
     */
    @Override
    public Result queryPage(Map<String, Object> params) {
        PageQueryUtils init = PageQueryUtils.init(params);
        try {
            List<Privilege> privileges = baseMapper.queryPage(init.getQuery(), init.getPageNum(), init.getPageSize());
            return new Result(ResultCode.SUCCESS, privileges);
        } catch (Exception e) {
            return new Result(ResultCode.FAIL, null);
        }
    }

    @Override
    public List<Privilege> privilegeTree() {
        return getAllMenuList(null);
    }

    /**
     * 根据角色id查询所有的权限
     *
     * @param roleIds
     * @return
     */
    @Override
    public List<Privilege> findAlPrivilegeByUserId(List<Integer> roleIds) {
//        return privilegeDao.findAlPrivilegeByUserId(roleIds);
        return null;
    }

    /*根据菜单id查询菜单*/
    public List<Privilege> queryById(List<Long> privilegeList) {
        List<Privilege> privileges = new ArrayList<Privilege>();
        for (Long id : privilegeList) {
            Privilege privilege = this.getById(id);
            privileges.add(privilege);
        }
        return privileges;
    }

    /**
     * 获取所有菜单列表
     */
    private List<Privilege> getAllMenuList(List<Integer> menuIdList) {
        //查询根菜单列表
        List<Privilege> menuList = queryListParentId(0, menuIdList);
        //递归获取子菜单
        getMenuTreeList(menuList, menuIdList);
        return menuList;
    }

    /**
     * 递归
     */
    private List<Privilege> getMenuTreeList(List<Privilege> menuList, List<Integer> menuIdList) {
        List<Privilege> subMenuList = new ArrayList<Privilege>();

        for (Privilege entity : menuList) {
            entity.setChildren(getMenuTreeList(queryListParentId(entity.getId(), menuIdList), menuIdList));

            subMenuList.add(entity);
        }

        return subMenuList;
    }

}
