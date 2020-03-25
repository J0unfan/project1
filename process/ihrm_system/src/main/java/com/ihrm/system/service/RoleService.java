package com.ihrm.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ihrm.common.entity.Result;
import com.ihrm.system.entity.Role;

import java.util.List;
import java.util.Map;

public interface RoleService extends IService<Role> {
    Result queryPage(Map<String,Object> params);

    boolean delete(int id);

    Result saveOrUpdate(Map<String,Object> params);
}
