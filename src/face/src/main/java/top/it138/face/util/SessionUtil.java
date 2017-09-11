package top.it138.face.util;

import org.springframework.security.core.context.SecurityContextHolder;

import top.it138.face.dto.MyUserDetails;
import top.it138.face.entity.User;

public class SessionUtil {
	/**
	 * 获得当前登录的用户
	 * 
	 * @return
	 */
	public static User getCurrentUser() {
		MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return userDetails;
	}
}
