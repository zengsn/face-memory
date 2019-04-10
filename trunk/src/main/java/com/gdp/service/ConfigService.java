package com.gdp.service;

import com.gdp.base.BaseService;
import com.gdp.entity.Config;

import tk.mybatis.mapper.common.Mapper;

/**
 * 配置项服务类
 * 
 * @author Jashon
 * @since 2018-09-29
 */
public interface ConfigService extends BaseService<Mapper<Config>, Config> {
	
}
