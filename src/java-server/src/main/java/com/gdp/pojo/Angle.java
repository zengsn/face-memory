package com.gdp.pojo;

/**
 * 人脸旋转角度参数 <br/>
 * yaw:	    三维旋转之左右旋转角[-90(左), 90(右)] <br/>
 * pitch: 三维旋转之俯仰角度[-90(上), 90(下)] <br/>
 * roll:  平面内旋转角[-180(逆时针), 180(顺时针)] <br/>
 * 
 * @author Jashon
 * @since 2018-07-22
 */
public class Angle {

	private double yaw;		// 三维旋转之左右旋转角[-90(左), 90(右)]
	private double pitch;	// 三维旋转之俯仰角度[-90(上), 90(下)]
	private double roll;	// 平面内旋转角[-180(逆时针), 180(顺时针)]

	public double getYaw() {
		return yaw;
	}

	public void setYaw(double yaw) {
		this.yaw = yaw;
	}

	public double getPitch() {
		return pitch;
	}

	public void setPitch(double pitch) {
		this.pitch = pitch;
	}

	public double getRoll() {
		return roll;
	}

	public void setRoll(double roll) {
		this.roll = roll;
	}

	@Override
	public String toString() {
		return "Angle [yaw=" + yaw + ", pitch=" + pitch + ", roll=" + roll + "]";
	}

}
