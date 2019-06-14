package com.gdp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 拦截登陆已经失效的请求, 并做重定向
 * 
 * Time: 2019-02-19 21:04
 * @author Jashon
 * @version 1.0
 */
public class RedirectInterceptor implements HandlerInterceptor {
	
	private Logger logger = LoggerFactory.getLogger(RedirectInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
        response.setCharacterEncoding("UTF-8");

        HttpSession httpSession = request.getSession();
        String requestURI = request.getRequestURI();
        logger.debug("intercept RequestURI: {}", requestURI);

        if (null == httpSession.getAttribute("role")) {
            response.sendRedirect("/login.html");
            return false;
        }
        return true;
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
