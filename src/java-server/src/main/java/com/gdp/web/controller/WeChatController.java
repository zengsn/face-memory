package com.gdp.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.gdp.pojo.OpenIdVO;
import com.gdp.util.AESUtil;
import com.gdp.util.HttpRequestUtil;
import com.gdp.util.ParaUtil;
import com.gdp.util.RealPathUtils;

/**
 * 微信 Controller
 * 
 * @author Jashon
 * @since 2018-09-02
 */
@RestController
@RequestMapping("/wx")
public class WeChatController {

	static {
		// 实例化对象到内存
		new RealPathUtils();
	}

	private Logger logger = LoggerFactory.getLogger(WeChatController.class);
	

	/**
	 * 获取微信用户的openid唯一标识
	 * 
	 * @param code		用户登录时获取的 code
	 * @return
	 */
	@RequestMapping("/getOpenId")
	public Map<String, Object> getOpendId(String code, HttpServletRequest request){
		
		HashMap<String, Object> res = new HashMap<String, Object>();
		// 发送获取 openid 的请求
		String reString = requestOpenId(code);

		// 解析请求的返回值, 转成json再使用对象进行封装
		JSON json = JSON.parseObject(reString);
		
		OpenIdVO openIdVO = JSON.toJavaObject(json, OpenIdVO.class);
		String openid = openIdVO.getOpenid();	// 用户小程序唯一表示openid
		if(openid == null) {
			res.put("openid", "request at fault");
		} else {
			String token = AESUtil.encrypt(openid, "openid");
			String role = AESUtil.encrypt("user", "role");
			res.put("role", role);		// 后续存放于 小程序请求header 中用于区分是用户还是管理员
			res.put("token", token);	// 登录权限 标识, 写于小程序内存, 请求时带在 header 中
			res.put("result", "succeed");
		}

        return res;
	}
	
	
	/**
	 * 发送请求获取 openid 和 session_key
	 * 请求结果： {"session_key":"hZ4XJ2008TSpUEyYEwv7Fw==","openid":"o7uNr5ZOT-MWxK93SRQ5sUe00tQE"}
	 * 
	 * @param code
	 * @return
	 */
	private String requestOpenId(String code){
		String result = "";
		// 请求url
        String url = "https://api.weixin.qq.com/sns/jscode2session?";
        // 请求所需的参数
        String params = "appid=" + ParaUtil.APPID + "&secret=" + ParaUtil.SECRET 
        					+ "&js_code=" + code + "&grant_type=authorization_code";
        
        result = HttpRequestUtil.sendPost(url, params);
        logger.info("发送请求获取 openid 和 session_key 请求结果" + result);
        
        return result;
	}
	

}
