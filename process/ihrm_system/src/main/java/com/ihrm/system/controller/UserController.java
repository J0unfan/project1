package com.ihrm.system.controller;

import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.system.entity.User;
import com.ihrm.system.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tyw
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/sys")
public class UserController {
    @Autowired
    public UserService userService;

    /**
     * 批量删除
     */
    @RequestMapping("/user/delete/{id}")
    public Result delete(@PathVariable("id") Integer id) {
        return userService.deleteRel(id);
    }

    /**
     * 分页查询用户
     */
    @RequestMapping("/users")
    //@RequiresPermissions("sys.user.list")
    public Result queryPage(@RequestParam Map<String, Object> params) {
        return userService.queryPage(params);
    }

    /**
     * 用户登录
     *
     * @param param
     * @return
     */
    @RequestMapping("/user/login")
    public Result login(@RequestBody Map<String, String> param) {
        String username = param.get("username");
        String password = param.get("password");
        try {
            //1.构造登录令牌 UsernamePasswordToken
            //加密密码
            password = new Md5Hash(password, "123", 3).toString();  //1.密码，盐，加密次数
            UsernamePasswordToken upToken = new UsernamePasswordToken(username, password);
            //2.获取subject
            Subject subject = SecurityUtils.getSubject();
            //3.调用login方法，进入realm完成认证
            subject.login(upToken);
            //4.token
            String token = (String) subject.getSession().getId();
            // 获取token
//            JwtUtils jwtUtils = new JwtUtils();
            User user = (User) subject.getPrincipal();
            Map<String, Object> map = new HashMap<>();
            map.put("username", user.getUsername());
            map.put("phone", user.getPhone());
            //5.构造返回结果
            return new Result(ResultCode.SUCCESS,user.getUsername(),token);
        } catch (UnknownAccountException e) {
            return new Result(500, "密码或用户名错误");
        } catch (LockedAccountException e) {
            return new Result(500, "账号已被锁定");
        } catch (AuthenticationException e) {
            return new Result(500, "验证失效，请重试");
        }
    }

    /**
     * 修改密码
     */
    @RequestMapping("/user/updatePassword")
    public Result updatePassword(String password, String newPassword) {
        if (StringUtils.isBlank(newPassword)) {
            return new Result(ResultCode.FAIL, "新密码不能为空");
        }
        if (password.equals(newPassword)) {
            return new Result(ResultCode.FAIL, "新密码与原密码一致");
        }
        boolean boo = userService.updatePassword(getUserId(), password, newPassword);
        if (!boo) {
            return new Result(ResultCode.FAIL, "原密码错误");
        }
        return new Result(ResultCode.SUCCESS);
    }

    /**
     * 获取用户id
     */
    private Integer getUserId() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return user.getId();
    }

    /**
     * 增加或者修改用户
     *
     * @param user
     * @return
     */
    @PostMapping("/user/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody User user) {
        return userService.saveOrUpdateUser(user);
    }

    @GetMapping("/user/getOne/{id}")
    public Result queryByUserId(@PathVariable("id") Integer id){
        return userService.queryByUserId(id);
    }
}
