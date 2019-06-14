package com.gdp.service.impl;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.gdp.entity.Announcement;

public class AnnouncementServiceTest {

	@Test
	public void testSelectById() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
		
		AnnouncementServiceImpl serviceImpl = context.getBean(AnnouncementServiceImpl.class);
		Announcement announcement2 = serviceImpl.selectByPrimaryKey(1);
		
		System.out.println(announcement2.toString());
	}
	
	@Test
	public void testInsertSelective() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
		
		AnnouncementServiceImpl serviceImpl = context.getBean(AnnouncementServiceImpl.class);

		Announcement announcement = new Announcement();
		announcement.setContent("test general mapper");
		announcement.setCreatetime(Date.from(Instant.now()));
		announcement.setCreator("admin");
		announcement.setPriority(1);
//		int i = serviceImpl.insertSelective(announcement);
		
		System.out.println("->  testInsertSelective :" + announcement.toString());
	}
	
	@Test
	public void testListWithPage() throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
		AnnouncementServiceImpl bean = context.getBean(AnnouncementServiceImpl.class);
		
		List<Announcement> listAnnouncementsWithPage = bean.listAnnouncementsWithPage(1, 10, "priority");
		System.out.println(JSONArray.toJSON(listAnnouncementsWithPage));
		
		
	}
}
