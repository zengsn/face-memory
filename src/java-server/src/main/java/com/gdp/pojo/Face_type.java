package com.gdp.pojo;

/**
 * 真实人脸/卡通人脸 <br/><br/>
 * type: human-> 真实人脸; cartoon-> 卡通人脸  <br/>
 * probability: 人脸类型判断正确的置信度，范围【0~1】，0代表概率最小、1代表最大。
 * 
 * @author Jashon
 * @since 2018-07-22
 *
 */
public class Face_type {

	private String type;	// 人脸类型
	private Double probability;	// 可信度

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getProbability() {
		return probability;
	}

	public void setProbability(Double probability) {
		this.probability = probability;
	}

	@Override
	public String toString() {
		return "Face_type [type=" + type + ", probability=" + probability + "]";
	}

}
