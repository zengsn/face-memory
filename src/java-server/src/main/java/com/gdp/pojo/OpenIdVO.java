package com.gdp.pojo;

/**
 * 封装请求获取微信用户 openid 的返回值
 * @author Jashon
 * @since 2018-07-26
 *
 */
public class OpenIdVO {

	private String openid;			// 用户唯一标识
	private String session_key;		// 会话密钥
	
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getSession_key() {
		return session_key;
	}
	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}
	
	@Override
	public String toString() {
		return "OpenIdVO [openid=" + openid + ", session_key=" + session_key + "]";
	}
	
}
