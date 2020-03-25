package com.ihrm.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ihrm.system.entity.UserDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserDeptDao extends BaseMapper<UserDept> {
    /**
     * 插入用户-部门关联表数据
     *
     * @param userId
     * @param deptIds
     * @return
     */
    int insertUserDept(@Param("userId") Integer userId, @Param("deptIds") List<Integer> deptIds);

    /**
     * 根据用户id删除关联关系
     */
    int deleteByUserId(Integer userId);
}
