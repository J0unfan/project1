package com.ihrm.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.List;

//实体类 菜单
@Entity
@Data
@TableName("sys_privilege")
public class Privilege implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type= IdType.AUTO)
    private Integer id;

    private String name;

    private String icon;

    private String path;

    private Integer parentId;

    private Integer isParent;

    private String remark;

    @TableField(exist = false)
    private List<?> children;

}
