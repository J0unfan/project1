package com.ihrm.system.entity;

import lombok.Data;

import java.util.List;

/**
 * @author caorui
 * @date 2020/3/13
 */
@Data
public class DeptTree {
    private Integer id;
    private String name;
    private Integer isParent;
    private List<DeptTree> children;
}
