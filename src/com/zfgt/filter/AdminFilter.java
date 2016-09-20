package com.zfgt.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zfgt.common.util.PropertiesUtil;
import com.zfgt.common.util.QwyUtil;
import com.zfgt.orm.Users;
import com.zfgt.orm.UsersAdmin;

/**
 * 管理员登录过滤; 对包名"/Product/Admin/"进行过滤
 * 
 * @author qwy
 *
 *         2015-4-16下午10:50:36
 */
public class AdminFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		UsersAdmin users = (UsersAdmin) request.getSession().getAttribute("usersAdmin");
		// System.out.println(request.getParameter("name"));
		Object isStartThread = PropertiesUtil.getProperties("isStartThread");
		if (!"1".equals(isStartThread.toString())) {
			// 跳转到后台地址;
			response.sendRedirect(PropertiesUtil.getProperties("httpIp") + request.getContextPath() + "/Product/loginBackground.jsp");
		} else {
			if (users == null) {
				// 重定向到管理员的登录界面

				response.sendRedirect(PropertiesUtil.getProperties("httpIp") + request.getContextPath() + "/Product/loginBackground.jsp");
				// response.sendRedirect("http://www.baiyimao.com/wgtz/Product/loginBackground.jsp");
			} else {
				/*
				 * if(users.getUserType().longValue()>=0){ //非管理员登录;
				 * response.sendRedirect(request.getContextPath()+
				 * "/Product/loginBackground.jsp"); return; }
				 */
				arg2.doFilter(request, response);
			}
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
