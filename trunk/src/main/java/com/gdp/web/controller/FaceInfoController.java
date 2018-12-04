package com.gdp.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.gdp.entity.Config;
import com.gdp.entity.FaceInfo;
import com.gdp.pojo.PastPhotoVO;
import com.gdp.service.ConfigService;
import com.gdp.service.FaceInfoService;
import com.gdp.util.FileUploadUtil;
import com.gdp.util.ParaUtil;

import net.sf.json.JSONArray;

/**
 * 人脸识别信息控制器
 * 
 * @author Jashon
 * @since 2018-07-26
 */
@Controller
@RequestMapping("/faceInfo")
public class FaceInfoController {

	private Logger logger = Logger.getLogger(FaceInfoController.class);
	
	@Autowired
	private ConfigService configService;
	@Autowired
	private FaceInfoService faceInfoService;

    /**
     * 检查是否该 openid 是否是第一次使用小程序拍照
     *
     * @param openid
     * @return
     */
	@RequestMapping("/checkFirst")
    @ResponseBody
	public Map<String, Object> checkFirst(String openid) {
	    Map<String, Object> res = new HashMap<>();

	    List<FaceInfo> list = faceInfoService.listFaceInfoByOpenId(openid);
        if(list.size() == 0){
            res.put("result", true);
        } else {
            res.put("result", false);
        }

	    return res;
    }

	/**
	 * 保存用户照片识别后的信息
	 * 
	 * @param openid		用户唯一标识
	 * @param age			识别年龄
	 * @param faceValue		识别颜值
	 * @param filepath		上传的照片保存的路径
	 * @return
	 */
	@RequestMapping("/saveFaceInfo")
	@ResponseBody
	public Map<String, Object> saveUserFaceInfo(String openid, byte age, double faceValue, String filepath){
		Map<String, Object> res = new HashMap<String, Object>();
		
		FaceInfo faceInfo = new FaceInfo();
        faceInfo.setAge(age);
        faceInfo.setFaceValue(faceValue);
        faceInfo.setPhotoPath(filepath);
        faceInfo.setCreateTime(new Date(System.currentTimeMillis()));
        faceInfo.setWxid(openid);
        
        int i = faceInfoService.saveFaceInfo(faceInfo);
        if(i > 0) {
        	res.put("result", "succeed");
        	res.put("recondId", i);
        } else {
        	res.put("result", "false");
        }
        return res;
	}
	
	/**
	 * 获取历史照片数据
	 * 
	 * @param openid
	 * @return
     *
	 */
	@RequestMapping("/getPastPhoto")
	@ResponseBody
	@RequiresRoles("user")
	public Map<String, Object> listPastPhoto(String openid, @RequestParam(value = "status", required = false, defaultValue = "-1") Integer face_status) {

		logger.info("status: " + face_status);
	    Map<String, Object> res = new HashMap<String, Object>();
		logger.info("openid ： " + openid);
		// 获取本地服务器的主机地址
/*		String hostAddress = "";
		try {
			InetAddress address = InetAddress.getLocalHost();
			hostAddress = address.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
//		String s = "http://" + hostAddress + ":8880/facememory/uploads/" + openid;10.200.131.192
*/		
//		if("".equals(ParaUtil.domain_name)) {
			Config config = configService.getConfig("domain_name");
			ParaUtil.domain_name = config.getValuees();
//		}
		String s = ParaUtil.domain_name + "uploads/" + openid;
		String ss = ParaUtil.domain_name + "uploads/abbr/" + openid;
		
		List<FaceInfo> lists = null;
		if(face_status != -1){
            lists = faceInfoService.listFaceInfoUncheck(openid, face_status);
        } else {
            lists = faceInfoService.listFaceInfoByOpenId(openid);
        }

		List<PastPhotoVO> pastPhotoVOs = new ArrayList<PastPhotoVO>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		// 利用迭代器分别处理 url 并封装数据
		String[] urls = new String [lists.size()];
		String [] abbr_url = new String [lists.size()];
		
		Iterator<FaceInfo> iterator = lists.iterator();
		int i=0;
		while(iterator.hasNext()) {
			FaceInfo faceInfo = iterator.next();
			StringBuilder sBuilder = new StringBuilder(s);
			sBuilder.append(faceInfo.getPhotoPath().split(openid)[1]);
			StringBuilder sb = new StringBuilder(ss);
			sb.append(faceInfo.getPhotoPath().split(openid)[1]);
			urls[i] = sBuilder.toString();
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

		JSONArray jsonArray = JSONArray.fromObject(pastPhotoVOs);
		res.put("abbr_urls", abbr_url);
		res.put("urls", urls);
		res.put("faceinfo", jsonArray);
		System.out.println("jsonArray: " + jsonArray);
		System.out.println("--> http photo path : " + urls.length);
		return res;
	}

	// 没有使用 
//	@RequestMapping("/getInfoForChart")
//	@ResponseBody
//	public Map<String, Object> getInfoForChart(String openid){
//		Map<String, Object> res = new HashMap<String, Object>();
//		List<FaceInfo> lists = faceInfoService.listFaceInfoByOpenId(openid);
//		
//		return res;
//	}

    /**
     * 删除一张照片和历史记录
     * json/id 必传一个
     *
     * @param json
     * @param id
     * @param request
     * @return
     */
	@RequestMapping("/deletePic")
	@ResponseBody
	public Map<String, Object> deletePictureUnknown(
	        @RequestParam(value = "json", required = false, defaultValue = "") String json,
            @RequestParam(value = "id",required = false, defaultValue = "") Integer id,
            HttpServletRequest request) {
        System.out.println("json " + json);
        System.out.println("id " + id);

		Map<String, Object> res = new HashMap<>();
		String path = request.getSession().getServletContext().getRealPath("uploads/");
// 本地IDEA 环境测试由于项目的路径映射，需要增加此字段，上线不需要
//		path = path.replace("ROOT\\","");


		logger.info("要删除的照片的根路径：" + path);
		FaceInfo faceInfo = null;
		if("".equals(json)){
            faceInfo = faceInfoService.selectFaceInfoById(id);
        } else {
            faceInfo = JSONObject.parseObject(json, FaceInfo.class);
        }

		String destPath = path + faceInfo.getPhotoPath();
		String abbrDestPath = path + "abbr/" + faceInfo.getPhotoPath();

		int i = faceInfoService.deleteFaceInfoById(faceInfo.getId());
        System.out.println(destPath);
        System.out.println("abbrDestPath" + abbrDestPath);
		boolean b = FileUploadUtil.deleteFile(destPath);
		boolean d = FileUploadUtil.deleteFile(abbrDestPath);
        System.out.println("b:" + b);
        System.out.println("d:" + d);
        System.out.println("i:" + i);
		if(b && d && (i==1) ) {
			logger.info("人脸图片和记录删除成功!");

		}
        res.put("result", "succeed");
		return res;
	}

	/**
	 * 第一张人脸照片确认后保存信息
	 * 
	 * @param json
	 * @param nickName
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Map<String, Object> saveFaceInfo(String json, String nickName){
		Map<String, Object> res = new HashMap<String, Object>();
		
		FaceInfo faceInfo = JSONObject.parseObject(json, FaceInfo.class);
        faceInfo.setNickName(nickName);
        
        int i = faceInfoService.saveFaceInfo(faceInfo);
        if(i>0) {
        	res.put("result", "succeed");
        	res.put("recondId", i);
        } else {
        	res.put("result", "false");
        }
        return res;
	}
	
	/**
	 * 根据 id 获取单条人脸识别后的信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/get")
	@ResponseBody
	public Map<String, Object> getFaceInfo(int id) {
		Map<String, Object> res = new HashMap<>();
		FaceInfo faceInfo = faceInfoService.selectFaceInfoById(id);
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		PastPhotoVO pastPhotoVO = new PastPhotoVO();
		pastPhotoVO.setAge(faceInfo.getAge());
		pastPhotoVO.setFaceValue(faceInfo.getFaceValue());
		pastPhotoVO.setHealthValue(faceInfo.getHealthValue());
		pastPhotoVO.setCreatetime(simpleDateFormat.format(faceInfo.getCreateTime()));
		pastPhotoVO.setEmotion(faceInfo.getEmotion());
		pastPhotoVO.setId(faceInfo.getId());
		pastPhotoVO.setSkinStatus(JSONObject.parseObject(faceInfo.getSkinStatus()));
		
		res.put("info", pastPhotoVO);
		logger.info("根据id获取到的faceinfo:" + pastPhotoVO.toString());
//		System.out.println(net.sf.json.JSONObject.fromObject(pastPhotoVO));
		return res;
	}

    /**
     * 更新照片状态为已查看
     *
     * @param id
     * @return
     */
    @RequestMapping("/check")
    @ResponseBody
	public Map<String, Object> check(Integer id){
	    Map<String, Object> res = new HashMap<>();
	    FaceInfo faceInfo = new FaceInfo();
	    faceInfo.setId(id);
	    faceInfo.setStatus(1);
	    int i = faceInfoService.updateFaceInfoById(faceInfo);
	    if(i == 1){
	        res.put("result", "succeed");
        }
	    return res;
    }
}
