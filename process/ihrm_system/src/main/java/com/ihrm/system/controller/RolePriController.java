package com.ihrm.system.controller;

import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.system.service.RolePriService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author tyw
 */
@CrossOrigin
@RestController
@RequestMapping(value="/sys/rel/roleAndPri")
public class RolePriController {
    @Autowired
    private RolePriService rolePriService;

    /**
     * 新增/修改角色权限关系
     * @param params
     * @return
     */
    @PostMapping("/saveOrUpdate")
    public Result save(@RequestBody Map<String,Object> params){
        try{
            rolePriService.saveOrUpdate(params);
            return new Result(ResultCode.SUCCESS);
        }catch (Exception e){
            return new Result(ResultCode.FAIL);
        }
    }

    /**
     * 删除角色权限关系
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result delete(@PathVariable("id") Integer id){
        try{
            rolePriService.removeById(id);
            return new Result(ResultCode.SUCCESS);
        }catch (Exception e){
            return new Result(ResultCode.FAIL);
        }
    }

    /**
     * 根据角色id查询所有的权限
     * @param roleId
     * @return
     */
    @GetMapping("/findPriByRoleId")
    public Result findPriByRoleId(String roleId){
        List<Integer> priIds = rolePriService.findAllPriByRoleId(roleId);
        if(priIds != null){
            return new Result(ResultCode.SUCCESS,priIds);
        }else{
            return new Result(ResultCode.FAIL,priIds);
        }
    }
}
