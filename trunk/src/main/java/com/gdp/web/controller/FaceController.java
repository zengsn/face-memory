package com.gdp.web.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
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
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gdp.entity.FaceInfo;
import com.gdp.pojo.FaceDetectVO;
import com.gdp.service.DetectService;
import com.gdp.service.FaceInfoService;
import com.gdp.util.FileUploadUtil;
import com.gdp.util.ImageZipUtil;
import com.gdp.util.RealPathUtils;
import com.gdp.util.face.Response;


/**
 * 人脸识别 控制器
 * 
 * @author Jashon
 * @since 2018-07-15
 */
@RestController
@RequestMapping("/face")
public class FaceController {

	private Logger logger = LoggerFactory.getLogger(FaceController.class);
	
	@Autowired
	private DetectService detectService;
	@Autowired
	private FaceInfoService faceInfoService;
	
	/**
	 * 保存客户端上传的图片(文件)到服务器
	 * 调用 百度人脸识别接口 进行识别
	 * [暂没使用]
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/uploadImage")
	public Map<String, Object> uploadpic(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		System.out.println("request come in");
		
		HashMap<String, Object> res = new HashMap<String, Object>();
		// 上传照片到服务器
        Map<String, Object> map = FileUploadUtil.fileUpload(request);
        
//-->	// 获取服务器 uploads/ 本地路径
        String path = RealPathUtils.uploadsPath;
        
        // 判断 人脸识别 是否成功
        if((Boolean) map.get("isSuccess")) {
        	// 获取照片保存路径
        	String filePath = (String) map.get("picPath");
        	// 将照片保存路径传入百度人脸识别
        	String reString = detectService.detect(path + filePath);
        	
            JSONObject jsonObject = JSONObject.parseObject(reString);
            // 封装百度识别接口的返回值
            FaceDetectVO fDetectVO = JSONObject.parseObject(reString, FaceDetectVO.class);
            
            if(jsonObject.getString("result") != "null") {
            	System.out.println("fDetectVO.getResult().getFace_list().size():" + fDetectVO.getResult().getFace_list().size());
            	double faceValue = fDetectVO.getResult().getFace_list().get(0).getBeauty();
                Integer age = fDetectVO.getResult().getFace_list().get(0).getAge();
                
                res.put("face_value", faceValue);
                res.put("age", age.intValue()); 
                res.put("filepath", filePath);

                res.put("result", "succeed");
            }else {
            	System.out.println("识别失败");
            	res.put("result", "pic_not_clear");
            }
        } else {
        	res.put("result", "upload at fault");
        }
        
        return res;
    }
	
	/**
	 * Face++ 接口人脸识别
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("/detect")
	public Map<String, Object> detectFace(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		
		Map<String, Object> res = new HashMap<>();
		res.put("result", "");
		// 上传照片到服务器
        Map<String, Object> map = FileUploadUtil.fileUpload(request);
        
    	// 服务器 uploads/ 文件夹的本地路径
        String path = RealPathUtils.uploadsPath;

        // 照片是否成功写入到服务器硬盘上
        if((Boolean) map.get("isSuccess")) {
        	// 获取照片保存的本地路径
        	String destPath = path + (String) map.get("picPath");
        	// 生成缩略图
			ImageZipUtil.thumbnailImage(destPath, destPath.replace("uploads/", "uploads/abbr/"), "JPG",72, 72, false);

        	logger.info("上传识别的照片所在本地地址为: " + destPath);
        	
        	// 接收识别后的返回值
        	Response r = detectService.detectByFace(destPath);
        	logger.info("人脸识别结果返回值为:" + r.toString());
        	
        	// 转化返回值的字节结果为字符串
        	String result = new String(r.getContent());
        	// 识别结果返回JSONObject
        	JSONObject jsonObject = JSONObject.parseObject(result);
        	JSONArray faces = (JSONArray) jsonObject.get("faces");
        	
        	// 1. 识别照片中是否存在人脸
        	if(faces.size() == 0) {
        		res.put("res", "识别失败, 该照片中不包含人脸!");
        		res.put("result", "pic_not_clear");
        		// 删除本地照片
        		boolean b = FileUploadUtil.deleteFile(destPath);
        		FileUploadUtil.deleteFile(destPath.replace("uploads/", "uploads/abbr/"));
        		if(b) {
        			logger.info("无人脸图片删除成功!");
        		}
        		return res;
        	} else {
        		List<FaceInfo> faceInfos = faceInfoService.listFaceInfoByOpenId((String) map.get("openid"));
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
    			FaceInfo faceInfo = new FaceInfo();

    			faceInfo.setAge(age);
    			faceInfo.setFaceValue(beauty);
    			faceInfo.setCreateTime(new Date(System.currentTimeMillis()));
    			faceInfo.setGender(gender);
    			faceInfo.setPhotoPath((String) map.get("picPath"));
    			faceInfo.setWxid((String) map.get("openid"));
        		faceInfo.setFaceQuality(attributes.getJSONObject("facequality").toJSONString());
        		String emotion = getEmotion(emotions);
        		faceInfo.setEmotion(emotion);
//        		faceInfo.setHealthValue(healthValue);
        		faceInfo.setSkinStatus(attributes.getString("skinstatus"));

        		// 保存数据记录到数据库
                int i = faceInfoService.insertSelective(faceInfo);

    			// 2. 是否是第一张照片
        		if(faceInfos.size() == 0) {
        			// 是第一张人脸照片
        			res.put("result", "first");
        			res.put("res", "一个微信号仅支持识别一张人脸即微信使用者本人, 确认所拍照片为本人么? 此提醒仅在第一张照片时提醒");
        			res.put("info", faceInfo);
        			res.put("face_rectangle", face_rectangle);
        			res.put("skinstatus", attributes.getJSONObject("skinstatus"));
        			res.put("emotion", emotion);
        			return res;
        		} else {
        			// 比较是否是原来的同一个人
        			FaceInfo faceInfoFromDB = faceInfos.get(0);
        			String filePath1 = path + faceInfoFromDB.getPhotoPath();
        			logger.info("来自原有照片的路径：" + filePath1);
        			
        			// 对比两张照片是否为本人
        			Response resp = detectService.compareTwoFace(filePath1, destPath);
        			logger.info("两张人脸照片对比的结果返回值为: " + resp.toString());
        			
        			String compareResult = new String(resp.getContent());
        			
        			JSONObject j = JSONObject.parseObject(compareResult);
        			// 一组用于参考的置信度阈值
        			// 低于“千分之一”阈值则不建议认为是同一个人；如果置信值超过“十万分之一”阈值，则是同一个人的几率非常高
        			JSONObject json = j.getJSONObject("thresholds");

        			float threshold = json.getBigDecimal("1e-5").floatValue();
        			// 是同一个人的置信度
        			float confidence = j.getBigDecimal("confidence").floatValue(); 
        			
        			// 3.  是否是同个人的照片
        			if(confidence > threshold) {
        				res.put("recondId", i);
//        				// 是同一个人
        				res.put("res", "识别成功");
        				res.put("info", faceInfo);
        				res.put("face_rectangle", face_rectangle);
        				res.put("skinstatus", attributes.getJSONObject("skinstatus"));
        				res.put("emotion", emotion);
        				return res;
        			} else {
        				res.put("res", "识别失败, 请拍本人人脸图片");
        				res.put("result", "not_the_same_human");
        				// 删除本地磁盘中的照片
                		boolean b = FileUploadUtil.deleteFile(destPath);
                		FileUploadUtil.deleteFile(destPath.replace("uploads/", "uploads/abbr/"));
                		// 删除数据库表记录
                        int t = faceInfoService.deleteFaceInfoById(i);

                        if(b && (t == 1)) {
                            logger.info("不是本人图片删除成功!");
                        }
                        return res;
        			}
        		}
        	}
        } else {
        	res.put("result", "upload_at_fault");
        	res.put("res", "非常抱歉, 服务器上传识别失败, 请反馈给我们! ");
        	logger.error("服务器上传识别失败!");
        }
		return res;
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
