package com.gdp.service;

import java.util.List;

import com.gdp.entity.Comment;

/**
 * 评论服务类
 */
public interface CommentService {
	
	/**
	 * 根据照片的id 查询评论
	 * @param photoId
	 * @return
	 */
	public List<Comment> selectByPhotoId(int photoId);

	/**
	 * 保存评论记录
	 * 
	 * @param comment
	 * @return
	 */
	public int saveComment(Comment comment);
	
	/**
	 * 更新记录
	 * 
	 * @param comment
	 * @return
	 */
	public int updateComment(Comment comment);
	
}
