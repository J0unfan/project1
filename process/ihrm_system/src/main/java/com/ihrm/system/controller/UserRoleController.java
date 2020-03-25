package com.ihrm.system.controller;

import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.system.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * @author tyw
 */
@CrossOrigin
@RestController
@RequestMapping(value="/sys/rel")
public class UserRoleController {
    @Autowired
    private UserRoleService userRoleService;

    @PostMapping("/userAndRole")
    public Result save(@RequestParam Map<String,Object> params){
        try{
            userRoleService.saveUserRole(params);
            return new Result(ResultCode.SUCCESS);
        }catch (Exception e){
            return new Result(ResultCode.FAIL);
        }
    }

    @GetMapping("/userAndRole/{id}")
    public Result delete(@PathVariable("id") Integer id){
        try{
            userRoleService.removeById(id);
            return new Result(ResultCode.SUCCESS);
        }catch (Exception e){
            return new Result(ResultCode.FAIL);
        }
    }

    @PutMapping("/userAndRole")
    public Result update(@RequestParam Map<String,Object> params){
        try{
            userRoleService.updateUserRole(params);
            return new Result(ResultCode.SUCCESS);
        }catch (Exception e){
            return new Result(ResultCode.FAIL);
        }
    }
}
