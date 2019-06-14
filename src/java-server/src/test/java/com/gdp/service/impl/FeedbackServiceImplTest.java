package com.gdp.service.impl;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSONArray;
import com.gdp.entity.Feedback;
import com.gdp.service.FeedbackService;

public class FeedbackServiceImplTest {

	@Test
	public void testListFeedback() {
		ApplicationContext context = new ClassPathXmlApplicationContext("spring-context.xml");
		
		FeedbackService service = (FeedbackService) context.getBean("feedbackService");
		
		List<Feedback> listFeedback = service.listFeedback(null, 1, 10);
		
		System.out.println((JSONArray) JSONArray.toJSON(listFeedback));
		
		((AbstractApplicationContext) context).close();
	}
	
//	@Test
	public void test() {
	}
}
