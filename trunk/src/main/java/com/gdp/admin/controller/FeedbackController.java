package com.gdp.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.gdp.entity.Feedback;
import com.gdp.service.FeedbackService;
import com.gdp.util.LogUtils;
import com.github.pagehelper.Page;

/**
 * 反馈管理控制器
 *
 * @author Jashon
 * @since 2018-09-27
 */
@Controller("adminFeedbackController")	// 跟小程序的 FeedbackController 做区分
@RequestMapping("/admin")
public class FeedbackController {

	private Logger logger = LogUtils.logger;
	
	@Autowired
	private FeedbackService feedbackService;

	/**
	 * 分页列出用户反馈的信息
	 *
	 * @param orderBy	排序的字段, 该参数可选
	 * @param page		页码
	 * @param limit		单页的记录条数
	 * @return
	 */
	@RequestMapping("/listFeedback")
	@ResponseBody
	public Map<String, Object> listFeedback(
			@RequestParam(value="orderBy", defaultValue="createtime", required=false) String orderBy,
			int page, int limit) {
		Map<String, Object> map = new HashMap<>();
		List<Feedback> list = feedbackService.listFeedback(orderBy, page, limit);
		JSONArray jsonArray = (JSONArray) JSONArray.toJSON(list);

		// 利用分页插件获取反馈数据总页数
		int total = ((Page<Feedback>) list).getPages();
		logger.info("[FeedbackController.listFeedback] \n ---> 反馈数据列表总页码：" + total);

		// 以下各返回参数为 layui 前端框架所需参数
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", total);
		map.put("data", jsonArray);
		return map;
	}
}
