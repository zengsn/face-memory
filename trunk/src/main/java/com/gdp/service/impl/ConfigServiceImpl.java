package com.gdp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gdp.base.BaseServiceImpl;
import com.gdp.entity.Config;
import com.gdp.mapper.ConfigMapper;
import com.gdp.service.ConfigService;

@Service("configService")
public class ConfigServiceImpl extends BaseServiceImpl<ConfigMapper, Config> implements ConfigService {

	@SuppressWarnings("unused")
	@Autowired
	private ConfigMapper configMapper;
	

}
