package top.it138.face.web;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.Page;

import top.it138.face.common.CommonResult;
import top.it138.face.entity.Group;
import top.it138.face.entity.Person;
import top.it138.face.entity.User;
import top.it138.face.service.GroupService;
import top.it138.face.service.PersonService;
import top.it138.face.util.SessionUtil;
import top.it138.face.util.StringUtil;
import top.it138.face.util.UuidUtil;
import top.it138.face.vo.PageRequest;

@Controller
@Validated
@RequestMapping("person")
public class PersonController {
	@Autowired
	private PersonService personService;
	@Autowired
	private GroupService groupService;

	@RequestMapping("/listByPage")
	public ModelAndView listByPage(PageRequest pageRequest, @NotNull Long groupId) {
		Page<Person> page = personService.selectPage(groupId, pageRequest.getPageNum(), pageRequest.getPageSize());
		ModelAndView mv = new ModelAndView("person/listByPage");
		mv.addObject("page", page);
		mv.addObject("persons", page.getResult());
		
		Group group = groupService.selectById(groupId);
		mv.addObject("group", group);

		return mv;
	}

	@RequestMapping("/edit")
	@ResponseBody
	@Transactional
	public Object edit(Person p) {
		Person person = personService.selectById(p.getId());
		User user = SessionUtil.getCurrentUser(); // 获取当前登录用户
		if (person == null || !user.getId().equals(person.getUserId())) {
			// 不能修改其他用户的appkey
			return new CommonResult<>("分组不存在或者没有访问权限");
		}
		person.setBirth(p.getBirth());
		person.setName(p.getName());
		person.setIdentity(p.getIdentity());
		person.setPhone(p.getPhone());
		person.setIdentification(p.getIdentification());
		
		personService.update(person);

		return new CommonResult<>(CommonResult.SUCCESS);
	}
	
	@RequestMapping("/byId")
	@ResponseBody
	public Object byId(long id) {
		Person person = personService.selectById(id);
		User user = SessionUtil.getCurrentUser(); // 获取当前登录用户
		if (person == null || !user.getId().equals(person.getUserId())) {
			// 不能修改其他用户的appkey
			return new CommonResult<>("分组不存在或者没有访问权限");
		}
		
		CommonResult<Person> result = new CommonResult<>(person);

		return result;
	}

	@RequestMapping("/delete")
	@ResponseBody
	@Transactional
	public Object delete(Long id) {
		Person person = personService.selectById(id);
		User user = SessionUtil.getCurrentUser();
		if (person == null || !person.getUserId().equals(user.getId())) {
			// 不能修改其他用户的appkey
			return new CommonResult<>("分组不存在");
		}

		personService.deleteById(person.getId());

		return new CommonResult<>(CommonResult.SUCCESS);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	@Transactional
	public Object add(@Valid Person person) {
		person.setUserId(SessionUtil.getCurrentUserId());
		Group group = groupService.selectById(person.getGroupId());
		if (group == null || !group.getUserId().equals(SessionUtil.getCurrentUserId())) {
			// 不能修改其他用户的
			return new CommonResult<>("分组不存在");
		}
		if (StringUtil.isEmpty(person.getIdentification())) {
			person.setIdentification(UuidUtil.getUUID());
		}
		personService.save(person);

		return new CommonResult<>(CommonResult.SUCCESS);
	}
	
	
	@InitBinder  
	public void initBinder(WebDataBinder binder) {  
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
	    dateFormat.setLenient(false);  
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));  
	}  
}
