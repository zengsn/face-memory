package top.it138.face.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class RequestUtil {
	public static HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
	}
	
	public static String PototoURL(HttpServletRequest request,String name, String suffix) {
		String contextpath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
		+ request.getContextPath();
		String getPhotoPath = contextpath + "/photo/show?code=";
		return getPhotoPath + name + suffix;
	}
	
	public static String PototoURL(String name, String suffix) {
		HttpServletRequest request = getRequest();
		String contextpath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
		+ request.getContextPath();
		String getPhotoPath = contextpath + "/photo/show?code=";
		return getPhotoPath + name + suffix;
	}
	
	public static String getIp() {
		HttpServletRequest request = getRequest();
		if (request == null) {
			return "0.0.0.0";
		}
		String ip = request.getRemoteAddr();
		return ip;
	}
}
