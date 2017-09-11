package top.it138.face.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import top.it138.face.common.CommonResult;
import top.it138.face.entity.User;
import top.it138.face.entity.UserRole;
import top.it138.face.service.MailService;
import top.it138.face.service.UserRoleService;
import top.it138.face.service.UserService;
import top.it138.face.util.CapchaUtil;
import top.it138.face.util.UuidUtil;
import top.it138.face.vo.UserRegistrationForm;

@SessionAttributes(types=UserRegistrationForm.class)
@Controller
public class LoginController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UserService userService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private MailService MailService;
	@Autowired
	protected TemplateEngine thymeleaf;
	
	@ModelAttribute("userForm")
	UserRegistrationForm getUserForm() {
	  return new UserRegistrationForm();
	}

	@RequestMapping(value = {"/login" }, method = RequestMethod.GET)
	public ModelAndView login() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login/login");
		return modelAndView;
	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ModelAndView registration() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login/registration");
		UserRegistrationForm userForm = new UserRegistrationForm();
		modelAndView.addObject("userForm", userForm);
		return modelAndView;
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid @ModelAttribute("userForm") UserRegistrationForm userForm, BindingResult bindingResult,
			HttpServletRequest request, SessionStatus status) {
		//校验
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login/registration");
		if (bindingResult.hasErrors()) {
			return modelAndView;
		}
		
		if (!verifyCapcha0(userForm.getCode())) {
			//CAPCHA error
			bindingResult.rejectValue("code", "code.error", "验证码错误");
			return modelAndView;
		}
		
		CapchaUtil.clearCapcha();//clear CAPCHA
		
		User userExists = userService.selectUserByEmail(userForm.getEmail());
		if (userExists != null) {
			bindingResult.rejectValue("email", "email.exist", "邮箱已被注册");
			if (userExists.getEnabled() == 0) {  //未激活,发送
				sendMail(userExists, request);
				modelAndView.addObject("successMessage", "重新发送激活邮件，请登录邮箱后，点击验证链接");
			}
			return modelAndView;
		}
		userExists = userService.selectByUserName(userForm.getUsername());
		if (userExists != null) {
			bindingResult.rejectValue("username", "username.exist", "用户名已被注册");
			return modelAndView;
		}
		if (!userForm.getPassword().equals(userForm.getConfirmPassword())) {
			bindingResult.rejectValue("confirmPassword", "confirmPassword.noEquealToPassword", "两次输入的密码不一致");
			return modelAndView;
		}
		

		//注册
		User user = new User();
		user.setCode(UuidUtil.getUUID()); //邮箱激活码
		String password = userForm.getPassword();
		user.setPassword(passwordEncoder.encode(password.trim()));
		user.setEmail(userForm.getEmail().trim());
		user.setEnabled((byte)0);
		user.setUsername(userForm.getUsername().trim());
		
		userService.save(user);
		//role
		UserRole userRole = new UserRole();
		userRole.setRole("ROLE_USER");
		userRole.setUserId(user.getId());
		userRoleService.save(userRole);
		
		sendMail(user, request);
		
		status.setComplete();
		userForm = new UserRegistrationForm();
		
		modelAndView.addObject("successMessage", "用户注册成功,请登录邮箱后，点击验证链接");
		modelAndView.addObject("user", new UserRegistrationForm());
		modelAndView.setViewName("login/registration");
		
		return modelAndView;
	}

	/**
	 * 发送激活邮件
	 * @param user
	 * @param request
	 */
	private void sendMail(User user, HttpServletRequest request) {
		String email = user.getEmail();
		String subject = "人脸识别用户验证邮件";
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;
		Context context = new Context();
		context.setVariable("code", user.getCode());
		context.setVariable("basePath", basePath);
		String text = thymeleaf.process("mail/verification", context);
		MailService.sendText(email, subject, text);
		logger.warn(text);

	}
	
	@RequestMapping("/activateAccount")
	public ModelAndView activateAccount(String code) {
		ModelAndView mv = new ModelAndView("message/message");
		
		User user = userService.selectByCode(code);
		
		if (user == null) {
			mv.addObject("message", "激活失败，请检查激活码");
			return mv;
		}
		
		if (user.getEnabled() == 1) {
			mv.addObject("message", "在此之前已经激活");
			return mv;
		}
		
		user.setEnabled((byte)1);
		userService.update(user);
		mv.addObject("message", "激活成功");
		return mv;
	}

	/**
	 * 验证码
	 * @param req
	 * @param resp
	 */
	@RequestMapping(value = "/code")
	public void capcha(HttpServletRequest req, HttpServletResponse resp) {
		CapchaUtil.writeCapcha(req, resp);
	}

	/**
	 * json验证验证码
	 * @param code
	 * @param errors
	 * @return
	 */
	@RequestMapping("/verify")
	@ResponseBody
	public Object verifyCapcha(String code) {
		CommonResult<Boolean> json = new CommonResult<Boolean>(true);
		
		json.setData(verifyCapcha0(code));

		return json;
	}
	
	private boolean verifyCapcha0(String code) {
		String capcha = CapchaUtil.getCapcha();
		boolean isVerify = capcha == null ? false : capcha.equalsIgnoreCase(code);
		
		return isVerify;
	}
}
