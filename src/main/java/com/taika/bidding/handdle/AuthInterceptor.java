package com.taika.bidding.handdle;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.taika.bidding.bean.User;
import com.taika.bidding.common.CommonStr;

/**
 * 登录拦截
 *
 */
public class AuthInterceptor extends HandlerInterceptorAdapter {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) {
		User user = (User) request.getSession(true).getAttribute(CommonStr.TKUSER);
		if (null != user || request.getRequestURI().contains("user/login")
				|| request.getRequestURI().contains("allcansee") || request.getRequestURI().contains("allcanseestr")) {
			return true;
		}
		else {
			// 判断是否是ajax
			if (request.getHeader("x-requested-with") != null
					&& "XMLHttpRequest".equalsIgnoreCase(request.getHeader("x-requested-with"))) {
				Writer out = null;
				try {
					out = response.getWriter();
					out.append("没登陆没权限");
					out.flush();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
				finally {
					if (out != null) {
						try {
							out.close();
						}
						catch (IOException e) {
						}
					}
				}
			}
			else {
				try {
					response.sendRedirect(request.getContextPath() + "/login.html");
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			return false;
		}
	}

}