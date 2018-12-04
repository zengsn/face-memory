package com.gdp.service.impl;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gdp.service.DetectService;
import com.gdp.util.face.Response;

public class DetectServiceImplTest {

//	@Test
	public void testDetect() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
		DetectService bean = (DetectService) context.getBean("baiduDetectService");
		String filePath = "C:\\Users\\73028\\Desktop\\"
				+ "uploads\\201808121126.jpg";
		String reString = bean.detect(filePath);
		
		System.out.println(reString);
	}
	
//	@Test
	public void testDetectByFace() {
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
		DetectService bean = (DetectService) context.getBean("detectService");
		
		String filePath = "C:\\Users\\73028\\Desktop\\uploads\\201808121126.jpg";
		
		Response response = bean.detectByFace(filePath);
		
		System.out.println(response.toString());
	}
	
	
//	@Test
	public void testCompareTwoFace(){
		@SuppressWarnings("resource")
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
		DetectService bean = (DetectService) context.getBean("detectService");
		
		String filePath1 = "C:\\Users\\73028\\Desktop\\uploads\\201808121126.jpg";
		String filePath2 = "C:\\Users\\73028\\Desktop\\uploads\\201808120825.jpg";
		
		Response response = bean.compareTwoFace(filePath1, filePath2);
		
		System.out.println(response.toString());
	}
	
}
