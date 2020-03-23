package ljf.GP.shop.adminuser.action;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import ljf.GP.shop.adminuser.service.AdminUserService;
import ljf.GP.shop.adminuser.vo.AdminUser;

public class AdminUserAction extends ActionSupport implements ModelDriven<AdminUser> {
	// 模型驱动使用的对象
	private AdminUser adminUser = new AdminUser();

	@Override
	public AdminUser getModel() {
		return adminUser;
	}

	// Service注入
	private AdminUserService adminUserService;

	public void setAdminUserService(AdminUserService adminUserService) {
		this.adminUserService = adminUserService;
	}

	public String login() {
		AdminUser existAdminUser = adminUserService.login(adminUser);
		if (existAdminUser == null) {
			// 用户名或者密码错误
			addActionError("用户名或密码错误！");
			return "loginFail";
		} else {
			// 登录成功
			ServletActionContext.getRequest().getSession().setAttribute("existAdminUser", existAdminUser);
			return "loginSuccess";
		}
	}

}
