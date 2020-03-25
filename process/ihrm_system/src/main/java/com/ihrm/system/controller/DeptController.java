package com.ihrm.system.controller;

import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.system.entity.Dept;
import com.ihrm.system.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author tyw
 */
@CrossOrigin
@RestController
@RequestMapping(value="/sys")
public class DeptController {
    @Autowired
    private DeptService deptService;

    /*分页查询*/
    @GetMapping("/dept")
    public Result queryPage(@RequestParam Map<String,Object> params){
        return deptService.queryPage(params);
    }

    /*添加或修改*/
    @PostMapping("/dept/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody Dept dept){
        try{
            deptService.saveOrUpdate(dept);
            return new Result(ResultCode.SUCCESS);
        }catch (Exception e){
            return new Result(ResultCode.FAIL);
        }
    }

    /*删除*/
    @DeleteMapping("/dept/{id}")
    public Result delete(@PathVariable("id") Integer id){
        return deptService.delete(id);
    }

    /*获取部门树*/
    @RequestMapping("/dept/tree")
    public Result getTree(){
        List<Map<String,Object>> depts = deptService.queryAllDeptList();
        return new Result(ResultCode.SUCCESS,depts);
    }

}
