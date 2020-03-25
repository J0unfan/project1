package com.ihrm.system.shiro.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ihrm.common.shiro.realm.IhrmRealm;
import com.ihrm.system.dao.RoleDao;
import com.ihrm.system.dao.UserDao;
import com.ihrm.system.entity.User;
import com.ihrm.system.service.PermissionService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class UserRealm extends IhrmRealm {

    @Autowired
    private UserDao userDao;
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleDao roleDao;// 角色dao

    private AuthenticationToken authenticationToken;

    //认证方法
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        this.authenticationToken = authenticationToken;
        // 获取用户的手机号和密码
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        String username = upToken.getUsername();
        String password = new String( upToken.getPassword());
        // 根据用户名查询用户
        User user = userDao.selectOne(new QueryWrapper<User>().eq("phone",username));
        List<String> perms = permissionService.findAllPermsByUserId(user.getId().intValue());// 获取该用户所有的权限
        user.setPerms(perms);
        // 设置用户角色集合
        List<Integer> roleList = roleDao.findAllRoleIdByUserId(user.getId().intValue());
        user.setRoles(roleList);
        // 判断用户是否存在，用户密码是否和输入密码一致
        if(user == null || !user.getPassword().equals(password)) {
            throw new UnknownAccountException("账号或密码不正确");
        }
        if(user.getStatus().equals("0")){
            throw new LockedAccountException("您已被禁用");
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getPassword(),this.getName());
        return info;
    }

    /**
     * 授权方法
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取用户的基本信息
        User user = (User)principalCollection.getPrimaryPrincipal();
        // 根据用户信息，查询用户的权限
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        List<String> perms = permissionService.findAllPermsByUserId(user.getId().intValue());
        info.addStringPermissions(user.getPerms());
        return info;
    }
}
