package com.ihrm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.system.dao.DeptDao;
import com.ihrm.system.entity.Dept;
import com.ihrm.system.entity.DeptTree;
import com.ihrm.system.entity.RolePri;
import com.ihrm.system.entity.UserDept;
import com.ihrm.system.service.DeptService;
import com.ihrm.system.service.UserDeptService;
import com.ihrm.system.utils.PageQueryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.ws.Action;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeptServiceImpl extends ServiceImpl<DeptDao, Dept> implements DeptService {
    @Autowired
    private UserDeptService userDeptService;

    @Override
    public Result queryPage(Map<String, Object> params) {
        PageQueryUtils init = PageQueryUtils.init(params);
        try {
            List<Dept> depts = baseMapper.queryPage(init.getQuery(),init.getPageNum(),init.getPageSize());
            return new Result(ResultCode.SUCCESS, depts);
        } catch (Exception e) {
            return new Result(ResultCode.FAIL, null);
        }
    }

    @Override
    public List<DeptTree> queryByParentId(int parentId) {
        return baseMapper.queryByParentId(parentId);
    }

    @Override
    public List<Map<String, Object>> queryAllDeptList() {
        return getAllDept();
    }

    @Override
    public Result delete(int id) {
        try{
            //先删除关联表
            userDeptService.remove(new QueryWrapper<UserDept>().eq("dept_id", id));
            this.removeById(id);
            return new Result(ResultCode.SUCCESS);
        }catch (Exception e){
            return new Result(ResultCode.FAIL,"请重试");
        }
    }

    /**
     * 获取部门树
     *
     * @return
     */
    List<Map<String, Object>> getAllDept() {
        List<DeptTree> depts = queryByParentId(0);
        List<Map<String, Object>> tree = getDeptTreeList(depts);
        return tree;
    }

    /*递归*/
    private List<Map<String, Object>> getDeptTreeList(List<DeptTree> deptList) {
        List<Map<String, Object>> list = new ArrayList<>();

        for (int i = 0; i < deptList.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            DeptTree temp = deptList.get(i);
            map.put("id", temp.getId());// 设置id
            map.put("label", temp.getName());// 设置name
            map.put("isParent",temp.getIsParent());// 是否是父部门
//            map.put("isParent",temp.get)
            List<DeptTree> deptTrees = queryByParentId(temp.getId());
            if (deptTrees.isEmpty() || deptTrees == null) {
                list.add(map);
                continue;
            }
            map.put("children", getDeptTreeList(deptTrees));
            list.add(map);
        }
        return list;
    }

}
