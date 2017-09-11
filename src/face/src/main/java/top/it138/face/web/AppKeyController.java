package top.it138.face.web;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;

import top.it138.face.common.CommonResult;
import top.it138.face.entity.App;
import top.it138.face.entity.User;
import top.it138.face.service.AppService;
import top.it138.face.util.SessionUtil;
import top.it138.face.util.UuidUtil;
import top.it138.face.vo.PageRequest;



@Controller
@Validated
@RequestMapping("appkeys")
public class AppKeyController {
	@Autowired
	private AppService appService;
	
	@RequestMapping("/listByPage")
	public ModelAndView listByPage(PageRequest pageRequest) {
		User user = SessionUtil.getCurrentUser();   //获取当前登录用户
		Page<App> page = appService.selectPage(user.getId(), pageRequest.getPageNum(), pageRequest.getPageSize());
		ModelAndView mv = new ModelAndView("appkey/listByPage");
		mv.addObject("page", page);
		mv.addObject("appkeys", page.getResult());
		
		return mv;
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public Object edit(String id, @NotBlank String appDesc) {
		App app = appService.selectById(Long.parseLong(id));
		User user = SessionUtil.getCurrentUser();
		if (app == null || !app.getUserId().equals(user.getId())) {
			//不能修改其他用户的appkey
			return new CommonResult<>(CommonResult.FAIL, "appId不存在");
		}
		
		app.setAppDesc(appDesc);
		appService.update(app);
		
		
		return new CommonResult<>(CommonResult.SUCCESS);
	}
	
	
	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(@NotBlank String id) {
		App app = appService.selectById(Long.parseLong(id));
		User user = SessionUtil.getCurrentUser();
		if (app == null || !app.getUserId().equals(user.getId())) {
			//不能修改其他用户的appkey
			return new CommonResult<>(CommonResult.FAIL, "appId不存在");
		}
		
		appService.deleteById(app.getId());;
		
		
		return new CommonResult<>(CommonResult.SUCCESS);
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	@ResponseBody
	public Object add(@NotBlank String appDesc) {
		App app = new App();
		app.setAppDesc(appDesc);
		app.setAppKey(UuidUtil.getUUID());
		app.setAppSecret(UuidUtil.getUUID());
		
		User user = SessionUtil.getCurrentUser();   //获取当前登录用户
		app.setUserId(user.getId());
		appService.save(app);
		
		return new CommonResult<>(CommonResult.SUCCESS);
	}
}
