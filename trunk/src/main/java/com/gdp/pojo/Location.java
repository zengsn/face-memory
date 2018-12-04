package com.gdp.pojo;

/**
 * 人脸在图片中的位置<br/><br/>
 * left;	人脸区域离左边界的距离<br/>
 * top;		人脸区域离上边界的距离<br/>
 * width;	人脸区域的宽度<br/>
 * height;		人脸区域的高度<br/>
 * rotation;	人脸框相对于竖直方向的顺时针旋转角，[-180,180]<br/>
 * 
 * @author Jashon
 * @since 2018-07-22
 */
public class Location {
	
	private double left;	// 人脸区域离左边界的距离
	private double top;		// 人脸区域离上边界的距离
	private int width;		// 人脸区域的宽度
	private int height;		// 人脸区域的高度
	private int rotation;	// 人脸框相对于竖直方向的顺时针旋转角，[-180,180]

	public double getLeft() {
		return left;
	}

	public void setLeft(double left) {
		this.left = left;
	}

	public double getTop() {
		return top;
	}

	public void setTop(double top) {
		this.top = top;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getRotation() {
		return rotation;
	}

	public void setRotation(int rotation) {
		this.rotation = rotation;
	}

	@Override
	public String toString() {
		return "Location [left=" + left + ", top=" + top + ", width=" + width + ", height=" + height + ", rotation="
				+ rotation + "]";
	}

}
