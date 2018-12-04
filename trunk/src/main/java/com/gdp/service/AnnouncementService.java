package com.gdp.service;

import com.gdp.entity.Announcement;

import java.util.List;

/**
 * 公告服务类
 */
public interface AnnouncementService {

    /**
     * 查出所有公告
     *
     * @return
     */
    public List<Announcement> selectAll();

    /**
     * 添加一条公告
     *
     * @param announcement
     * @return
     */
    public int saveAnnouncement(Announcement announcement);

    /**
     * 查找指定优先级的公告
     *
     * @param priority
     * @return
     */
    public List<Announcement> listAnnouncementByPriority(Integer priority);
}
