package top.it138.face.web;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;

import top.it138.face.common.CommonResult;
import top.it138.face.entity.App;
import top.it138.face.entity.Group;
import top.it138.face.entity.User;
import top.it138.face.service.AppService;
import top.it138.face.service.GroupService;
import top.it138.face.util.SessionUtil;
import top.it138.face.vo.PageRequest;

@Controller
@Validated
@RequestMapping("group")
public class GroupController {
	@Autowired
	private GroupService groupService;
	@Autowired
	private AppService appService;

	@RequestMapping("/listByPage")
	public ModelAndView listByPage(PageRequest pageRequest, @NotBlank String appKey) {
		Page<Group> page = groupService.selectPage(appKey, pageRequest.getPageNum(), pageRequest.getPageSize());
		ModelAndView mv = new ModelAndView("group/listByPage");
		mv.addObject("page", page);
		mv.addObject("groups", page.getResult());
		
		App app = appService.selectByAppKey(appKey);
		mv.addObject("app", app);

		return mv;
	}

	@RequestMapping("/edit")
	@ResponseBody
	@Transactional
	public Object edit(long id, Group g) {
		Group group = groupService.selectById(id);
		User user = SessionUtil.getCurrentUser(); // 获取当前登录用户
		if (group == null || !user.getId().equals(group.getUserId()) || !g.getId().equals(id)) {
			// 不能修改其他用户的appkey
			return new CommonResult<>(CommonResult.FAIL, "appId不存在或者没有访问权限");
		}
		group.setGroupName(g.getGroupName());
		group.setMaxNum(g.getMaxNum());
		groupService.update(group);

		return new CommonResult<>(CommonResult.SUCCESS);
	}
	
	@RequestMapping("/byId")
	@ResponseBody
	public Object byId(long id) {
		Group group = groupService.selectById(id);
		User user = SessionUtil.getCurrentUser(); // 获取当前登录用户
		if (group == null || !user.getId().equals(group.getUserId())) {
			// 不能修改其他用户的appkey
			return new CommonResult<>(CommonResult.FAIL, "appId不存在或者没有访问权限");
		}
		
		CommonResult<Group> result = new CommonResult<>(group);

		return result;
	}

	@RequestMapping("/delete")
	@ResponseBody
	@Transactional
	public Object delete(Long id) {
		Group group = groupService.selectById(id);
		User user = SessionUtil.getCurrentUser();
		if (group == null || !group.getUserId().equals(user.getId())) {
			// 不能修改其他用户的appkey
			return new CommonResult<>(CommonResult.FAIL, "appId不存在");
		}

		groupService.deleteById(group.getId());

		return new CommonResult<>(CommonResult.SUCCESS);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Object add(@Valid Group group) {
		group.setUserId(SessionUtil.getCurrentUserId());
		groupService.save(group);

		return new CommonResult<>(CommonResult.SUCCESS);
	}
}
