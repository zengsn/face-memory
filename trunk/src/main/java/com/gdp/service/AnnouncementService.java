package com.gdp.service;

import java.util.List;

import com.gdp.base.BaseService;
import com.gdp.entity.Announcement;

import tk.mybatis.mapper.common.Mapper;

public interface AnnouncementService extends BaseService<Mapper<Announcement>, Announcement> {
	
	/**
	 * 有顺序的查找所有公告
	 * 
	 * @return
	 */
	public List<Announcement> selectAllWithOrder();

    /**
     * 查找指定优先级的公告
     *
     * @param priority
     * @return
     */
    public List<Announcement> listAnnouncementByPriority(Integer priority);
}
