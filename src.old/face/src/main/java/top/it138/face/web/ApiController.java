package top.it138.face.web;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import top.it138.face.common.CommonResult;
import top.it138.face.dto.FindForm;
import top.it138.face.dto.PersonWithPath;
import top.it138.face.entity.App;
import top.it138.face.entity.Group;
import top.it138.face.entity.Person;
import top.it138.face.exception.APIException;
import top.it138.face.exception.RecognitionException;
import top.it138.face.service.AppService;
import top.it138.face.service.GroupService;
import top.it138.face.service.NetworkService;
import top.it138.face.service.PersonService;
import top.it138.face.service.RecognitionService;
import top.it138.face.util.RequestUtil;
import top.it138.face.util.SessionUtil;

@RestController
@RequestMapping("/api/2.0/")
@Validated
@Transactional
public class ApiController {
	private final Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private PersonService personService;
	@Autowired
	private AppService appService;
	@Autowired
	private RecognitionService recognitionService;
	@Autowired
	private NetworkService networkService;
	@Autowired
	private GroupService groupService;
	
	@RequestMapping(value = "app", method = RequestMethod.GET)
	public boolean getApp(String appKey, String appSecret) {
		App app = appService.selectByAppKey(appKey);
		if (app.getAppSecret().equals(appSecret)) {
			return true;
		}
		
		return false;
	}
	
	@RequestMapping(value = "compareBase64", method = RequestMethod.POST)
	public Object compareBase64(@RequestBody Map<String, String> map) throws Exception {
		String img1 = map.get("img1");
		String img2 = map.get("img2");
		
		byte[] bys1 = Base64.decodeBase64(img1);
		
		byte[] bys2 = Base64.decodeBase64(img2);
		log.info("接收图片成功，大小img1：" + bys1.length + ", im2:" +bys2.length);
		return compareByteArray(bys1, bys2);
	}
	
	public Object compareByteArray(byte[] bys1, byte[] bys2) throws Exception{
		long t1 = System.currentTimeMillis();
		log.info("正在识别");
		double dis = recognitionService.distance(bys1, bys2);
		CommonResult<Double> result = new CommonResult<Double>(true, dis);
		long t2 = System.currentTimeMillis();
		log.info("识别成功,用时:" + (t2 - t1) + "ms, 结果：" + result.getData());
		return result;
	}
	
	@RequestMapping(value = "compareURL", method = RequestMethod.POST)
	public Object CompareUrl(@RequestBody Map<String, String> map) throws Exception{
		String img1Url = map.get("img1Url");
		String img2Url = map.get("img2Url");
		byte[] bys1 = networkService.getByteArrayByURL(img1Url);
		byte[] bys2 = networkService.getByteArrayByURL(img2Url);
		return compareByteArray(bys1, bys2);
	}
	
	@RequestMapping(value = "group", method = RequestMethod.GET)
	public Object getAllGroups(@RequestHeader String appKey) {
		List<Group> groups = groupService.selectByAppKey(appKey);
		CommonResult<List<Group>> result = new CommonResult<>(true, groups);
		return result;
	}
	
	@RequestMapping(value = "{groupId}/person", method = RequestMethod.GET)
	public Object getGroupPersons(@NotNull @PathVariable Long groupId) throws APIException{
		List<PersonWithPath> personWithPath = getGroupPersonWithPath(groupId);
		CommonResult<List<PersonWithPath>> result = new CommonResult<>(true, personWithPath);
		return result;
	}

	private List<PersonWithPath> getGroupPersonWithPath(Long groupId) throws APIException {
		List<Person> persons = personService.selectByGroupId(groupId);
		//校验权限
		if (!persons.isEmpty()) {
			if (!persons.get(0).getUserId().equals(SessionUtil.getCurrentUserId())) {
				throw new APIException("禁止访问");
			}
		}
		List<PersonWithPath> personWithPath = personService.parseTo(RequestUtil.getRequest(), persons);
		return personWithPath;
	}
	
	@RequestMapping(value = "/person", method = RequestMethod.GET)
	public Object getAllPersons(@RequestHeader String appKey) throws APIException{
		List<PersonWithPath> list = getAllPersonWithPath(appKey);
		
		CommonResult<List<PersonWithPath>> result = new CommonResult<>(true, list);
		return result;
	}

	private List<PersonWithPath> getAllPersonWithPath(String appKey) throws APIException {
		List<Group> groupList = groupService.selectByAppKey(appKey);
		List<PersonWithPath> list = new ArrayList<>();
		for (Group g : groupList) {
			List<PersonWithPath> pwpList = getGroupPersonWithPath(g.getId());
			list.addAll(pwpList);
		}
		return list;
	}
	
	@RequestMapping(value = "/find", method = RequestMethod.POST)
	public Object find(@RequestHeader String appKey,@RequestBody FindForm form) throws APIException{
		//不能同时为空，不同同时有值,if判断是数字逻辑不用深究
		boolean bool1 = form.getImgBase64() != null;
		boolean bool2 = form.getImgUrl() != null;
		if ((bool1 && bool2) && !(bool1 || bool2)) {
			//不满足
			//同时为真或者同时为假
			throw new APIException("URL和BASE64不能同时为空，也不能同时有值");
		}
		
		//获取照片字节数组
		byte[] unknowFaceBys = null;
		if (form.getImgBase64() != null) {
			unknowFaceBys = Base64.decodeBase64(form.getImgBase64());
		} else {
			try {
				unknowFaceBys = networkService.getByteArrayByURL(form.getImgUrl());
			} catch (IOException e) {
				log.error("下载图片错误", e);
				throw new APIException("无法从imgUrl下载图片");
			}
		}
		
		List<PersonWithPath> pwpList = null;
		//构造Person照片放到List
		if (form.getIsAllPerson()) {
			pwpList = getAllPersonWithPath(appKey);
		} else {
			pwpList = new ArrayList<>();
			List<Long> groupIdList = form.getGroupId();
			
			//用set去除重复
			Set<Long> set = new HashSet<>();
			for (Long id :groupIdList) {
				set.add(id);
			}
			
			//构造
			for (Long id : set) {
				pwpList.addAll(getGroupPersonWithPath(id));
			}
		}
		
		//去除没有照片的
		for (int i = 0; i < pwpList.size(); i++) {
			PersonWithPath pwp = pwpList.get(i);
			if (pwp.getPaths() == null || pwp.getPaths().size() == 0) {
				pwpList.remove(i);
			}
		}
		
		//获取照片路径
		List<String> facePath = new ArrayList<>();
		for (PersonWithPath pwp : pwpList) {
			facePath.add(pwp.getPaths().get(0));
		}
		
		//连接python服务器识别
		Double[] distance;
		try {
			distance = recognitionService.distance(facePath.toArray(new String[0]), unknowFaceBys);
		} catch (RecognitionException e) {
			log.error("连接python服务器发生异常", e);
			throw new APIException("服务器异常");
		}
		
		//对结果查找
		//找到可能性最大的
		double max = 0;
		int maxIndex = -1;
		for (int i = 0; i < distance.length; i++) {
			if (distance[i] > max) {
				maxIndex = i;
				max = distance[i];
			}
		}
		if (maxIndex == -1) {
			//
			return new CommonResult<>("没有找到");
		}
		
		//找到目标任务
		PersonWithPath targetPerson = pwpList.get(maxIndex);
		//TODO 这里识别目标任务所有图片
		
		return new CommonResult<PersonWithPath>(true, targetPerson);
	}
}
