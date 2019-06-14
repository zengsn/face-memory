package com.gdp.admin.controller;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.gdp.entity.Announcement;
import com.gdp.service.AnnouncementService;
import com.github.pagehelper.Page;

/**
 * 
 * Time: 2019-03-15 21:06
 * @author Jashon
 * @version 1.0
 */
@RestController("adminAnnouncementController")
@RequestMapping("/admin/announcement")
public class AnnouncementController {

	@Autowired
	public AnnouncementService announcementService;
	
	@RequestMapping("/list")
	public Map<String, Object> listAnnouncements(Integer page, Integer limit,
			@RequestParam(value = "orderBy", required = false) String orderBy) {
		Map<String, Object> modelMap = new HashMap<>();
		List<Announcement> list = announcementService.listAnnouncementsWithPage(page, limit, orderBy);
		JSONArray jsonArray = (JSONArray) JSONArray.toJSON(list);
		
		long count = ((Page<Announcement>)list).getTotal();
		
		// 以下各返回参数为 layui 前端框架所需参数
		modelMap.put("code", 0);
		modelMap.put("msg", "");
		modelMap.put("count", count);	// 数据总数
		modelMap.put("data", jsonArray);
		return modelMap;
	}
	
	/**
	 * 设置公告
	 * @param announcement	传入参数 -> content, priority
	 * @return
	 */
	@RequestMapping("/saveAnnouncement")
	public Map<String, Object> saveAnnouncement(Announcement announcement) {
		Map<String, Object> modelMap = new HashMap<>();
		announcement.setCreatetime(Date.from(Instant.now()));
		announcement.setCreator("admin");
		if(announcement.getPriority() == null) {
			announcement.setPriority(10);
		}
		int i = announcementService.insertSelective(announcement);
		if(i == 1) {
			modelMap.put("result", "succeed");
			modelMap.put("resultMsg", "添加公告成功");
		} else {
			modelMap.put("result", "failed");
			modelMap.put("resultMsg", "添加公告失败, 请重试!");
		}
		
		return modelMap;
	}
	
	/**
	 * 设置公告
	 * @param announcement	传入参数 -> content, priority
	 * @return
	 */
	@RequestMapping("/modifyAnnouncement")
	public Map<String, Object> modifyAnnouncement(Announcement announcement) {
		Map<String, Object> modelMap = new HashMap<>();
		if(announcement.getPriority() == null) {
			announcement.setPriority(10);
		}
		int i = announcementService.updateByPrimaryKeySelective(announcement);
		if(i == 1) {
			modelMap.put("result", "succeed");
			modelMap.put("resultMsg", "添加公告成功");
		} else {
			modelMap.put("result", "failed");
			modelMap.put("resultMsg", "添加公告失败, 请重试!");
		}
		
		return modelMap;
	}
	/**
	 * 删除公告
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	public Map<String, Object> deleteAnnouncements(Integer id) {
		Map<String, Object> modelMap = new HashMap<>();
		int i = announcementService.deleteAnnouncement(id);
		if(i == 1) {
			modelMap.put("result", "succeed");
			modelMap.put("resultMsg", "删除成功");
		} else {
			modelMap.put("result", "failed");
			modelMap.put("resultMsg", "删除失败, 请重试!");
		}
		return modelMap;
	}
	
	/**
	 * a隐藏或逻辑删除公告
	 * @param id
	 * @return
	 */
	@RequestMapping("/operateAnnouncement")
	public Map<String, Object> operateAnnouncement(Integer id, Integer status) {
		Map<String, Object> modelMap = new HashMap<>();
//		Announcement announcement = new Announcement();

		int i = 0;
		/*
		 * 待添加内容
		 */
	
		if(i == 1) {
			modelMap.put("result", "succeed");
			modelMap.put("resultMsg", "操作公告成功");
		} else {
			modelMap.put("result", "failed");
			modelMap.put("resultMsg", "操作公告失败, 请重试!");
		}
		
		return modelMap;
	}
	
	
}
