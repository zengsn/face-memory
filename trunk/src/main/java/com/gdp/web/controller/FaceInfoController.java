package com.gdp.web.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gdp.entity.Config;
import com.gdp.entity.FaceInfo;
import com.gdp.entity.UserInfo;
import com.gdp.pojo.PastPhotoVO;
import com.gdp.service.ConfigService;
import com.gdp.service.DetectService;
import com.gdp.service.FaceInfoService;
import com.gdp.service.UserInfoService;
import com.gdp.util.AESUtil;
import com.gdp.util.FileUploadUtil;
import com.gdp.util.ImageZipUtil;
import com.gdp.util.ParaUtil;
import com.gdp.util.UploadUtil;
import com.gdp.util.face.Response;

/**
 * 人脸识别信息控制器
 * 
 * @author Jashon
 * @since 2018-07-26
 */
@RestController
@RequestMapping("/faceInfo")
public class FaceInfoController {

	private Logger logger = LoggerFactory.getLogger(FaceInfoController.class);
	
	@Autowired
	private FaceInfoService faceInfoService;
	@Autowired
	private ConfigService configService;
	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private DetectService detectService;

	/**
	 * 创建人脸识别记录
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/create")
	public Map<String, Object> createRecord(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		
		String openid = AESUtil.decrypt((String) request.getHeader("token"), "openid");
		UserInfo info = this.userInfoService.selectByPrimaryKey(openid);
		if (info.getStatus() == 3) {
			modelMap.put("result", "failed");
			modelMap.put("resultMsg", "存在需求处理的记录, 无法继续提交记录, 请先前往'识别结果'中处理");
			modelMap.put("id", "");
			return modelMap;
		}
		
		FaceInfo faceInfo = new FaceInfo();
		faceInfo.setWxid(openid);
		faceInfo.setCreateTime(Date.from(Instant.now()));
		faceInfo.setStatus(ParaUtil.FACE_INFO_CREATE);
		
		this.faceInfoService.insertSelective(faceInfo);
		int id = faceInfo.getId();	// 创建记录之后的主键 id
		logger.debug("faceinfo create new record id : {} ", id);
		
		modelMap.put("result", "succeed");
		modelMap.put("resultMsg", "创建记录成功");
		modelMap.put("id", id);
		return modelMap;
	}
	
	/**
	 * 上传图片到服务器
	 * 
	 * @param id
	 * @param photo
	 * @param request
	 * @return
	 */
	@RequestMapping("/upload")
	public Map<String, Object> uploadPhoto(Integer id, MultipartFile photo, HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		
		String openid = AESUtil.decrypt((String) request.getHeader("token"), "openid");
		String originalFileName = photo.getOriginalFilename();
		String pix = originalFileName.substring(originalFileName.lastIndexOf("."));
		
		String photoPath = openid + "/" + UploadUtil.getNewFileName(pix);
		String pathname = ParaUtil.UPLOAD_PATH + photoPath;
		
		boolean bol = UploadUtil.upload(photo, pathname);
		
		if(bol) {
			FaceInfo faceInfo = new FaceInfo();
			faceInfo.setId(id);
			faceInfo.setPhotoPath(photoPath);
			faceInfo.setStatus(ParaUtil.FACE_INFO_UPLOADED);
			this.faceInfoService.updateByPrimaryKeySelective(faceInfo);
			
        	// 生成缩略图
			ImageZipUtil.thumbnailImage(pathname, pathname.replace("uploads/", "uploads/abbr/"), "JPG",72, 72, false);
			logger.error("faceInfo:{}" + faceInfo.toString());
			modelMap.put("result", "succeed");
			modelMap.put("resultMsg", "图片上传成功");
		} else {
			modelMap.put("result", "failed");
			modelMap.put("resultMsg", "非常抱歉, 图片上传失败, 请反馈给我们! ");
		}
		
		return modelMap;
	}
	
	/**
	 * 开始人脸识别
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/detect")
	public Map<String, Object> detect(String id, HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		modelMap.put("first", false);
		
		String openid = AESUtil.decrypt((String) request.getHeader("token"), "openid");
		
		FaceInfo faceInfo = this.faceInfoService.selectByPrimaryKey(id);
		
		String nowPhotoPath = ParaUtil.UPLOAD_PATH + faceInfo.getPhotoPath();
		Response detectByFace = this.detectService.detectByFace(nowPhotoPath);
		logger.info("人脸识别结果返回值为:" + detectByFace.toString());
    	
    	// 转化返回值的字节结果为字符串
    	String resultContent = new String(detectByFace.getContent());
    	// 识别结果返回JSONObject
    	JSONObject jsonObject = JSONObject.parseObject(resultContent);
    	JSONArray faces = (JSONArray) jsonObject.get("faces");
    	
    	// 1. 识别照片中是否存在人脸
    	if(faces.size() == 0) {
    		faceInfo.setStatus(ParaUtil.FACE_INFO_NOT_CLEAR);
    		// 更新人脸信息状态
    		this.faceInfoService.updateByPrimaryKeySelective(faceInfo);
    		
    		modelMap.put("result", "pic_not_clear");
    		modelMap.put("resultMsg", "识别失败, 该照片中不包含人脸!建议删除该图片");
    		return modelMap;
    	}
    	
    	// 2. 获取识别的人脸属性值
		JSONObject face_rectangle = faces.getJSONObject(0).getJSONObject("face_rectangle");	// 获取人脸的矩形框
		JSONObject attributes = faces.getJSONObject(0).getJSONObject("attributes");	// 获取人脸属性
		JSONObject emotions= attributes.getJSONObject("emotion");			// 面部表情
		
		byte age = attributes.getJSONObject("age").getByteValue("value");	// 获取年龄 
		
		String gender = attributes.getJSONObject("gender").getString("value");
		double beauty = 0.0d;
		if("Male".equals(gender)) {
			beauty = attributes.getJSONObject("beauty").getDoubleValue("female_score");
		} else {
			beauty = attributes.getJSONObject("beauty").getDoubleValue("male_score");
		}
		// 建立数据对象, 准备保存进数据库
		faceInfo.setAge(age);
		faceInfo.setFaceValue(beauty);
		faceInfo.setCreateTime(new Date(System.currentTimeMillis()));
		faceInfo.setGender(gender);
		faceInfo.setFaceQuality(attributes.getJSONObject("facequality").toJSONString());
		String emotion = getEmotion(emotions);
		faceInfo.setEmotion(emotion);
		faceInfo.setSkinStatus(attributes.getString("skinstatus"));

        // 3. 查询用户信息, 用户是否已经确认第一张人脸
    	UserInfo userInfo = this.userInfoService.selectByPrimaryKey(openid);
    	if (userInfo.getStatus() == 0) {
    		// 用户状态为默认值, 即第一次拍照
    		// 3.1 该图片的人脸是第一张人脸, 更新用户状态为未确认 - 待操作
    		userInfo.setStatus(3);
    		int i = this.userInfoService.updateByPrimaryKeySelective(userInfo);
    		
    		faceInfo.setStatus(ParaUtil.FACE_INFO_WAITING);
    		int j = this.faceInfoService.updateByPrimaryKeySelective(faceInfo);
    		
    		if(1 == (i & j)) {
    			modelMap.put("result", "succeed");
    			modelMap.put("first", true);
    			modelMap.put("resultMsg", "识别成功");
    		} else {
    			modelMap.put("result", "succeed_but_no_save");
    			modelMap.put("resultMsg", "识别成功但人脸信息保存失败, 请联系管理员处理");
    		}
		} else {
			// 3.2 获取用户确认的第一张人脸信息
			String orderBy = "create_time asc";
			List<FaceInfo> list = this.faceInfoService.selectByOpenidAndStatus(openid, ParaUtil.FACE_INFO_FINISH, orderBy);
			
			if (list != null && list.size() != 0) {
				// 对比人脸
				String photopath = ParaUtil.UPLOAD_PATH + list.get(0).getPhotoPath();
				
				Response compareTwoFace = this.detectService.compareTwoFace(photopath, nowPhotoPath);
				logger.info("两张人脸照片对比的结果返回值为: " + compareTwoFace.toString());
				
    			String compareResult = new String(compareTwoFace.getContent());
    			
    			JSONObject j = JSONObject.parseObject(compareResult);
    			// 一组用于参考的置信度阈值
    			// 低于“千分之一”阈值则不建议认为是同一个人；如果置信值超过“十万分之一”阈值，则是同一个人的几率非常高
    			JSONObject json = j.getJSONObject("thresholds");

    			float threshold = json.getBigDecimal("1e-5").floatValue();
    			// 是同一个人的置信度
    			float confidence = j.getBigDecimal("confidence").floatValue(); 
    			
    			// 3.  是否是同个人的照片
    			if(confidence > threshold) {
//    				// 是同一个人
    	    		faceInfo.setStatus(ParaUtil.FACE_INFO_FINISH);	// 更新人脸识别信息为 完成.
    	    		
    				modelMap.put("result", "succeed");
    				modelMap.put("resultMsg", "识别成功");
    				modelMap.put("info", faceInfo);
    				modelMap.put("face_rectangle", face_rectangle);
    				modelMap.put("skinstatus", attributes.getJSONObject("skinstatus"));
    				modelMap.put("emotion", emotion);
    			} else {
    				faceInfo.setStatus(ParaUtil.FACE_INFO_NOSELF);	// 更新人脸识别信息为 不是本人
    				
    				modelMap.put("result", "not_the_same_human");
    				modelMap.put("resultMsg", "识别失败, 请拍本人人脸图片! 建议删除该图片");
    			}
    			this.faceInfoService.updateByPrimaryKeySelective(faceInfo);
			}
		}
    	
    	return modelMap;
	}
	
	/**
	 * 用户确认该照片是本人
	 * 
	 * @param id
	 * @param status
	 * @return
	 */
	@RequestMapping("/decide")
	public Map<String, Object> decide(Integer id, HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		String openid = AESUtil.decrypt((String) request.getHeader("token"), "openid");
		
		FaceInfo faceInfo = this.faceInfoService.selectByPrimaryKey(id);
		logger.info("用户确认该照片是本人, 该照片下信息为: {}; 更新 status 为 0", faceInfo.toString());
		
		// 更新人脸信息 status: 3 为 status
		faceInfo.setStatus(ParaUtil.FACE_INFO_FINISH);
		int i = this.faceInfoService.updateByPrimaryKeySelective(faceInfo);
		// 更新用户状态为 1
		UserInfo userInfo = new UserInfo();
		userInfo.setOpenid(openid);
		userInfo.setStatus(1);
		int j = userInfoService.updateByPrimaryKeySelective(userInfo);
		
		if((i & j) == 1) {
			modelMap.put("result", "succeed");
			modelMap.put("resultMsg", "确认本人人脸成功!");
		}
		
		return modelMap;
	}

	/**
	 * 获取历史照片数据
	 * 
	 * @param request
	 * @param pageNum
	 * @return
	 */
	@RequestMapping("/getPastPhoto")
	public Map<String, Object> listPastPhoto(HttpServletRequest request, Integer pageNum) {
	    Map<String, Object> res = new HashMap<String, Object>();
	    
	    String openid = AESUtil.decrypt((String) request.getHeader("token"), "openid");
		logger.debug("openid ： {}", openid);

		// 获取本地服务器的主机域名地址
		Config config = configService.selectByPrimaryKey("domain_name");
		ParaUtil.domain_name = config.getValuees();

		String s = ParaUtil.domain_name + "uploads/";
		String ss = ParaUtil.domain_name + "uploads/abbr/";
		
		// 分页查询记录, 每次只返回 10 条记录
		List<FaceInfo> lists = this.faceInfoService.listFaceInfoByOpenIdWithPage(openid, pageNum);
		if(lists != null && lists.size() != 0) {
			logger.info("list: " + lists.toString());
			
			List<PastPhotoVO> pastPhotoVOs = new ArrayList<PastPhotoVO>();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			
			// 利用迭代器分别处理 url 并封装数据
			String[] urls = new String [lists.size()];
			String [] abbr_url = new String [lists.size()];
			
			Iterator<FaceInfo> iterator = lists.iterator();
			int i = 0;
			while(iterator.hasNext()) {
				FaceInfo faceInfo = iterator.next();
				
				StringBuilder sBuilder = new StringBuilder(s);
				sBuilder.append(faceInfo.getPhotoPath());
				urls[i] = sBuilder.toString();
				
				StringBuilder sb = new StringBuilder(ss);
				sb.append(faceInfo.getPhotoPath());
				abbr_url[i] = sb.toString();
				/****************************/
				/** 封装返回给页面的数据对象 **/
				PastPhotoVO pastPhotoVO = new PastPhotoVO();
				pastPhotoVO.setAge(faceInfo.getAge());
				pastPhotoVO.setFaceValue(faceInfo.getFaceValue());
				pastPhotoVO.setHealthValue(faceInfo.getHealthValue());
				pastPhotoVO.setCreatetime(simpleDateFormat.format(faceInfo.getCreateTime()));
				pastPhotoVO.setEmotion(faceInfo.getEmotion());
				pastPhotoVO.setId(faceInfo.getId());
				pastPhotoVO.setSkinStatus(JSONObject.parseObject(faceInfo.getSkinStatus()));
				pastPhotoVOs.add(pastPhotoVO);
				/****************************/
				i++;
			}
		 		
			JSONArray jsonArray = (JSONArray) JSONArray.toJSON(pastPhotoVOs);
			res.put("result", "succeed");
			// 返回压缩图片地址, 若小程序图片加载慢时, 可返回缩略图链接, 小图使用缩略图, 预览使用大图链接
			res.put("abbr_urls", abbr_url);
			res.put("urls", urls);
			res.put("faceinfo", jsonArray);
			logger.debug("获取历史照片数据: jsonArray: {}", jsonArray);
			logger.debug("photo http url size : {}", urls.length);
		} else {
			res.put("result", "failed");
			res.put("resultMsg", "沒有更多数据了!");
		}
		return res;
	}

	
	/**
	 * 获取历史照片数据, 用户图标显示
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/listAllForCharts")
	public Map<String, Object> listAllForCharts(HttpServletRequest request) {
	    Map<String, Object> res = new HashMap<String, Object>();
	    
	    String openid = AESUtil.decrypt((String) request.getHeader("token"), "openid");
		logger.debug("openid ： {}", openid);

		List<FaceInfo> list = this.faceInfoService.listFaceInfoByOpenId(openid);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		List<PastPhotoVO> todayList = new ArrayList<>(list.size());
		list.stream().forEach((t) -> {
			PastPhotoVO pastPhotoVO = new PastPhotoVO();
			pastPhotoVO.setAge(t.getAge());
			pastPhotoVO.setFaceValue(t.getFaceValue());
			pastPhotoVO.setHealthValue(t.getHealthValue());
			pastPhotoVO.setCreatetime(simpleDateFormat.format(t.getCreateTime()));
			pastPhotoVO.setEmotion(t.getEmotion());
			pastPhotoVO.setId(t.getId());
			pastPhotoVO.setSkinStatus(JSONObject.parseObject(t.getSkinStatus()));
			
			todayList.add(pastPhotoVO);
		});
		
		JSONArray jsonArray = (JSONArray) JSONArray.toJSON(todayList);
		res.put("result", "succeed");
		res.put("faceinfo", jsonArray);
		logger.debug("获取历史照片数据: jsonArray: {}", jsonArray);
		return res;
	}
	
	
    /**
     * 删除一张照片和历史记录
     * json/id 必传一个
     *
     * @param json
     * @param id
     * @param first
     * @param request
     * @return
     */
	@RequestMapping("/deletePic")
	public Map<String, Object> deletePictureUnknown(
	        @RequestParam(value = "json", required = false, defaultValue = "") String json,
            @RequestParam(value = "id", required = false, defaultValue = "") Integer id,
            @RequestParam(value = "first", required = false, defaultValue = "0") Integer first,
            HttpServletRequest request) {
		String openid = AESUtil.decrypt((String) request.getHeader("token"), "openid");
		Map<String, Object> res = new HashMap<>();

		FaceInfo faceInfo = null;
		if("".equals(json)){
            faceInfo = faceInfoService.selectByPrimaryKey(id);
        } else {
            faceInfo = JSONObject.parseObject(json, FaceInfo.class);
        }

		String destPath = ParaUtil.UPLOAD_PATH + faceInfo.getPhotoPath();
		String abbrDestPath = ParaUtil.UPLOAD_PATH + "abbr/" + faceInfo.getPhotoPath();

		int i = faceInfoService.deleteFaceInfoById(faceInfo.getId());
		boolean b = FileUploadUtil.deleteFile(destPath);
		boolean d = FileUploadUtil.deleteFile(abbrDestPath);
		logger.info("删除图片: {} ,结果: {}", destPath, b);
		logger.info("删除缩略图: {} ,结果: {}", abbrDestPath, d);

		if(b && d && (i==1)) {
			logger.info("人脸图片和记录删除成功!");
		}
		
		List<FaceInfo> list = faceInfoService.listByOpenIdForAdmin(openid);
		if(list == null || first == 1 || faceInfo.getStatus() == 3) {
			// 恢复用户状态为初始值
			UserInfo userInfo = new UserInfo();
			userInfo.setOpenid(openid);
			userInfo.setStatus(0);
			userInfoService.updateByPrimaryKeySelective(userInfo);
		}
		
        res.put("result", "succeed");
		return res;
	}
	
	/**
	 * 根据 id 获取单条人脸识别后的信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/get")
	public Map<String, Object> getFaceInfo(int id, HttpServletRequest request, HttpServletResponse httpServletResponse) {
		Map<String, Object> modelMap = new HashMap<>();
		FaceInfo faceInfo = this.faceInfoService.selectByPrimaryKey(id);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		PastPhotoVO pastPhotoVO = new PastPhotoVO();
		pastPhotoVO.setAge(faceInfo.getAge());
		pastPhotoVO.setFaceValue(faceInfo.getFaceValue());
		pastPhotoVO.setHealthValue(faceInfo.getHealthValue());
		pastPhotoVO.setCreatetime(simpleDateFormat.format(faceInfo.getCreateTime()));
		pastPhotoVO.setEmotion(faceInfo.getEmotion());
		pastPhotoVO.setId(faceInfo.getId());
		pastPhotoVO.setSkinStatus(JSONObject.parseObject(faceInfo.getSkinStatus()));
		
		// 设置 response 中的 Cache-Control 进行http缓存 ( 30天 )
		httpServletResponse.addHeader("Cache-control", "max-age=2592000");
		modelMap.put("info", pastPhotoVO);
		
		return modelMap;
	}

	/**
	 * 列出待操作的 + 当天的人脸识别结果
	 * 
	 * @return
	 */
	@RequestMapping("/listResult")
	public Map<String, Object> listResult(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		modelMap.put("faceinfo", "");
		modelMap.put("urls", "");
		modelMap.put("delFaceInfo", "");	// 不含人脸或人脸不是本人的图片信息
		modelMap.put("delUrls", "");	// 不含人脸或人脸不是本人的图片URL
		
		String openid = AESUtil.decrypt((String) request.getHeader("token"), "openid");
		
		// 获取本地服务器的主机域名地址
		Config config = configService.selectByPrimaryKey("domain_name");
		ParaUtil.domain_name = config.getValuees();
		String prefix = ParaUtil.domain_name + "uploads/";
		
		UserInfo userInfo = this.userInfoService.selectByPrimaryKey(openid);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		/*
		 * 用户未确认第一张人脸是本人, 只返回人脸图片、id、status信息
		 */
		if(userInfo.getStatus() == ParaUtil.FACE_INFO_WAITING) {
			List<FaceInfo> face = this.faceInfoService.selectByOpenidAndStatus(openid, ParaUtil.FACE_INFO_WAITING, null);
			JSONObject jsonObject = new JSONObject();
			if(face != null && face.size() != 0) {
				FaceInfo faceInfo = face.get(0);
				jsonObject.put("id", faceInfo.getId());
				jsonObject.put("url", prefix + faceInfo.getPhotoPath());
				jsonObject.put("status", ParaUtil.FACE_INFO_WAITING);
				jsonObject.put("createtime", simpleDateFormat.format(faceInfo.getCreateTime()));
				modelMap.put("faceinfo", jsonObject);
			}
			modelMap.put("result", "waiting");
			return modelMap;
		}

		// 1.获取当天识别成功的记录
		List<FaceInfo> list = this.faceInfoService.listTodayFinished(openid);
		
		List<PastPhotoVO> todayList = new ArrayList<>(list.size());
		List<String> urls = new ArrayList<>(list.size());	// 图片地址 url

		if(list != null && list.size() != 0) {
			list.stream().forEach((t) -> {
				PastPhotoVO pastPhotoVO = new PastPhotoVO();
				pastPhotoVO.setAge(t.getAge());
				pastPhotoVO.setFaceValue(t.getFaceValue());
				pastPhotoVO.setHealthValue(t.getHealthValue());
				pastPhotoVO.setCreatetime(simpleDateFormat.format(t.getCreateTime()));
				pastPhotoVO.setEmotion(t.getEmotion());
				pastPhotoVO.setId(t.getId());
				pastPhotoVO.setSkinStatus(JSONObject.parseObject(t.getSkinStatus()));
				
				todayList.add(pastPhotoVO);
				urls.add(prefix + t.getPhotoPath());
			});
			
			modelMap.put("faceinfo", (JSONArray) JSONArray.toJSON(todayList));
			modelMap.put("urls", (JSONArray) JSONArray.toJSON(urls));
		}
		
		// 查询 faceInfo 状态为 4或5 的记录
		List<FaceInfo> listShouldBedeleted = this.faceInfoService.listShouldBedeleted(openid);
		if (listShouldBedeleted != null && listShouldBedeleted.size() != 0) {
			JSONArray delArray = new JSONArray(listShouldBedeleted.size());
			List<String> delUrls = new ArrayList<>(listShouldBedeleted.size());
			listShouldBedeleted.stream().forEach((faceInfo) -> {
				JSONObject obj = new JSONObject();
				obj.put("createtime", simpleDateFormat.format(faceInfo.getCreateTime()));
				obj.put("id", faceInfo.getId());
				obj.put("status", faceInfo.getStatus());
				delArray.add(obj);
				delUrls.add(prefix + faceInfo.getPhotoPath());
			});
			modelMap.put("delFaceInfo", delArray);
			modelMap.put("delUrls", delUrls);
		}
		
		modelMap.put("result", "succeed");
		return modelMap;
	}
	
    
    
	/**
	 * 提取识别的表情结果
	 * 
	 * @param emotion
	 * @return
	 */
	private String getEmotion(JSONObject emotion) {
		Set<Map.Entry<String,Object>> map = emotion.entrySet();
		Iterator<Entry<String, Object>> iterator = map.iterator();
		
		String result = "";
		while(iterator.hasNext()) {
			Entry<String, Object> obj = iterator.next();
			BigDecimal value = (BigDecimal) obj.getValue();
			if(value.compareTo(new BigDecimal(50)) > 0) {
				String key = obj.getKey();
				switch (key) {
				case "anger":
					result = "愤怒";
					break;
				case "disgust":
					result = "厌恶";
					break;
				case "fear":
					result = "恐惧";
					break;
				case "happiness":
					result = "高兴";
					break;
				case "neutral":
					result = "平静";
					break;
				case "sadness":
					result = "伤心";
					break;
				case "surprise":
					result = "惊讶";
					break;
				default:
					break;
				}
			}
			if(result != "") {
				return result;
			}
		}

		return "面无表情";
	}
}
