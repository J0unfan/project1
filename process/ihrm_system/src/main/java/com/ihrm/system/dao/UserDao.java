package com.ihrm.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ihrm.system.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao extends BaseMapper<User> {

     /**
     * 查询用户的所有菜单ID
     */
    List<Integer> queryAllMenuId(int userId);


    /*分页查询*/
    List<User> queryPage(String username,int page,int limit);

    /*根据用户名查询用户*/
    User queryByPhone(String phone);

    /**
     * 根据userid查询所有的角色
     * @param userId
     * @return
     */
    List<Integer> findAllRoleByUserId(String userId);

    /**
     * 根据userid查询所有的部门
     * @param userId
     * @return
     */
    List<Integer> findAllDeptByUserId(String userId);

    /**
     * 统计记录数
     * @return
     */
    Integer count();
}
