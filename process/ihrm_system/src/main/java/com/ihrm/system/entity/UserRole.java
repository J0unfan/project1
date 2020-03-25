package com.ihrm.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;
//实体类
@Entity
@Data
@TableName("sys_rel_user_role")
public class UserRole implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type= IdType.AUTO)
    private Integer id;

    private Integer userId;

    private Integer roleId;
}
