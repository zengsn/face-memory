package com.gdp.entity;

import java.util.Date;

public class Comment {
	
    private Integer id;

    private String content;

    private Boolean satisfied;

    private Date createtime;

    private Integer photoId;

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

    public Boolean getSatisfied() {
        return satisfied;
    }

    public void setSatisfied(Boolean satisfied) {
        this.satisfied = satisfied;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getPhotoId() {
        return photoId;
    }

    public void setPhotoId(Integer photoId) {
        this.photoId = photoId;
    }

	@Override
	public String toString() {
		return "Comment [id=" + id + ", content=" + content + ", satisfied=" + satisfied + ", createtime=" + createtime
				+ ", photoId=" + photoId + "]";
	}
}