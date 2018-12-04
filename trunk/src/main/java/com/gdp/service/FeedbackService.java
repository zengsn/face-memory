package com.gdp.service;

import java.util.List;

import com.gdp.entity.Feedback;

/**
 * 用户反馈服务
 * 
 * @author Jashon
 * @since 2018-09-04
 */
public interface FeedbackService {

	/**
	 * 保存用户反馈信息
	 * 
	 * @param feedback
	 * @return
	 */
	public int saveFeedback(Feedback feedback);
	
	/**
	 * 分页获取反馈信息
	 * 
	 * @param index
	 * @param limit
	 * @return
	 */
	public List<Feedback> listFeedback(String orderBy, int index, int limit);
	
}
