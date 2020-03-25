package com.ihrm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ihrm.common.entity.Result;
import com.ihrm.common.entity.ResultCode;
import com.ihrm.system.dao.RoleDao;
import com.ihrm.system.entity.Role;
import com.ihrm.system.entity.RolePri;
import com.ihrm.system.entity.UserRole;
import com.ihrm.system.service.RolePriService;
import com.ihrm.system.service.RoleService;
import com.ihrm.system.service.UserRoleService;
import com.ihrm.system.utils.PageQueryUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {
    @Autowired
    private RolePriService rolePriService;
    @Autowired
    private UserRoleService userRoleService;

    @Override
    public Result queryPage(Map<String,Object> params) {
        PageQueryUtils init = PageQueryUtils.init(params);
        try{
            List<Role> roles = baseMapper.queryPage(init.getQuery(),init.getPageNum(),init.getPageSize());
            return new Result(ResultCode.SUCCESS,roles);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(ResultCode.FAIL,null);
        }
    }

    @Override
    public boolean delete(int id) {
        rolePriService.remove(new QueryWrapper<RolePri>().eq("role_id", id));
        userRoleService.remove(new QueryWrapper<UserRole>().eq("role_id", id));
        boolean b = this.removeById(id);
        return b;
    }

    @Override
    public Result saveOrUpdate(Map<String, Object> params) {
        Integer id = null==params.get("id")?null:Integer.valueOf(params.get("id").toString());
        String name = (String) params.get("name");
        String remark = (String) params.get("remark");
        List<Integer> priIds = (List<Integer>) params.get("priIds");
        Role role = new Role();
        if(null != id){
            role.setId(id);
        }
        role.setName(name);
        role.setRemark(remark);
        try{
            saveOrUpdate(role);
            if(null == id){
                RolePri rolePri = new RolePri();
                rolePri.setRoleId(role.getId());
                for(int priId : priIds){
                    rolePri.setPriId(priId);
                    rolePriService.save(rolePri);
                }
            }
            return new Result(ResultCode.SUCCESS);
        }catch (Exception e){
            return new Result(ResultCode.FAIL,"请重试");
        }
    }
}
