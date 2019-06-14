package top.it138.face.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;

import top.it138.face.entity.Log;
import top.it138.face.service.LogService;
import top.it138.face.vo.PageRequest;



@Controller
@Validated
@RequestMapping("logs")
public class LogController {
	@Autowired
	private LogService logService;
	
	@RequestMapping("/listByPage")
	public ModelAndView listByPage(PageRequest pageRequest) {
		Page<Log> page = logService.selectPage(pageRequest.getPageNum(), pageRequest.getPageSize());
		ModelAndView mv = new ModelAndView("log/listByPage");
		mv.addObject("page", page);
		mv.addObject("logs", page.getResult());
		
		return mv;
	}
	
}
