package com.zfgt.login.action;

import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;

import com.zfgt.common.action.BaseAction;
import com.zfgt.common.bean.PlatformBean;
import com.zfgt.common.util.DESEncrypt;
import com.zfgt.common.util.MyRedis;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.login.bean.RegisterUserBean;
import com.zfgt.orm.Platform;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersLogin;

/**
 * 注册用户Action
 * 
 * @author qwy
 *
 *         2015-4-18上午3:34:06
 */
@SuppressWarnings("serial")
@ParentPackage("struts-default")
@Namespace("/Product")
@Results({ @Result(name = "index", value = "/Product/index.jsp"), @Result(name = "regist", value = "/Product/register.jsp"), @Result(name = "resetPassword", value = "/Product/set_password.jsp"),
		@Result(name = "resetPayPassword", value = "/Product/set_pay_password.jsp") })
public class RegisterUserAction extends BaseAction {

	private Users users;
	private String validateCode;// 页面验证码;
	private String smsValidateCode;// 手机验证码;
	private static Logger log = Logger.getLogger(RegisterUserAction.class);

	@Resource
	private RegisterUserBean bean;
	@Resource
	private PlatformBean platformBean;

	private String password2;
	private String modifyId;// 重置密码时的,用户id;

	public String regist() {
		Platform platform = platformBean.getPlatform();
		getRequest().setAttribute("registerCount", platform.getRegisterCount());
		getRequest().setAttribute("collectMoney", platform.getCollectMoney());
		return "regist";
	}

	/**
	 * 注册用户;邮箱注册;
	 * 
	 * @return
	 */
	public String registerUser() {
		System.out.println("进入注册页面");
		String json = "";
		try {
			request = getRequest();
			Object obj = request.getSession().getAttribute("rand");
			if (QwyUtil.isNullAndEmpty(obj) || !obj.toString().equals(validateCode)) {
				// 验证码错误;
				json = QwyUtil.getJSONString("err", "验证码错误");
			} else {
				// 验证码正确;
				if (users == null) {
					json = QwyUtil.getJSONString("err", "请填写注册信息");
				} else {
					String email = users.getUsername();
					if (email == null || email.length() < 6) {
						// response.getWriter().write("{'message':'邮箱长度不能少于6位'}");
						json = QwyUtil.getJSONString("err", "邮箱长度不能少于6位");
						// return "input";
						QwyUtil.printJSON(getResponse(), json);
						return null;
					}
					if (users.getPassword().length() < 6) {
						json = QwyUtil.getJSONString("err", "密码长度至不能少于6位");
						QwyUtil.printJSON(getResponse(), json);
						return null;
					}
					if (users.getPassword().length() > 16) {
						json = QwyUtil.getJSONString("err", "密码长度不能大于16位");
						QwyUtil.printJSON(getResponse(), json);
						return null;
					} else {
						String p_email = "^([a-zA-Z0-9]+[-|_|\\_|\\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[-|_|\\_|\\.]?)*[a-zA-Z0-9]+\\.[a-zA-Z]{2,3}$";
						Pattern pattern_1 = Pattern.compile(p_email);
						Matcher m_email = pattern_1.matcher(email);
						if (!m_email.find()) {
							String status = "邮箱格式不正确，请输入正确的邮箱地址";
							json = QwyUtil.getJSONString("err", status);
							// return "input";
						} else {
							Users newUsers = bean.getUsersByUsername(email);
							if (newUsers != null) {
								json = QwyUtil.getJSONString("err", "用户名已存在");
							} else {
								if (!password2.equals(users.getPassword())) {
									json = QwyUtil.getJSONString("err", "两次密码不一致");
								} else {
									// 进入到此处; 用户名可用,两次密码一致,验证码正确;可以注册;
									String uid = bean.registerNewUser(users, "1");
									if (QwyUtil.isNullAndEmpty(uid)) {
										json = QwyUtil.getJSONString("err", "注册失败");
									} else {
										json = QwyUtil.getJSONString("ok", "注册成功");
										Users newUser = bean.getUsersById(Long.parseLong(uid));
										getRequest().getSession().setAttribute("users", newUser);
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("操作异常: ", e);
			json = QwyUtil.getJSONString("err", "注册失败");
		}
		try {
			System.out.println(json);
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			log.error("操作异常: ", e);
		}
		return null;
	}

	/**
	 * 手机注册<br>
	 * 验证页面验证码,验证手机验证码,验证密码是否符合格式;
	 * 
	 * @return
	 * @throws IOException
	 */
	public String phoneRegister() {
		String json = "";
		try {
			System.out.println("进入注册action");
			if (users == null) {
				json = QwyUtil.getJSONString("err", "请填写注册信息");
			} else {
				String phone = QwyUtil.isNullAndEmpty(users.getUsername()) ? "" : users.getUsername();
				if (!QwyUtil.verifyPhone(phone)) {
					log.info(phone + "请输入正确的手机号");
					json = QwyUtil.getJSONString("err", "请输入正确的手机号");
				} else {
					Users newUsers = bean.getUsersByUsername(phone);
					if (newUsers != null) {
						json = QwyUtil.getJSONString("err", "该手机已被注册");
						System.out.println("该手机已被注册");
						QwyUtil.printJSON(getResponse(), json);
						return null;
					}
					Object obj = request.getSession().getAttribute("rand");
					if (QwyUtil.isNullAndEmpty(obj) || !obj.toString().equals(validateCode)) {
						// 验证码错误;
						json = QwyUtil.getJSONString("err", "验证码错误");
						QwyUtil.printJSON(getResponse(), json);
						return null;
					}

					String smsCode = bean.getYzm(phone, "1");
					if (QwyUtil.isNullAndEmpty(smsValidateCode) || !smsValidateCode.equals(smsCode)) {
						// 手机验证码错误;
						json = QwyUtil.getJSONString("err", "手机验证码错误");
						QwyUtil.printJSON(getResponse(), json);
						return null;
					}

					String password = users.getPassword();
					if (password.length() < 6 || password.length() > 16) {
						json = QwyUtil.getJSONString("err", "密码长度为6~16位");
						QwyUtil.printJSON(getResponse(), json);
						return null;
					}
					if (!QwyUtil.isPassword(password)) {
						json = QwyUtil.getJSONString("err", "密码只支持字母数字下划线");
						QwyUtil.printJSON(getResponse(), json);
						return null;
					}
					if (!password.equals(password2)) {
						json = QwyUtil.getJSONString("err", "两次密码不一致");
						QwyUtil.printJSON(getResponse(), json);
						return null;
					}
					/*
					 * Users newUsers = bean.getUsersByUsername(phone);
					 * if(newUsers!=null){ json = QwyUtil.getJSONString("err",
					 * "该手机已被注册"); System.out.println("用户名已存在"); }else{
					 */
					// 手机验证通过，账号不存在且符合要求，并且密码一致，可以注册;
					String uid = bean.registerNewUser(users, "0");
					if (QwyUtil.isNullAndEmpty(uid)) {
						json = QwyUtil.getJSONString("err", "注册失败");
					} else {
						users = bean.getUsersById(Long.parseLong(uid));
						UsersLogin usersLogin = new UsersLogin(users.getId(), DESEncrypt.jieMiUsername(users.getUsername()), users.getUserType(), users.getUsersInfo().getLeftMoney());
						getRequest().getSession().setAttribute("usersLogin", usersLogin);
						// getRequest().getSession().setAttribute("users",
						// users);
						System.out.println("注册成功");
						String productId = bean.getFreshmanProductId();
						json = QwyUtil.getJSONString("ok", productId);
					}
					// }
				}
			}

		} catch (Exception e) {
			log.error("操作异常: ", e);
			json = QwyUtil.getJSONString("err", "注册失败");
		}
		try {
			System.out.println(json);
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			log.error("操作异常: ", e);
		}
		return null;
	}

	/**
	 * 找回登录密码;
	 * 
	 * @return
	 */
	public String findPassword() {
		/*
		 * String json=""; try { System.out.println("进入找回登录密码action");
		 * if(users==null){ json = QwyUtil.getJSONString("err", "请填写找回密码信息");
		 * }else{ String
		 * phone=QwyUtil.isNullAndEmpty(users.getUsername())?"":users.
		 * getUsername(); if(!QwyUtil.verifyPhone(phone)){
		 * log.info(phone+"请输入正确的手机号"); json= QwyUtil.getJSONString("err",
		 * "请输入正确的手机号"); }else{ Users newUsers = bean.getUsersByUsername(phone);
		 * if(newUsers==null){ json = QwyUtil.getJSONString("err", "用户名不存在");
		 * System.out.println("用户名不存在"); QwyUtil.printJSON(getResponse(), json);
		 * return null; } String smsCode = bean.getYzm(phone, "2");
		 * if(QwyUtil.isNullAndEmpty(smsValidateCode) ||
		 * !smsValidateCode.equals(smsCode)){ //手机验证码错误; json =
		 * QwyUtil.getJSONString("err", "手机验证码错误");
		 * QwyUtil.printJSON(getResponse(), json); return null; }
		 * //手机验证通过，账号存在;可以进入重新设置密码步骤; json = QwyUtil.getJSONString("ok",
		 * newUsers.getId().toString()); } }
		 * 
		 * }catch (Exception e) { log.error("操作异常: ",e); json =
		 * QwyUtil.getJSONString("err", "注册失败"); }
		 */
		try {
			String json = findPwd("2");
			System.out.println(json);
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			log.error("操作异常: ", e);
		}
		return null;
	}

	/**
	 * 找回登录密码;
	 * 
	 * @return
	 */
	public String findPayPassword() {
		try {
			String json = findPwd("3");
			System.out.println(json);
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			log.error("操作异常: ", e);
		}
		return null;
	}

	/**
	 * 生成验证码并保存到手机中
	 * 
	 * @param sendSMSType
	 *            1为注册，2为找回登录密码, 3找回交易密码
	 * @return
	 */
	public String findPwd(String sendSMSType) {
		String json = "";
		try {
			System.out.println("进入找回密码公共方法action,type: " + sendSMSType);
			if (users == null) {
				json = QwyUtil.getJSONString("err", "请填写找回密码信息");
			} else {
				String phone = QwyUtil.isNullAndEmpty(users.getUsername()) ? "" : users.getUsername();
				if (!QwyUtil.verifyPhone(phone)) {
					log.info(phone + "请输入正确的手机号");
					json = QwyUtil.getJSONString("err", "请输入正确的手机号");
				} else {
					Users newUsers = bean.getUsersByUsername(phone);
					if (newUsers == null) {
						json = QwyUtil.getJSONString("err", "用户名不存在");
						System.out.println("用户名不存在");
						return json;
					}
					String smsCode = bean.getYzm(phone, sendSMSType);
					if (QwyUtil.isNullAndEmpty(smsValidateCode) || !smsValidateCode.equals(smsCode)) {
						// 手机验证码错误;
						json = QwyUtil.getJSONString("err", "手机验证码错误");
						return json;
					}
					// 手机验证通过，账号存在;可以进入重新设置密码步骤;
					json = QwyUtil.getJSONString("ok", newUsers.getId().toString());
				}
			}

		} catch (Exception e) {
			log.error("操作异常: ", e);
			json = QwyUtil.getJSONString("err", "操作异常");
		}
		return json;
	}

	/**
	 * 跳转到重置密码的页面;
	 * 
	 * @return
	 */
	public String goToResetPassword() {

		request = getRequest();
		request.setAttribute("smsValidateCode", smsValidateCode);
		request.setAttribute("modifyId", modifyId);

		return "resetPassword";
	}

	/**
	 * 跳转到重置支付密码的页面;
	 * 
	 * @return
	 */
	public String goToResetPayPassword() {

		request = getRequest();
		request.setAttribute("smsValidateCode", smsValidateCode);
		request.setAttribute("modifyId", modifyId);

		return "resetPayPassword";
	}

	/**
	 * 重置密码;
	 * 
	 * @param sendSMSType
	 *            类型: 2:找回登录密码; 3:找回支付密码;
	 * @return
	 */
	public String resetPwd(String sendSMSType) {
		String json = "";
		try {
			if (QwyUtil.isNullAndEmpty(users)) {
				json = QwyUtil.getJSONString("err", "用户名不存在");
				return json;
			}
			if (QwyUtil.isNullAndEmpty(password2) || QwyUtil.isNullAndEmpty(users.getPassword()) || QwyUtil.isNullAndEmpty(modifyId)) {
				json = QwyUtil.getJSONString("err", "请输入密码");
				return json;
			}
			long usersId = -1;
			try {
				usersId = Long.parseLong(modifyId);
			} catch (NumberFormatException e) {
				log.error("重置密码时，ID被恶意修改过！", e);
				json = QwyUtil.getJSONString("err", "用户名不存在");
				return json;
			}
			Users dbUser = bean.getUsersById(usersId);
			if (QwyUtil.isNullAndEmpty(dbUser)) {
				json = QwyUtil.getJSONString("err", "用户名不存在");
				return json;
			}
			String smsCode = bean.getYzm(DESEncrypt.jieMiUsername(dbUser.getUsername()), sendSMSType);
			if (QwyUtil.isNullAndEmpty(smsValidateCode) || !smsValidateCode.equals(smsCode)) {
				// 手机验证码错误;
				json = QwyUtil.getJSONString("err", "手机验证码错误");
				return json;
			}
			if (!QwyUtil.isPassword(users.getPassword())) {
				json = QwyUtil.getJSONString("err", "密码只支持字母数字下划线");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
			if (!password2.equals(users.getPassword())) {
				json = QwyUtil.getJSONString("err", "两次密码不一致");
				return json;
			}
			if ("2".equals(sendSMSType)) {
				// 重置登录密码;
				boolean isOk = bean.modifyPassword(usersId, password2);
				if (isOk) {
					json = QwyUtil.getJSONString("ok", "重置密码成功");
				} else {
					json = QwyUtil.getJSONString("err", "重置密码失败,请联系客服");
				}
			} else if ("3".equals(sendSMSType)) {
				// 重置支付密码;
				boolean isOk = bean.modifyPayPassword(usersId, password2);
				if (isOk) {
					json = QwyUtil.getJSONString("ok", "重置支付密码成功");
				} else {
					json = QwyUtil.getJSONString("err", "重置支付密码失败,请联系客服");
				}
			} else {
				json = QwyUtil.getJSONString("err", "请不要修改参数");
			}
		} catch (Exception e) {
			log.error("操作异常: ", e);
			json = QwyUtil.getJSONString("err", "操作异常,请联系客服");
		}
		return json;
	}

	/**
	 * 重置密码;
	 * 
	 * @return
	 */
	public String resetPassword() {
		try {
			String json = resetPwd("2");
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			log.error("操作异常: ", e);
		}
		return null;
	}

	/**
	 * 重置密码;
	 * 
	 * @return
	 */
	public String resetPayPassword() {
		try {
			String json = resetPwd("3");
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			log.error("操作异常: ", e);
		}
		return null;
	}

	/**
	 * 发送注册时的验证码;
	 * 
	 * @return
	 */
	public String sendSMSMessage() {
		try {
			String json = validataUser("1");
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			log.error("操作异常: ", e);
		}
		return null;
	}

	/**
	 * 发送找回密码的验证码;
	 * 
	 * @return
	 */
	public String sendSMSMessageFindPassword() {
		try {
			String json = validataUser("2");
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			log.error("操作异常: ", e);
		}
		return null;
	}

	/**
	 * 发送找回支付密码的验证码;
	 * 
	 * @return
	 */
	public String sendSMSMessageFindPayPassword() {
		String json = "";
		try {
			log.info("发送找回支付密码的验证码;");
			UsersLogin usersLogin = (UsersLogin) getRequest().getSession().getAttribute("usersLogin");
			if (QwyUtil.isNullAndEmpty(usersLogin)) {
				json = QwyUtil.getJSONString("noLogin", "登录超时,请重新登录");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
			Users users1 = bean.getUsersById(usersLogin.getUsersId());
			if (!users.getUsername().equals(DESEncrypt.jieMiUsername(users1.getUsername()))) {
				json = QwyUtil.getJSONString("error", "请使用本人注册绑定的手机号码");
				QwyUtil.printJSON(getResponse(), json);
				return null;
			}
			json = validataUser("3");
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			log.error("操作异常: ", e);
		}
		return null;
	}

	/**
	 * 发送短信的类型;
	 * 
	 * @param sendMSMType
	 *            //1:注册帐号;2:找回登录密码;3:找回交易密码;
	 * @return
	 */
	public String validataUser(String sendMSMType) {
		String json = "";
		try {
			request = getRequest();
			if (QwyUtil.isNullAndEmpty(users)) {
				return QwyUtil.getJSONString("error", "请输入正确的手机号");
			}
			String phone = users.getUsername();
			phone = QwyUtil.isNullAndEmpty(phone) ? "" : phone;
			if (!QwyUtil.verifyPhone(phone)) {
				log.info(phone + "请输入正确的手机号");
				json = QwyUtil.getJSONString("error", "请输入正确的手机号");
			} else {
				String time = bean.getYmYzm(phone);
				if (!QwyUtil.isNullAndEmpty(time)) {
					long dis = new Date().getTime() - Long.parseLong(time);
					long newDis = 120 - dis / 1000;
					if (newDis > 0) {
						System.out.println("请在 " + newDis + " 秒后再次获取验证码");
						return json = QwyUtil.getJSONString("error", "请在 " + newDis + " 秒后再次获取验证码");
					}
				}
				String yibuString = bean.getYmYzm(sendMSMType + "yibu" + phone);
				String lastTime = bean.getYmYzm(sendMSMType + "yibuTime" + phone);
				if (QwyUtil.isNullAndEmpty(yibuString)) {
					MyRedis yibu = new MyRedis();
					yibu.setex(sendMSMType + "yibu" + phone, 1 * 60 * 60, "1");// 请求次数
					yibu.setex(sendMSMType + "yibuTime" + phone, 1 * 60 * 60, new Date().getTime() + "");// 请求时间
				} else {
					// MyRedis delyibu=new MyRedis();
					// delyibu.del(sendMSMType+"yibu"+phone);
					long limitTime = 1 * 60 * 60;
					if (!QwyUtil.isNullAndEmpty(lastTime)) {
						System.out.println(Long.parseLong(lastTime));
						limitTime = new Date().getTime() - Long.parseLong(lastTime);
					}
					if (limitTime <= 1 * 60 * 60 * 1000) {
						limitTime = 1 * 60 * 60 - limitTime / 1000;
					}
					if ("1".equals(yibuString)) {
						yibuString = "2";
						MyRedis yibu = new MyRedis();
						yibu.setex(sendMSMType + "yibu" + phone, (int) limitTime, "2");
						yibu.setex(sendMSMType + "yibuTime" + phone, (int) limitTime, new Date().getTime() + "");// 请求时间
					} else if ("2".equals(yibuString)) {
						yibuString = "3";
						MyRedis yibu = new MyRedis();
						yibu.setex(sendMSMType + "yibu" + phone, (int) limitTime, "3");
						yibu.setex(sendMSMType + "yibuTime" + phone, (int) limitTime, new Date().getTime() + "");// 请求时间
					} else if ("3".equals(yibuString)) {
						System.out.println("一小时内只能发送三次验证码");
						return json = QwyUtil.getJSONString("error", "一小时内只能发送三次，请稍候再获取验证码；");
					}
				}
				Users newUsers = bean.getUsersByUsername(phone);
				boolean isOk = false;
				if ("1".equals(sendMSMType)) {
					// 发送短信,走注册通道;
					if (newUsers != null) {
						isOk = false;
						json = QwyUtil.getJSONString("exists", "该手机已被注册");
						System.out.println("用户名已存在");
					} else {
						isOk = true;
					}
				} else {
					if (newUsers == null) {
						isOk = false;
						json = QwyUtil.getJSONString("exists", "用户名不存在");
						System.out.println("用户名不存在");
					} else {
						isOk = true;
					}
				}
				if (isOk) {
					String result = bean.sendYZM(phone, sendMSMType);
					if (QwyUtil.isNullAndEmpty(result)) {
						json = QwyUtil.getJSONString("ok", "手机验证码发送成功,请注意查收!");
						bean.setYmYzm(phone);
					} else {
						json = QwyUtil.getJSONString("error", result);
					}
				}
			}

		} catch (Exception e) {
			log.error("操作异常: ", e);
			json = QwyUtil.getJSONString("ok", "发送验证码失败");
		}
		return json;
	}

	/**
	 * 判断用户是否存在
	 * 
	 * @return
	 * @throws IOException
	 */
	public String isUserExist() throws IOException {
		String json = "";
		try {
			if (QwyUtil.isNullAndEmpty(users)) {
				json = QwyUtil.getJSONString("error", "手机号码格式错误");
			} else {
				Users user = bean.getUsersByUsername(users.getUsername());
				if (user != null) {
					json = QwyUtil.getJSONString("exists", "该手机已被注册");
				} else {
					json = QwyUtil.getJSONString("ok", "手机号码可用");
				}
			}
		} catch (Exception e) {
			log.error("操作异常: ", e);
			json = QwyUtil.getJSONString("error", "验证手机号码失败");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (Exception e) {
			log.error("操作异常: ", e);
		}
		return null;

	}

	/**
	 * 验证页面验证码是否正确;
	 * 
	 * @return
	 */
	public String yzCode() {
		String json = "";
		Object obj = request.getSession().getAttribute("rand");
		if (QwyUtil.isNullAndEmpty(obj) || !obj.toString().equals(validateCode)) {
			// 验证码错误;
			json = QwyUtil.getJSONString("err", "验证码错误");
		} else {
			json = QwyUtil.getJSONString("ok", "验证码正确");
		}
		try {
			QwyUtil.printJSON(getResponse(), json);
		} catch (IOException e) {
			log.error("操作异常: ", e);
			e.printStackTrace();
		}
		return null;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getSmsValidateCode() {
		return smsValidateCode;
	}

	public void setSmsValidateCode(String smsValidateCode) {
		this.smsValidateCode = smsValidateCode;
	}

	public String getModifyId() {
		return modifyId;
	}

	public void setModifyId(String modifyId) {
		this.modifyId = modifyId;
	}

}
