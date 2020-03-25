package com.ihrm.system.controller;

import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.system.entity.Privilege;
import com.ihrm.system.entity.User;
import com.ihrm.system.service.PrivilegeService;
import org.apache.shiro.SecurityUtils;
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
public class PrivilegeController {
    @Autowired
    private PrivilegeService privilegeService;

    /**导航菜单*/
    @GetMapping("/menus")
    public Result nav(){
        List<Map<String,Object>> userMenuList = privilegeService.getUserMenuList(getUserId().longValue());
        return new Result(ResultCode.SUCCESS,userMenuList);
    }

    /**删除*/
    @GetMapping("/menu/delete/{id}")
    public Result delete(@PathVariable("id") Integer id){
        return privilegeService.delete(id);
    }

    /**分页查询*/
    @GetMapping("/menu")
    public Result queryPage(@RequestParam Map<String,Object> params){
        return privilegeService.queryPage(params);
    }

    /**根据父节点查询子节点*/
    @RequestMapping("menu/findChildrenById/{id}")
    public Result findChildrenById(@PathVariable("id") Integer id){
        List<Privilege> privileges = privilegeService.queryListParentId(id);
        return new Result(ResultCode.SUCCESS,privileges);
    }

    /**获取用户id*/
    public Integer getUserId(){
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return user.getId();
    }

    /**
     * 增加 或 修改
     * @param privilege
     * @return
     */
    @PostMapping("/menu/saveOrUpdate")
    public Result saveOrUpdate(@RequestBody Privilege privilege){
        try{privilegeService.saveOrUpdate(privilege);
            return new Result(ResultCode.SUCCESS);}
        catch (Exception e){
            return new Result(ResultCode.FAIL);
        }
    }

    /**权限树*/
    @RequestMapping("/menu/privilegeTree")
    public Result privilegeTree(){
        try{
            List<Privilege> privileges = privilegeService.privilegeTree();
            return new Result(ResultCode.SUCCESS,privileges);
        }catch (Exception e){
            return new Result(ResultCode.FAIL);
        }
    }

    /**
     * 更具id查询
     * @param priId
     * @return
     */
    @RequestMapping("/menu/{id}")
    public Result findById(@PathVariable("id") Integer priId){
        Privilege p = privilegeService.getById(priId);
        if(p != null){
            return new Result(ResultCode.SUCCESS,p);
        }
        return new Result(ResultCode.FAIL);
    }

    /**
     * 获取根部门信息
     */
    @GetMapping("/menu/root")
    public List<Privilege> findAllRoot(){
        return privilegeService.queryListParentId(0);
    }
}
