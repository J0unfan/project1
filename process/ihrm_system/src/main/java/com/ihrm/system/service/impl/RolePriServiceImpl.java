package com.ihrm.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ihrm.system.dao.RolePriDao;
import com.ihrm.system.entity.RolePri;
import com.ihrm.system.service.RolePriService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class RolePriServiceImpl extends ServiceImpl<RolePriDao, RolePri> implements RolePriService {
    /**
     * 根据角色id查询所有的权限
     * @param roleId
     * @return
     */
    @Override
    public List<Integer> findAllPriByRoleId(String roleId) {
        return baseMapper.queryMenuIdList(roleId);
    }

    /**
     * 删除
     * @param roleIds
     * @return
     */
    @Override
    public int deleteBatch(int[] roleIds) {
        return baseMapper.deleteBatch(roleIds);
    }

    /**
     * 保存角色权限关系
     * @param params
     * @return
     */
    @Override
    public boolean saveOrUpdate(Map<String,Object> params) {
        Integer roleId = Integer.valueOf(params.get("roleId").toString());
        List<Integer> prids = (List<Integer>) params.get("pris");
        if(roleId == null){// 保存
            return this.save(roleId,prids);
        }else{// 修改
            return this.update(roleId,prids);
        }
    }

    /**
     * 修改角色权限关系
     * @param roleId 角色id
     * @param priIds 权限集合
     * @return
     */
    @Override
    public boolean update(int roleId,List<Integer> priIds) {
        // 先删除角色及权限关系
        remove(new QueryWrapper<RolePri>().eq("role_id", roleId));
        for(Integer priId : priIds){
            RolePri rolePri = new RolePri();
            rolePri.setRoleId(roleId);
            rolePri.setPriId(priId);
            save(rolePri);
        }
        return true;
    }

    /**
     * 保存角色权限关系
     * @param roleId
     * @param prids
     * @return
     */
    public boolean save(int roleId,List<Integer> prids){
        RolePri rolePri = new RolePri();
        rolePri.setRoleId(roleId);
        for(Integer priId : prids){
            rolePri.setPriId(priId);
            save(rolePri);
        }
        return true;
    }
}
