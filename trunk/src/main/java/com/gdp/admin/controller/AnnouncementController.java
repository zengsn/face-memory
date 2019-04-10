package com.gdp.admin.controller;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gdp.entity.Announcement;
import com.gdp.service.AnnouncementService;

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
