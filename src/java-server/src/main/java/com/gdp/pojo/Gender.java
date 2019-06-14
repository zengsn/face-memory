package com.gdp.pojo;

/**
 * 性别 <br/><br/>
 * type: male:男性 female:女性 <br/>
 * probability: 性别置信度，范围【0~1】，0代表概率最小、1代表最大 <br/>
 * 
 * @author Jashon
 * @since 2018-07-22
 */
public class Gender {
	private String type;
    private Double probability;
    
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
		return "Gender [type=" + type + ", probability=" + probability + "]";
	}
    
}
