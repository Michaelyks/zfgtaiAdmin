package com.zfgt.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.UsersLogin;

/**
 * 用户登录过滤; 对包名"/Product/User/"进行过滤
 * 
 * @author qwy
 *
 *         2015-4-16下午10:50:36
 */
public class UsersFilter implements Filter {
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		// 获取路径
		String URL = request.getRequestURI().replace("wgtz/", "");
		String params = request.getQueryString();
		if (StringUtils.isNotBlank(params)) {
			URL = URL + "?" + params;
		}

		String url = "/Product/login.jsp";// 登录页面
		if (!QwyUtil.isNullAndEmpty(URL) && !URL.contains(url) && !URL.contains("modifyPassword")) {
			// 产生一个cookie对象
			Cookie cookie = new Cookie("url", URL);
			cookie.setMaxAge(1800);// 设置cookie有效期，以秒为单位（设置和session一样30分钟）
			cookie.setPath("/");
			response.addCookie(cookie);// 响应
		}
		UsersLogin usersLogin = (UsersLogin) request.getSession().getAttribute("usersLogin");
		// System.out.println(request.getParameter("name"));
		if (usersLogin == null) {
			// 重定向到用户的登录界面

			response.sendRedirect(request.getContextPath() + "/Product/login.jsp");
			return;
		} else {
			if (usersLogin.getUserType() >= 0) {
				// 用户已登录;
				arg2.doFilter(request, response);
				return;
			} else {
				// 用户未登录;
				response.sendRedirect(request.getContextPath() + "/Product/login.jsp");
				return;
			}
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
