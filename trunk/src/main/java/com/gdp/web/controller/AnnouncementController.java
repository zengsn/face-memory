package com.gdp.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.gdp.entity.Announcement;
import com.gdp.service.AnnouncementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 小程序公告控制器
 *
 * @author Jashon
 * @since 2018-10-21
 */
@RestController
@RequestMapping("/announcement")
public class AnnouncementController {

    private Logger logger = LoggerFactory.getLogger(AnnouncementController.class);

    @Autowired
    private AnnouncementService announcementService;

    /**
     * 列出所有公告
     * @return
     */
    @RequestMapping("/listAll")
    public JSONArray listAll(){
        List<Announcement> lists = announcementService.selectAllWithOrder();
        JSONArray jsons = (JSONArray) JSONArray.toJSON(lists);
        logger.info("所有公告记录为:" + jsons);
        return jsons;
    }

    /**
     * 获取某一优先级的公告
     *
     * @param priority
     * @return
     */
    @RequestMapping("/get")
    public Map<String,Object> getByPriority(Integer priority){
        List<Announcement> lists = announcementService.listAnnouncementByPriority(priority);
        Announcement announcement = null;
        Map<String,Object> res = new HashMap<>();
        if(lists != null){
            announcement = lists.get(0);
            res.put("announcement", announcement.getContent());
        } else {
            res.put("announcement", "");
        }

        return res;
    }

}
