package com.gdp.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.gdp.service.DetectService;
import com.gdp.util.ParaUtil;
import com.gdp.util.baidu.Base64Util;
import com.gdp.util.baidu.FileUtil;
import com.gdp.util.baidu.HttpUtil;
import com.gdp.util.face.CommonOperate;
import com.gdp.util.face.Response;

/**
 * 
 * @author Jashon
 * @since 2018-07-22
 *
 */
@Service("detectService")
public class DetectServiceImpl implements DetectService {
	
	public String detect(String filePath) {
    	// 读取照片为 Base64 编码
    	byte[] imgData = null;
		try {
			imgData = FileUtil.readFileByBytes(filePath);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// 对图片进行 Base64 编码
        String imgStr = Base64Util.encode(imgData);
    	
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/detect";
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            // faceshape 脸型; gender 性别; beauty 美丑打分
            String face_field = "faceshape,facetype,age,gender,beauty";
            map.put("image", imgStr);
            map.put("face_field", face_field);
            map.put("image_type", "BASE64");

            String param = JSONObject.toJSONString(map);

            // 线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            // 有效期为30天.
            String accessToken = ParaUtil.ACCESS_TOKEN;

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println("--- > result" + result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

	@Override
	public Response detectByFace(String filePath) {
		CommonOperate commonOperate = new CommonOperate(ParaUtil.API_KEY, ParaUtil.API_SECRET, false);
		
    	// 读取照片为 Base64 编码
    	byte[] imgData = null;
		try {
			imgData = FileUtil.readFileByBytes(filePath);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// 对图片进行 Base64 编码
        String base64 = Base64Util.encode(imgData);
		
		String attributes = "gender,age,smiling,eyestatus,headpose,facequality,"
				+ "blur,emotion,facequality,beauty,skinstatus";
		Response response = null;
		try {
			response = commonOperate.detectBase64(base64, 0, attributes);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	@Override
	public Response compareTwoFace(String filePath1, String filePath2) {
		CommonOperate commonOperate = new CommonOperate(ParaUtil.API_KEY, ParaUtil.API_SECRET, false);
		
    	// 读取照片为 Base64 编码
    	byte[] imgData1 = null;
    	byte[] imgData2 = null;
		try {
			imgData1 = FileUtil.readFileByBytes(filePath1);
			imgData2 = FileUtil.readFileByBytes(filePath2);
 		} catch (IOException e1) {
			e1.printStackTrace();
		}
		// 对图片进行 Base64 编码
        String base64_1 = Base64Util.encode(imgData1);
        String base64_2 = Base64Util.encode(imgData2);
        Response response = null;
		try {
			response = commonOperate.compare("", "", null, base64_1, "", "", null, base64_2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
	
}
