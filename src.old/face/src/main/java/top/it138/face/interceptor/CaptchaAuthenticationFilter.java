package top.it138.face.interceptor;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import top.it138.face.util.CapchaUtil;

public class CaptchaAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	// ~Filed
	private final String CAPTCHA_FIELD = "code";
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	public CaptchaAuthenticationFilter() {
		super(new AntPathRequestMatcher("/login", "POST"));
	}

	private void captchaError(HttpServletRequest request, HttpServletResponse response, String errorMsg) {
		log.error("ReCaptcha failed : " + errorMsg);

		try {
			response.sendRedirect(request.getContextPath() + "/login?error=1");
		} catch (IOException e) {
			log.error("发送重定向到登录错误页面s失败", e);
		}
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		if (!requiresAuthentication(request, response)) {
			chain.doFilter(request, response);

			return;
		}
		
		String capchaInput = req.getParameter(CAPTCHA_FIELD);
		String capcha = CapchaUtil.getCapcha();
		CapchaUtil.clearCapcha();
		if (!StringUtils.isEmpty(capchaInput)) {
			if (capchaInput.equalsIgnoreCase(capcha)) {
				chain.doFilter(req, res);
				return;
			}
		}
		captchaError(request, response, "验证码为空");
		
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		return null;
	}

}
