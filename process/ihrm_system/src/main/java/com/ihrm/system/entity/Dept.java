package com.ihrm.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

//实体类
@Entity
@Data
@TableName("sys_dept")
public class Dept implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Integer id;

    @NotBlank(message = "部门名不能为空")
    private String name;

    @TableField(value = "dept_number")
    private String deptNumber;// 部门/企业号

    @TableField(value = "manager_user_id")
    private Integer managerUserId;// 负责人id

    @TableField(value = "dept_level")
    private Integer deptLevel;// 部门级别

    @TableField(value = "is_parent")
    private Integer isParent;// 是否是父部门

    @NotBlank(message = "上级部门不能为空")
    private int parentId;

    private String remark;

    @TableField(value = "create_time")
    private Date createTime;

    @TableField(exist = false)
    private List<?> children;
}
