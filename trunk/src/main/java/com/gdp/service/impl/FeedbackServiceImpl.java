package com.gdp.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdp.base.BaseServiceImpl;
import com.gdp.entity.Feedback;
import com.gdp.mapper.FeedbackDao;
import com.gdp.mapper.FeedbackMapper;
import com.gdp.service.FeedbackService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service("feedbackService")
public class FeedbackServiceImpl extends BaseServiceImpl<FeedbackMapper, Feedback> implements FeedbackService {

//	private Logger logger = LoggerFactory.getLogger(FeedbackServiceImpl.class);
	
	@Autowired
	private FeedbackMapper feedbackMapper;
	@Autowired
	private FeedbackDao feedbackDao;
	
	@Override
	public int saveFeedback(Feedback feedback) {
		int temp = this.feedbackMapper.insertSelective(feedback);
		if(temp == 1) {
			temp = feedback.getId();
		}
		return temp;
	}

	@Override
	public List<Feedback> listFeedback(String orderBy, int index, int limit) {
		/// 通过criteria构造查询条件
		Example example = new Example(Feedback.class);
		// asc升序,desc降序排列
		if(orderBy != null) {
			example.setOrderByClause(orderBy + " asc");
		}
		
		// 去除重复,true是选择不重复记录,false反之
		example.setDistinct(true);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("status", (byte) 0);
		
		// RowBounds(int offset, int limit) -> offet 偏移数, 而不是页码; limit 单页数据条数
		RowBounds rowBounds = new RowBounds((index-1)*limit, limit);

		// 使用 RowBounds 进行分页操作
		List<Feedback> list = feedbackMapper.selectByExampleAndRowBounds(example, rowBounds);
		return list;
	}

	@Override
	public int deleteFeedback(int[] ids) {
		int i = this.feedbackDao.deleteFeedback(ids);
		return i;
	}
}
