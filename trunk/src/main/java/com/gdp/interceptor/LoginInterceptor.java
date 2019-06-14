package com.gdp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.gdp.util.AESUtil;

public class LoginInterceptor implements HandlerInterceptor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String roleString = request.getHeader("role");
		String role;
		if (roleString != null) {
			role = AESUtil.decrypt(roleString, "role");
			if("user".equals(role)) {
//				String openid = AESUtil.decrypt(request.getHeader("token"), "openid");
//				LOGGER.info("小程序用户登录, openid: {} ", openid);

				return true;
			}
		} else {
			role = (String) request.getSession().getAttribute("role");
			if("admin".equals(role)) {
//				LOGGER.info("管理员登录");
				return true;
			}
		}
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
