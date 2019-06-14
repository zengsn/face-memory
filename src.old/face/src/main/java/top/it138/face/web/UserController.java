package top.it138.face.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;

import top.it138.face.common.CommonResult;
import top.it138.face.entity.User;
import top.it138.face.service.UserService;
import top.it138.face.vo.PageRequest;



@Controller
@Validated
@RequestMapping("users")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("/listByPage")
	public ModelAndView listByPage(PageRequest pageRequest) {
		Page<User> page = userService.selectPage(pageRequest.getPageNum(), pageRequest.getPageSize());
		ModelAndView mv = new ModelAndView("user/listByPage");
		mv.addObject("page", page);
		mv.addObject("users", page.getResult());
		
		return mv;
	}
	
	
	
	@RequestMapping("/delete")
	@ResponseBody
	@Transactional
	public Object delete(Long id) {
		userService.deleteById(id);
		
		
		return new CommonResult<>(CommonResult.SUCCESS);
	}
	
}
