package ljf.GP.shop.user.service;
/**
*
* @author ljf
*/

import ljf.GP.shop.user.dao.UserDao;
import ljf.GP.shop.user.vo.User;

public class UserService {
	// 注入UserDao
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	// 按用户名查找用户
	public User findByUsername(String username) {
		return userDao.findByUsername(username);
	}

}
