package com.gdp.pojo.admin;

public class WeChatInfoVO {

	private String wxid;
	private String nickName;
	private String size;

	public String getWxid() {
		return wxid;
	}

	public void setWxid(String wxid) {
		this.wxid = wxid;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "WeChatInfoVO [wxid=" + wxid + ", nickName=" + nickName + ", size=" + size + "]";
	}

}
