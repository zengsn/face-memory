package com.gdp.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.gdp.entity.Config;
import com.gdp.service.ConfigService;

/**
 * 系统配置项 控制器
 *
 * @author Jashon
 * @since 2018-09-25
 */
@Controller
@RequestMapping("/admin/config")
public class ConfigController {
	
	private Logger logger = LoggerFactory.getLogger(ConfigController.class);
	
	@Autowired
	private ConfigService configService;

	/**
	 * 更新指定的配置项
	 *
	 * @param key
	 * @param value
	 * @param description
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Map<String, Object> updateConfig(String key, String value,
			@RequestParam(value="description", defaultValue = "") String description){
		Map<String, Object> res = new HashMap<>();
		Config config = new Config();
		config.setKeyes(key);
		config.setValuees(value);
		if(!"".equals(description)) {
			config.setDescription(description);
		}
		int i = configService.updateByPrimaryKeySelective(config);
		logger.info("-> 更新配置项为: {}", config.toString());
		if(i == 1) {
			res.put("result", "succeed");
		} else {
			res.put("result", "update_config_at_fault");
		}
		return res;
	}
	
	/**
	 * 列出所有配置项
	 * 
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public JSONArray listConfigs(){
		List<Config> list = configService.selectAll();
		JSONArray jsonArray = (JSONArray) JSONArray.toJSON(list);
		return jsonArray;
	}
}
