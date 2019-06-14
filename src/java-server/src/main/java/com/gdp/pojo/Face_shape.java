package com.gdp.pojo;

/**
 * 脸型 <br/>
 * type: square: 正方形 triangle:三角形 oval: 椭圆 heart: 心形 round: 圆形 <br/>
 * probability: 可信度, 0~1 代表判断正确的概率
 * 
 * @author Jashon
 * @since 2018-07-22
 */
public class Face_shape {
	
	private String type;	/* 脸型
	square: 正方形 triangle:三角形 oval: 椭圆 heart: 心形 round: 圆形 */
	private Double probability;	/* 置信度
	范围【0~1】，代表这是人脸形状判断正确的概率，0最小、1最大 */

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
		return "Face_shape [type=" + type + ", probability=" + probability + "]";
	}
}
