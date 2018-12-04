package com.gdp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdp.entity.Config;
import com.gdp.entity.ConfigExample;
import com.gdp.mapper.ConfigMapper;
import com.gdp.service.ConfigService;

@Service("configService")
public class ConfigServiceImpl implements ConfigService {

	@Autowired
	private ConfigMapper configMapper;
	
	@Override
	public Config getConfig(String key) {
		Config config = this.configMapper.selectByPrimaryKey(key);
		return config;
	}

	@Override
	public int updateValue(Config config) {
		int i = this.configMapper.updateByPrimaryKeySelective(config);
		return i;
	}

	@Override
	public List<Config> listAll() {
		ConfigExample configExample = new ConfigExample();
		List<Config> list = this.configMapper.selectByExample(configExample);
		return list;
	}

}
