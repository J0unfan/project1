package ljf.GP.shop.user.action;
/**
*
* @author ljf
*/

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import ljf.GP.shop.user.service.UserService;
import ljf.GP.shop.user.vo.User;

public class UserAction extends ActionSupport implements ModelDriven<User> {
	// 模型驱动使用的对象
	private User user = new User();

	public User getModel() {
		return user;
	}

	// 注入UserService
	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	// 接收验证码
	private String checkcode;

	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

	private InputStream inputStream;

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	/**
	 * 跳转到注册页面的方法
	 */
	public String registPage() {
		return "registPage";
	}

	/**
	 * ajax进行异步校验用户名的方法
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public String findByName() throws UnsupportedEncodingException {
		String result = "yes";
		// 调用Service进行查询:
		User existUser = userService.findByUsername(user.getUsername());
		// 判断
		if (existUser != null) {
			// 查询到该用户:用户名已经存在
			inputStream = new ByteArrayInputStream(result.getBytes("utf-8"));
		} else {
			// 没查询到该用户:用户名可以使用
			result = "no";
			inputStream = new ByteArrayInputStream(result.getBytes("utf-8"));
		}
		return SUCCESS;
	}
}
