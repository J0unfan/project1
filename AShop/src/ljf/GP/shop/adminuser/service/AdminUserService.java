package ljf.GP.shop.adminuser.service;

import org.springframework.transaction.annotation.Transactional;

import ljf.GP.shop.adminuser.dao.AdminUserDao;
import ljf.GP.shop.adminuser.vo.AdminUser;

/**
 * 
 * @author ljf
 */
@Transactional
public class AdminUserService {
	// Dao注入
	private AdminUserDao adminUserDao;

	public void setAdminUserDao(AdminUserDao adminUserDao) {
		this.adminUserDao = adminUserDao;
	}

	public AdminUser login(AdminUser adminUser) {
		return adminUserDao.login(adminUser);
	}
}
