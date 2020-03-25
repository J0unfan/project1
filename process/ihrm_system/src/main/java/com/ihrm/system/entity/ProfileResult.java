package com.ihrm.system.entity;

import com.ihrm.domain.system.Permission;
import lombok.Getter;
import lombok.Setter;
import org.crazycake.shiro.AuthCachePrincipal;

import java.io.Serializable;
import java.util.*;

@Setter
@Getter
public class ProfileResult implements Serializable,AuthCachePrincipal {
    private Integer id;// id
    private String phone;// 电话
    private String username;// 用户名
    private List<Integer> dept;// 部门
    private List<Integer> roles;// 角色
    private List<String> perms;// 权限

    public ProfileResult(User user) {
        this.phone = user.getPhone();
        this.username = user.getUsername();
        this.dept = user.getDepts();
        this.roles= user.getRoles();
        this.perms = user.getPerms();
    }

    @Override
    public String getAuthCacheKey() {
        return String.valueOf(id);
    }
}
