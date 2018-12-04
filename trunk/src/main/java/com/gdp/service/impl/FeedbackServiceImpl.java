package com.gdp.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdp.entity.Feedback;
import com.gdp.entity.FeedbackExample;
import com.gdp.mapper.FeedbackMapper;
import com.gdp.service.FeedbackService;
import com.github.pagehelper.PageHelper;

@Service("feedbackService")
public class FeedbackServiceImpl implements FeedbackService {

	private Logger logger = Logger.getLogger(FeedbackServiceImpl.class);
	
	@Autowired
	private FeedbackMapper feedbackMapper;
	
	@Override
	public int saveFeedback(Feedback feedback) {
		int temp = this.feedbackMapper.insertSelective(feedback);
		if (temp == 1) {
			logger.info("===> 保存一条用户反馈成功!");
		}
		int i = this.feedbackMapper.selectRecentId();
		return i;
	}

	@Override
	public List<Feedback> listFeedback(String orderBy, int index, int limit) {
		/// 通过criteria构造查询条件
		FeedbackExample example = new FeedbackExample();
		// asc升序,desc降序排列
		if(orderBy != null) {
			example.setOrderByClause(orderBy + " asc");
		}
		
		// 去除重复,true是选择不重复记录,false反之
		example.setDistinct(true);

		// 使用分页插件, 其后面紧跟着的第一个select方法会被分页
		PageHelper.startPage(index, limit);
		// 查询所有记录
		List<Feedback> list = feedbackMapper.selectByExample(example);
		return list;
	}

}
