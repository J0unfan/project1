package com.ihrm.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.crazycake.shiro.AuthCachePrincipal;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;


//实体类
@Data
@TableName("sys_user")
public class User implements AuthCachePrincipal,Serializable{
    private static final long serialVersionUID = 1L;

    @TableId(type= IdType.AUTO)
    private Integer id;

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "手机号不能为空")
    private String phone;

    private String password;

    private Date enterDate;

    @NotBlank(message = "用户状态不能为空")
    private String status;

    private Date leaveDate;
    // 用户的角色
    @TableField(exist = false)
    private List<Integer> roles;
    // 用户的部门
    @TableField(exist = false)
    private List<Integer> depts;
    // 用户的权限
    @TableField(exist = false)
    private List<String> perms;

    @Override
    public String getAuthCacheKey() {
        return String.valueOf(id);
    }
}
