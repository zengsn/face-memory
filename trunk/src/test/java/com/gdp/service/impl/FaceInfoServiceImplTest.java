package com.gdp.service.impl;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gdp.entity.FaceInfo;
import com.gdp.service.FaceInfoService;

public class FaceInfoServiceImplTest {

//	@Test
	public void testSaveFaceInfo() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
		FaceInfoService bean = (FaceInfoService) context.getBean("faceInfoService");
		
		FaceInfo faceInfo = new FaceInfo();
		
		faceInfo.setPhotoPath("e//");
		
		int i = bean.saveFaceInfo(faceInfo);
		System.out.println(i);
	}
}
