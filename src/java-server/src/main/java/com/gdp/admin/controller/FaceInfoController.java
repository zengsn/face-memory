package com.gdp.admin.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gdp.service.FaceInfoService;
import com.gdp.service.UserInfoService;

@RestController("adminFaceInfoController")
@RequestMapping("/admin/faceInfo")
public class FaceInfoController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private FaceInfoService faceInfoService;
	@Autowired
	private UserInfoService userInfoService;
	
	/**
	 * 列出最近一周小程序使用用户数
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
		
		for (int i = -6; i <= 0; i++) {
			days[i+6] = nowDate.plusDays(i).format(dateTimeFormatter);
			counts[i+6] = faceInfoService.countBetween(Date.from(nowDate.plusDays(i).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()),
					Date.from(nowDate.plusDays(i+1).atStartOfDay().atZone(ZoneId.systemDefault()).toInstant()));
		}
		
		int total = userInfoService.countAll();
		logger.info("七天日期{}；增加用户数： {}",days, counts);
		modelMap.put("days", days);
		modelMap.put("counts", counts);
		modelMap.put("total", total);
		
		return modelMap;
	}
}
