package com.gdp.service;

import com.gdp.util.face.Response;

/**
 * 人脸识别服务
 * 
 * @author Jashon
 * @since 2018-09-02
 */
public interface DetectService {
	
	/**
	 * 使用百度的接口进行人脸识别
	 * 
	 * @param filePath	图片的本地路径
	 * @return
	 */
	public String detect(String filePath);
	
	/**
	 * 使用 face++ 接口进行人脸识别
	 * 
	 * @param filePath
	 * @return
	 */
	public Response detectByFace(String filePath);
	
	/**
	 * face++ 对比两张人脸照片是否是同个人
	 * 
	 * @param filePath1
	 * @param filePath2
	 * @return
	 */
	public Response compareTwoFace(String filePath1, String filePath2);
	
}
