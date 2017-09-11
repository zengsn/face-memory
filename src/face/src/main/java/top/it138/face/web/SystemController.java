package top.it138.face.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SystemController {
	
	@RequestMapping("/access-denied")
	public ModelAndView accessDenied() {
		ModelAndView mv = new ModelAndView("message/message");
		mv.addObject("message", "无权访问");
		return mv;
	}
	
	@RequestMapping("/")
	public String home() {
		return "index";
	}
}
