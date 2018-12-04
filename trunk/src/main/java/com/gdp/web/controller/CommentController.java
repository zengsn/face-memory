package com.gdp.web.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gdp.entity.Comment;
import com.gdp.service.CommentService;
import org.springframework.web.bind.annotation.RestController;

/**
 * 小程序评论控制器
 * 
 * @author Jashon
 * @since 2018-08-25
 */
@RestController
@RequestMapping("/comment")
public class CommentController {
	
	private Logger logger = Logger.getLogger(CommentController.class);
	@Autowired
	private CommentService commentService;
	
	/**
	 * 保存一条评论记录
	 * 
	 * @param photoId	对应的照片id
	 * @param content   评论内容
	 * @param satisfied
	 * @return
	 */
	@RequestMapping("/save")
	public Map<String, Object> saveComment(int photoId, @RequestParam(value = "content", defaultValue = "") String content, int satisfied){
		Map<String, Object> res = new HashMap<>();
		Comment comment  = new Comment();
		comment.setCreatetime(new Date(System.currentTimeMillis()));
		comment.setPhotoId(photoId);
		comment.setContent(content);
		if(satisfied == 1) {
			comment.setSatisfied(true);
		}else {
			comment.setSatisfied(false);
		}

		int i = commentService.saveComment(comment);
		if (i > 0) {
			res.put("result", "succeed");
		} else {
			res.put("result", "at_fault");
		}

		return res;
	}
	
	/**
	 * 为照片结果 吐槽 或 满意
	 * 
	 * @param photoId 照片id
	 * @param satisfied
	 * @return
	 */
	@RequestMapping("/like")
	public Map<String, Object> likeTheResult(int photoId, int satisfied) {
		Map<String, Object> res = new HashMap<>();
		Comment comment = new Comment();
		comment.setSatisfied(satisfied == 1 ?true :false);
		comment.setPhotoId(photoId);
		List<Comment> list = commentService.selectByPhotoId(photoId);
		
		int i = 0;
		if (list.size() == 0) {
			comment.setCreatetime(new Date(System.currentTimeMillis()));
			i = commentService.saveComment(comment);
		} else {
			comment.setId(list.get(0).getId());
			i = commentService.updateComment(comment);
		}
		
		if(i != 0) {
			res.put("result", "succeed");
		}
		logger.info("photoId: " + photoId + "\n" + comment.toString());
		return res;
	}
}
