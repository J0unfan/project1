package ljf.GP.shop.adminuser.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ljf.GP.shop.adminuser.vo.AdminUser;

/**
 * 后台用户登录Dao层
 * 
 * @author ljf
 */
public class AdminUserDao extends HibernateDaoSupport {

	public AdminUser login(AdminUser adminUser) {
		String hql = "from AdminUser where username = ? and password = ?";
		List<AdminUser> list = getHibernateTemplate().find(hql, adminUser.getUsername(), adminUser.getPassword());
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}
}
