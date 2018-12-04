package com.gdp.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.gdp.entity.Announcement;
import com.gdp.service.AnnouncementService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.List;

/**
 * @author Jashon
 * @since 2018-10-21
 */
public class AnnouncementServiceImplTest {

//    @Test
    public void testSaveAnnouncement(){
        int priority = 1;
        String content = "一个微信号是能识别一张人脸，因此请确保第一张拍摄识别的人脸照为本人";
        Date createtime = new Date();
        String creator = "admin";
        Announcement announcement = new Announcement(content, priority, createtime, creator);
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-context.xml");
        AnnouncementService bean = (AnnouncementService) ctx.getBean("announcementService");
        int i = bean.saveAnnouncement(announcement);
        System.out.println(announcement.toString());
        System.out.println("i:" + i);
    }

//    @Test
    public void testListAll(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-context.xml");
        AnnouncementService bean = (AnnouncementService) ctx.getBean("announcementService");
        List<Announcement> list = bean.selectAll();
        System.out.println((JSONArray) JSONArray.toJSON(list));
    }
}
