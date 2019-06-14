package top.it138.face.interceptor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import top.it138.face.annotation.LogAfter;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {

	/**
	 * 利用注解写日志到数据库，逻辑在@see /face/src/main/java/top/it138/face/interceptor/LogAopInterceptor.java
	 */
	@Override
	@LogAfter(message="登录成功", operation="login")
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		response.sendRedirect(request.getContextPath() + "/");
		
	}

}
