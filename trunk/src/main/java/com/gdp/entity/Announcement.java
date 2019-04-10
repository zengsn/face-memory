package com.gdp.entity;

import java.util.Date;
import javax.persistence.*;

import lombok.ToString;

@ToString
public class Announcement {
    /**
     * 主键id, 自增
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 公告内容
     */
    private String content;

    /**
     * 显示优先级, 可用于置顶某一条公告
     */
    private Integer priority;

    /**
     * 创建时间
     */
    private Date createtime;

    /**
     * 创建者
     */
    private String creator;

    /**
     * 获取主键id, 自增
     *
     * @return id - 主键id, 自增
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键id, 自增
     *
     * @param id 主键id, 自增
     */
    public void setId(Integer id) {
        this.id = id;
    }


	/**
     * 获取公告内容
     *
     * @return content - 公告内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置公告内容
     *
     * @param content 公告内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取显示优先级, 可用于置顶某一条公告
     *
     * @return priority - 显示优先级, 可用于置顶某一条公告
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * 设置显示优先级, 可用于置顶某一条公告
     *
     * @param priority 显示优先级, 可用于置顶某一条公告
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /**
     * 获取创建时间
     *
     * @return createtime - 创建时间
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 设置创建时间
     *
     * @param createtime 创建时间
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * 获取创建者
     *
     * @return creator - 创建者
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置创建者
     *
     * @param creator 创建者
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }
}