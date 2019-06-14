package com.gdp.service.impl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.gdp.entity.FaceInfo;
import com.gdp.entity.UserInfo;
import com.gdp.service.FaceInfoService;
import com.gdp.service.UserInfoService;

public class UserServiceImplTest {
	private ApplicationContext context;
	
	@Before
	public void Before() {
		context = new ClassPathXmlApplicationContext("spring-context.xml");
	}
	
	@Test
	public void testCountBetweenToday() {
		UserInfoService userInfoService = (UserInfoService) context.getBean("userInfoService");
		
		String[] days = new String[7];
		int[] counts = new int[7];
		
		// 获取当天日期
		LocalDate nowDate = LocalDate.now();
		nowDate = nowDate.plusDays(-30);
		
		// 日期格式化 
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		for (int i = -6; i <= 0; i++) {
			days[i+6] = nowDate.plusDays(i).format(dateTimeFormatter);
			counts[i+6] = userInfoService.countBetweenToday(Date.from(nowDate.plusDays(i).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()),
					Date.from(nowDate.plusDays(i+1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
		}
		for (int i = 0; i < counts.length; i++) {
			System.out.println(days[i]);
			System.out.println("新增用户数: " + counts[i]);
		}

		System.out.println("用户总数: " + userInfoService.countAll());
	}
	
//	@Test
	public void test() {
		UserInfoService userInfoService = (UserInfoService) context.getBean("userInfoService");
		FaceInfoService faceInfoService = (FaceInfoService) context.getBean("faceInfoService");
		List<FaceInfo> list = faceInfoService.listByOpenIdForAdmin("oxebW5Rpjc-_L0-DiM740kuCgtd0");
		System.out.println(list);
		if (list == null || list.size() == 0) {
			System.out.println("is null");
		}
		
		// 恢复用户状态为初始值
		UserInfo userInfo = new UserInfo();
		userInfo.setOpenid("oxebW5Rpjc-_L0-DiM740kuCgtd0");
		userInfo.setStatus(0);
//		userInfoService.updateByPrimaryKeySelective(userInfo);
	}
}
