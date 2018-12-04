package com.gdp.recognition;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gdp.pojo.FaceDetectVO;
import com.gdp.util.ParaUtil;
import com.gdp.util.baidu.Base64Util;
import com.gdp.util.baidu.FileUtil;
import com.gdp.util.baidu.GsonUtils;
import com.gdp.util.baidu.HttpUtil;


/**
 * 人脸检测与属性分析
 * 
 * @author Jashon
 * @since 2018-07-22
 */
public class BaiduFaceDetect {
	
	/**
	 * 重要提示代码中所需工具类
	 * FileUtil, Base64Util, HttpUtil, GsonUtils 请从
	 * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
	 * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
	 * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
	 * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
	 * 下载
	 */
    public static String detect(String filePath) {
//    	StringBuilder fi = new StringBuilder("H:\\eclipse-workspace\\zMyJavaWebLearning\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\facememory\\uploads\\");
//    	fi.append("20180722090253.png");
//    	String filePath = fi.toString();
    	// 读取照片为 Base64 编码
    	byte[] imgData = null;
		try {
			imgData = FileUtil.readFileByBytes(filePath);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
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

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            // 有效期为30天. 2018.07.22
            String accessToken = ParaUtil.ACCESS_TOKEN;

            String result = HttpUtil.post(url, accessToken, "application/json", param);
//            System.out.println("--- > result" + result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 测试 main 方法
     * 
     * @param args
     */
    public static void main(String[] args) {
    	String filePath = "H:\\eclipse-workspace\\zMyJavaWebLearning\\.metadata\\"
    			+ ".plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\facememory\\uploads\\"
    			+ "201808121126.jpg";

    	// 传入参数: 图片地址
        String reString = BaiduFaceDetect.detect(filePath);
        JSON json = JSON.parseObject(reString);
        
        FaceDetectVO fDetectVO = JSONObject.toJavaObject(json, FaceDetectVO.class);
        
        System.out.println("----> result Beauty : " + fDetectVO.getResult().getFace_list().get(0).getBeauty() 
        		+ "\n age: " + fDetectVO.getResult().getFace_list().get(0).getAge());
    }
}
