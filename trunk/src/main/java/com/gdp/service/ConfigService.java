package com.gdp.service;

import java.util.List;

import com.gdp.entity.Config;

/**
 * 配置项服务类
 * 
 * @author Jashon
 * @since 2018-09-29
 */
public interface ConfigService {

	/**
	 * 根据 key 值查询对应的 value 值
	 * @param key
	 * @return
	 */
	public Config getConfig(String key);
	
	/**
	 * 根据 key 更新 value值
	 * 
	 * @param config
	 * @return
	 */
	public int updateValue(Config config);
	
	/**
	 * 列出所有配置项
	 * 
	 * @return
	 */
	public List<Config> listAll();
	
}
