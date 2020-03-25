package com.ihrm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ihrm.common.entity.Result;
import com.ihrm.system.entity.Dept;
import com.ihrm.system.entity.DeptTree;

import java.util.List;
import java.util.Map;

public interface DeptService extends IService<Dept> {
    Result queryPage(Map<String,Object> params);

    /*根据父id查询子部门*/
    List<DeptTree> queryByParentId(int parentId);

    /*查询所有部门*/
    List<Map<String,Object>> queryAllDeptList();

    Result delete(int id);//删除部门及其关联表
}
