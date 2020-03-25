package com.ihrm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ihrm.system.entity.UserDept;

import java.util.List;

public interface UserDeptService extends IService<UserDept> {
    /**
     *  插入用户-部门关联表数据
     */
   int insertUserDept(Integer userId,List<Integer> deptIds);

    /**
     * 根据用户id删除关联关系
     */
    int deleteByUserId(Integer userId);
}
