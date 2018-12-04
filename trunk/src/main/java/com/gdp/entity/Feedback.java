package com.gdp.entity;

import java.util.Date;

public class Feedback {
	
    private Integer id;

    private String email;

    private String content;

    private Date createtime;

    private String wxid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getWxid() {
        return wxid;
    }

    public void setWxid(String wxid) {
        this.wxid = wxid == null ? null : wxid.trim();
    }

	@Override
	public String toString() {
		return "Feedback [id=" + id + ", email=" + email + ", content=" + content + ", createtime=" + createtime
				+ ", wxid=" + wxid + "]";
	}
}