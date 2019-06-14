package top.it138.face.interceptor;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import top.it138.face.dto.MyUserDetails;
import top.it138.face.entity.Log;
import top.it138.face.service.LogService;

/**
 * 退出前写日志到数据库
 * @author Lenovo
 *
 */
@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
	@Autowired
	private LogService LogService;

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		MyUserDetails userDetails = (MyUserDetails)authentication.getPrincipal();
		Log log = new Log();
		log.setIp(request.getRemoteAddr());
		log.setMessage("退出成功");
		log.setOperation("logout");
		log.setParams("");
		log.setTime(new Date());
		LogService.save(log);
		
		response.sendRedirect(request.getContextPath() + "/");
	}

}
