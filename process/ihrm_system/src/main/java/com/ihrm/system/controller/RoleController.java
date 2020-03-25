package com.ihrm.system.controller;

import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.system.entity.Role;
import com.ihrm.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author tyw
 */
@CrossOrigin
@RestController
@RequestMapping(value="/sys")
public class RoleController {
    @Autowired
    private RoleService roleService;


    /*查询所有的角色*/
    @GetMapping("/roles")
    public Result roles(){
        List<Role> list = roleService.list();
        return new Result(ResultCode.SUCCESS,list);
    }

    /*删除角色,关联删除*/
    // TODO
    @GetMapping("/role/{id}")
    public Result delete(@PathVariable("id") Integer id){
        try{
            roleService.delete(id);
            return new Result(ResultCode.SUCCESS);
        }catch (Exception e){
            return new Result(ResultCode.FAIL);
        }
    }

    /*分页查询*/
    @GetMapping("/role")
    public Result queryPage(@RequestParam Map<String,Object> params){
        return roleService.queryPage(params);
    }

    /*添加或修改*/
    @RequestMapping("/role/saveOrUpdate")
    public Result saveOrUpdate(@RequestParam Map<String,Object> params){
        return roleService.saveOrUpdate(params);
    }

}
