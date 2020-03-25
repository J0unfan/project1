package com.ihrm.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ihrm.system.entity.Dept;
import com.ihrm.system.entity.DeptTree;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeptDao extends BaseMapper<Dept> {
    List<Dept> queryPage(String name,int page,int limit);

    /*根据fuid查询子部门*/
    List<DeptTree> queryByParentId(int parentId);
}
