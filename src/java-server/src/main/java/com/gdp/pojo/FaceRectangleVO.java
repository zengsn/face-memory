package com.gdp.pojo;

/**
 * 识别人脸的矩形框的位置
 * 
 * @author Jashon
 * @since 2018-09-02
 */
public class FaceRectangleVO {

	private Integer top;	// 矩形框左上角像素点的纵坐标
	private Integer left;	// 矩形框左上角像素点的横坐标
	private Integer width;	// 矩形框的宽度
	private Integer height;	// 矩形框的高度

	public Integer getTop() {
		return top;
	}

	public void setTop(Integer top) {
		this.top = top;
	}

	public Integer getLeft() {
		return left;
	}

	public void setLeft(Integer left) {
		this.left = left;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	@Override
	public String toString() {
		return "FaceRectangleVO [top=" + top + ", left=" + left + ", width=" + width + ", height=" + height + "]";
	}

}
