package com.gdp.entity;

import java.time.Instant;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gdp.mapper.AnnouncementMapper;

public class AnnouncementTest {
	
	private static final Logger logger = LoggerFactory.getLogger(AnnouncementMapper.class);

//	@Test
	public void testAnnouncement() {
		@SuppressWarnings("resource")
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-context.xml");
		AnnouncementMapper announcementMapper = applicationContext.getBean(AnnouncementMapper.class);
		
		Announcement announcement = new Announcement();
		announcement.setContent("test general mapper");
		announcement.setCreatetime(Date.from(Instant.now()));
		announcement.setCreator("admin");
		announcement.setPriority(1);
		
		int i = announcementMapper.insert(announcement);
		logger.info("i：{}", i);
		
		logger.info("id: {}", announcement.getId());
		
	}

}
