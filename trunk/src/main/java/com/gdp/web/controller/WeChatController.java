package com.gdp.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.gdp.pojo.OpenIdVO;
import com.gdp.util.HttpRequestUtil;
import com.gdp.util.LogUtils;
import com.gdp.util.ParaUtil;
import com.gdp.util.RealPathUtils;

/**
 * 微信 Controller
 * 
 * @author Jashon
 * @since 2018-09-02
 */
@Controller
@RequestMapping("/wx")
public class WeChatController {

	static {
		// 实例化对象到内存
		new RealPathUtils();
	}
	private Logger logger = Logger.getLogger(WeChatController.class);
	/**
	 * 获取微信用户的openid唯一标识
	 * 
	 * @param code		用户登录时获取的 code
	 * @return
	 */
	@RequestMapping("/getOpenId")
	@ResponseBody
	public Map<String, Object> getOpendId(String code, HttpServletRequest request){
		
		HashMap<String, Object> res = new HashMap<String, Object>();
		logger.info("---> code :" + code);
		// 发送获取 openid 的请求
		String reString = requestOpenId(code);
		// 打印请求openID返回值
		logger.info("\n request OPENID code return : " + reString);
		
		// 解析请求的返回值, 转成json再使用对象进行封装
		JSON json = JSON.parseObject(reString);
		
		OpenIdVO openIdVO = JSON.toJavaObject(json, OpenIdVO.class);
		String openid = openIdVO.getOpenid();
		if(openid == null) {
			res.put("openid", "request at fault");
		} else {
			// 获取 Subject
			Subject currentUser = SecurityUtils.getSubject();

			// 判断是否已经由登陆过账户，是的话直接返回登陆成功
			if(currentUser.getSession().getAttribute("openid") != null) {
				res.put("result", "succeed");
			} else {
				if(!currentUser.isAuthenticated()) {
					System.out.println("--> wxlogin: !currentUser.isAuthenticated()");
					currentUser.getSession().setAttribute("role", "wx");
					// 将登陆的 openid 和密码封装成 token
					UsernamePasswordToken token = new UsernamePasswordToken(openid, "123");

					try {
						// 执行登录
						currentUser.login(token);
						// 记住我
						token.setRememberMe(true);
						res.put("result", "succeed");
					} catch (AuthenticationException ae) {
						logger.error("---> 登录失败: " + ae.getMessage());
						res.put("result", "failed_token_wrong");
					}
				}
			}

			logger.info("--> 返回openid");
			request.getSession().setAttribute("openid", openid);
			res.put("openid", openid);
		}

        return res;
	}
	
	
	/**
	 * 发送请求获取 openid和session_key
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
        logger.info("发送请求获取 openid和session_key 请求结果" + result);
        
        return result;
	}
	
}
