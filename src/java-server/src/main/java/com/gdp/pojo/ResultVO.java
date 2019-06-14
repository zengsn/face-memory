package com.gdp.pojo;

import java.util.List;

/**
 * 人脸识别结果封装对象
 * 
 * @author Jashon
 * @since 2018-07-22
 */
public class ResultVO {

	private Integer face_num;	// 检测到的图片中的人脸数量
	private List<Face_list> face_list;	// 人脸信息列表
	
	public Integer getFace_num() {
		return face_num;
	}
	public void setFace_num(Integer face_num) {
		this.face_num = face_num;
	}
	public List<Face_list> getFace_list() {
		return face_list;
	}
	public void setFace_list(List<Face_list> face_list) {
		this.face_list = face_list;
	}
	
	
}
