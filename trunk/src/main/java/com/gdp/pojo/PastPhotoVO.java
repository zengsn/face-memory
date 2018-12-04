package com.gdp.pojo;

import com.alibaba.fastjson.JSONObject;

/**
 * 返回给查看历史照片页面 PastPhoto的数据类
 * 
 * @author Jashon
 * @since 2018-08-10
 */
public class PastPhotoVO {
	private Byte age;

	private String createtime;

	private String emotion;

	private Double faceValue;

	private Double healthValue;

	private Integer id;

	private JSONObject skinStatus;

	public Byte getAge() {
		return age;
	}

	public String getCreatetime() {
		return createtime;
	}

	public String getEmotion() {
		return emotion;
	}

	public Double getFaceValue() {
		return faceValue;
	}

	public Double getHealthValue() {
		return healthValue;
	}

	public Integer getId() {
		return id;
	}

	public void setSkinStatus(JSONObject skinStatus) {
		this.skinStatus = skinStatus;
	}

	public JSONObject getSkinStatus() {
		return skinStatus;
	}

	public void setAge(Byte age) {
		this.age = age;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public void setEmotion(String emotion) {
		this.emotion = emotion;
	}

	public void setFaceValue(Double faceValue) {
		this.faceValue = faceValue;
	}

	public void setHealthValue(Double healthValue) {
		this.healthValue = healthValue;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "PastPhotoVO [age=" + age + ", createtime=" + createtime + ", emotion=" + emotion + ", faceValue="
				+ faceValue + ", healthValue=" + healthValue + ", id=" + id + ", skinStatus=" + skinStatus + "]";
	}

}