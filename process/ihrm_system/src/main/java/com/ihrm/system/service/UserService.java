package com.ihrm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ihrm.common.entity.Result;
import com.ihrm.system.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService extends IService<User> {
    Result queryPage(Map<String,Object> params);

    List<Integer> queryMenuId(int userId);

    boolean updatePassword(int userId,String password,String newPassword);

    Result saveOrUpdateUser(User user);

    Result deleteRel(int id);

    Result queryByUserId(int userId);
}
