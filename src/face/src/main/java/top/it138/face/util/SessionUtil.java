package top.it138.face.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import top.it138.face.dto.MyUserDetails;
import top.it138.face.entity.User;

public class SessionUtil {
	/**
	 * 获得当前登录的用户
	 * 
	 * @return
	 */
	public static User getCurrentUser() {
		Object obj = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		
		
		if (obj.equals("anonymousUser")) {
			//获取当前线程绑定的请求
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
					.getRequest();
			User user = (User)request.getSession().getAttribute(Constants.SESSION_USER);
			return user;
		} else {
			MyUserDetails userDetails = (MyUserDetails)obj;
			return userDetails;
		}
		
	}

	public static Long getCurrentUserId() {
		return getCurrentUser().getId();
	}
}
