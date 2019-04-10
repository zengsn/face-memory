package com.gdp.service;

import java.util.List;

import com.gdp.base.BaseService;
import com.gdp.entity.Comment;

import tk.mybatis.mapper.common.Mapper;

/**
 * 评论服务类
 */
public interface CommentService extends BaseService<Mapper<Comment>, Comment> {
	
	/**
	 * 根据照片的id 查询评论
	 * @param photoId
	 * @return
	 */
	public List<Comment> selectByPhotoId(int photoId);

	
}
