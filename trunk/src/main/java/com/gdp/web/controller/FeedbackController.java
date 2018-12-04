package com.gdp.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdp.entity.Feedback;
import com.gdp.service.FeedbackService;

/**
 * 小程序反馈控制器
 *
 * @author Jashon
 * @since 2018-09-25
 */
@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    private Logger logger = Logger.getLogger(FeedbackController.class);

	@Autowired
	private FeedbackService feedbackService;
	
	/**
	 * 保存反馈信息
	 * 
	 * @param email
	 * @param content
	 * @param wxid		微信唯一openid
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Map<String, Object> save(String email, String content, @RequestParam(value = "wxid", required = false) String wxid){
		Map<String, Object> res = new HashMap<>();
		Feedback feedback = new Feedback();
		if (wxid == null){
            feedback.setWxid(wxid);
        } else {
		    logger.info(" -- > wxid 为空, 可能用户端访问服务端失败!");
        }
		feedback.setEmail(email);
		feedback.setContent(content);
		feedback.setCreatetime(new Date(System.currentTimeMillis()));
		
		int i = feedbackService.saveFeedback(feedback);
		if(i>0) {
			res.put("result", "succeed");
		}
		
		return res;
	}
}
