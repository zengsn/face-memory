package top.it138.face.interceptor;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.google.gson.Gson;

import top.it138.face.common.CommonResult;
import top.it138.face.entity.App;
import top.it138.face.entity.User;
import top.it138.face.service.AppService;
import top.it138.face.service.UserService;
import top.it138.face.util.Constants;

@Component
public class AppKeyLoginInterceptor extends HandlerInterceptorAdapter {
	private final Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private AppService appService;
	@Autowired
	private UserService userService;

	public AppKeyLoginInterceptor(AppService appService) {
		super();
		this.appService = appService;
	}

	/**
	 * 在请求处理之前进行调用（Controller方法调用之前）调用, 返回true 则放行， false 则将直接跳出方法
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String appKey = request.getHeader(Constants.STRING_APP_KEY);
		String appSecret = request.getHeader(Constants.STRING_APP_SECRET);
		App app = appService.selectByAppKey(appKey);
		if (app == null || !app.getAppSecret().equals(appSecret)) {
			ServletOutputStream output = response.getOutputStream();
			CommonResult<Boolean> result = new CommonResult<Boolean>("appKey或者appSecret错误");
			Gson gson = new Gson();
			String data = gson.toJson(result);
			IOUtils.write(data, output, "UTF-8");
			IOUtils.closeQuietly(output);
			return false;
		}
		
		HttpSession session = request.getSession();
		if (session.getAttribute(Constants.SESSION_USER) == null) {
			log.debug("往session注入User");
			User user = userService.selectById(app.getUserId());
			request.getSession().setAttribute(Constants.SESSION_USER, user);
		}

		return true;
	}

}
