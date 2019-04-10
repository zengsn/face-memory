package com.gdp.admin.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gdp.service.UserInfoService;

@RestController
@RequestMapping("/admin")
public class UserInfoController {
	
	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 *  列出最近一周用户数的变化
	 * 
	 * @return
	 */
	@RequestMapping("/listUserChange")
	public Map<String, Object> listUserChange() {
		Map<String, Object> modelMap = new HashMap<>();
		
		String[] days = new String[7];
		int[] counts = new int[7];
		
		// 获取当天日期
		LocalDate nowDate = LocalDate.now();
		// 日期格式化
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
		for (int i = 0; i < days.length; i++) {
			days[i] = nowDate.plusDays(i).format(dateTimeFormatter);
			userInfoService.countBetweenToday(Date.from(nowDate.plusDays(i).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()),
					Date.from(nowDate.plusDays(i).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
		}
		
		return modelMap;
	}
	
}
