package ljf.GP.shop.user.dao;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import ljf.GP.shop.user.vo.User;

/**
 *
 * @author ljf
 */
public class UserDao extends HibernateDaoSupport {

	// 根据username查询是否有此用户
	public User findByUsername(String username) {
		String hql = "from User where username = ?";
		List<User> list = this.getHibernateTemplate().find(hql, username);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

}
