package com.gdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdp.base.BaseServiceImpl;
import com.gdp.entity.Comment;
import com.gdp.mapper.CommentMapper;
import com.gdp.service.CommentService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service("commentService")
public class CommentServiceImpl extends BaseServiceImpl<CommentMapper, Comment>implements CommentService {

	@Autowired
	private CommentMapper commentMapper;

	@Override
	public List<Comment> selectByPhotoId(int photoId) {
		Example example = new Example(Comment.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("photoId", photoId);
		
		List<Comment> list = this.commentMapper.selectByExample(example);
		return list;
	}
	


}
