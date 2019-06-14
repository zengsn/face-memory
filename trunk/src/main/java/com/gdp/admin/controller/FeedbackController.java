package com.gdp.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gdp.entity.Feedback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.gdp.service.FeedbackService;
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

	private Logger logger = LoggerFactory.getLogger(getClass());
	
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
//		int totalPage = ((Page<Feedback>) list).getPages();
//		logger.info("-> 反馈数据列表总页码：" + totalPage);
		long count = ((Page<Feedback>) list).getTotal();

		// 以下各返回参数为 layui 前端框架所需参数
		map.put("code", 0);
		map.put("msg", "");
		map.put("count", count);	// 数据总数
		map.put("data", jsonArray);
		return map;
	}

	/**
	 * 删除指定 id 的反馈信息
     *
	 * @param ids
	 * @return
	 */
	@RequestMapping("/deleteFeedback")
	@ResponseBody
	public Map<String, Object> deleteFeedback(@RequestParam(value = "ids", required = false) int [] ids) {
		Map<String, Object> modelMap = new HashMap<>();
		logger.info("ids: {}", ids);
		if (ids == null){
            modelMap.put("success", false);
            modelMap.put("code", "-1");
            modelMap.put("data", "");
            modelMap.put("resultMsg", "ids 不能为空");
            return modelMap;
        }

        int i = this.feedbackService.deleteFeedback(ids);
        logger.info("-> 逻辑删除的记录id有: {}；成功删除个数: {}\n", ids, i);
        if(i == ids.length) {
            modelMap.put("success", true);
            modelMap.put("data", i);
            modelMap.put("resultMsg", "删除成功!");
        } else {
            modelMap.put("success", false);
            modelMap.put("data", i);
            modelMap.put("resultMsg", "删除失败!");
        }
		return modelMap;
	}
}
