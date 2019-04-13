package com.gdp.service.impl;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONArray;
import com.gdp.entity.FaceInfo;
import com.gdp.service.FaceInfoService;

public class FaceInfoServiceImplTest {
	
	String openid = "oxebW5Rpjc-_L0-DiM740kuCgtd0";
	
	@Test
	public void testListShouldBedeleted() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
		FaceInfoService bean = (FaceInfoService) context.getBean("faceInfoService");
		
		List<FaceInfo> list = bean.listShouldBedeleted(openid);
		System.out.println(JSONArray.toJSON(list));
	}

	@Test
	public void testListTodayFinished() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
		FaceInfoService bean = (FaceInfoService) context.getBean("faceInfoService");
		
		List<FaceInfo> list = bean.listTodayFinished(openid);
		
		System.out.println(JSONArray.toJSON(list));
		
	}
}
