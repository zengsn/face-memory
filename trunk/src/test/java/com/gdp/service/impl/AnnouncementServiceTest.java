package com.gdp.service.impl;

import java.time.Instant;
import java.util.Date;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
}
