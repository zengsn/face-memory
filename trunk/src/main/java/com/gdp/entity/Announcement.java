package com.gdp.entity;

import java.util.Date;

public class Announcement {
    private Integer id;

    private String content;

    private Integer priority;

    private Date createtime;

    private String creator;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    public Announcement() {
    }

    public Announcement( String content, Integer priority, Date createtime, String creator) {
        this.content = content;
        this.priority = priority;
        this.createtime = createtime;
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", priority=" + priority +
                ", createtime=" + createtime +
                ", creator='" + creator + '\'' +
                '}';
    }

}