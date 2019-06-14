package com.gdp.service;

import java.util.List;

import com.gdp.base.BaseService;
import com.gdp.entity.Feedback;

import tk.mybatis.mapper.common.Mapper;

/**
 * 用户反馈服务
 * 
 * @author Jashon
 * @since 2018-09-04
 */
public interface FeedbackService extends BaseService<Mapper<Feedback>, Feedback> {

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

    /**
     * 逻辑删除指定 id 的反馈数据
     *
     * @param ids
     * @return
     */
	public int deleteFeedback(int [] ids);
	
}
