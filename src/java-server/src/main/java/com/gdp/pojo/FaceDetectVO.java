package com.gdp.pojo;

/**
 * 封装对象: 对访问百度人脸识别的接口返回值进行封装
 * 
 * @author Jashon
 * @since 2018-07-22
 *
 */
public class FaceDetectVO {
	
	private Integer error_code;		// 错误码
	private String error_msg;		// 错误信息
	private Long log_id;		 	// 日志编号
	private Long timestamp;			// 时间戳
	private Integer cached;
	
	private ResultVO result;		// 返回结果

	public Integer getError_code() {
		return error_code;
	}

	public void setError_code(Integer error_code) {
		this.error_code = error_code;
	}

	public String getError_msg() {
		return error_msg;
	}

	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}

	public Long getLog_id() {
		return log_id;
	}

	public void setLog_id(Long log_id) {
		this.log_id = log_id;
	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public Integer getCached() {
		return cached;
	}

	public void setCached(Integer cached) {
		this.cached = cached;
	}

	public ResultVO getResult() {
		return result;
	}

	public void setResult(ResultVO result) {
		this.result = result;
	}
	
	
	
}
