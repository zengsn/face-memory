package com.gdp.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.gdp.entity.Announcement;
import com.gdp.entity.AnnouncementExample;
import com.gdp.mapper.AnnouncementMapper;
import com.gdp.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jashon
 * @since 2018-10-21
 */
@Service("announcementService")
public class AnnouncementServiceImpl implements AnnouncementService{

    @Autowired
    private AnnouncementMapper announcementMapper;

    @Override
    public List<Announcement> selectAll() {
        AnnouncementExample announcementExample = new AnnouncementExample();
        announcementExample.setOrderByClause("priority asc, createtime desc");
        AnnouncementExample.Criteria criteria = announcementExample.createCriteria();
        List<Announcement> lists = this.announcementMapper.selectByExample(announcementExample);
        return lists;
    }

    @Override
    public int saveAnnouncement(Announcement announcement) {
        int i = this.announcementMapper.insertSelective(announcement);
        return i;
    }

    @Override
    public List<Announcement> listAnnouncementByPriority(Integer priority) {
        AnnouncementExample announcementExample = new AnnouncementExample();
        announcementExample.setOrderByClause("createtime desc");
        AnnouncementExample.Criteria criteria = announcementExample.createCriteria();
        criteria.andPriorityEqualTo(priority);
        return this.announcementMapper.selectByExample(announcementExample);
    }


}
