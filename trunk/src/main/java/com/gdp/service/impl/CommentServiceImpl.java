package com.gdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdp.entity.Comment;
import com.gdp.entity.CommentExample;
import com.gdp.mapper.CommentMapper;
import com.gdp.service.CommentService;

@Service("commentService")
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentMapper commentMapper;

	@Override
	public List<Comment> selectByPhotoId(int photoId) {
		CommentExample example = new CommentExample();
		CommentExample.Criteria criteria = example.createCriteria();
		criteria.andPhotoIdEqualTo(photoId);
		
		List<Comment> list = this.commentMapper.selectByExample(example);
		return list;
	}
	
	@Override
	public int saveComment(Comment comment) {
		return this.commentMapper.insertSelective(comment);
	}

	@Override
	public int updateComment(Comment comment) {
		return this.commentMapper.updateByPrimaryKeySelective(comment);
	}



}
