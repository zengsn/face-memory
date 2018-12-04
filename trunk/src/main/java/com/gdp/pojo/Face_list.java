package com.gdp.pojo;

/**
 * 百度AI的 Detect 接口返回值 result -> face_list 封装数据对象
 * 人脸信息列表.
 * 
 * @author Jashon
 * @since 2018-07-22
 */
public class Face_list {

	private Integer age;	// 年龄 ，当face_field包含age时返回
	private Angle angle;	// 人脸旋转角度参数
	private Double beauty;	// 美丑打分，范围0-100，越大表示越美。当face_fields包含beauty时返回
	private Integer face_probability;
	private Face_shape face_shape;	// 脸型，当face_field包含faceshape时返回
	private String face_token;		// 人脸图片的唯一标识
	private Face_type face_type;	// 真实人脸/卡通人脸 face_field包含facetype时返回
	private Gender gender;		// 性别，face_field包含gender时返回
	private Location location;	// 人脸在图片中的位置
	// private Expression expression;
	// private Glasses glasses;
	
	public Integer getAge() {
		return age;
	}

	public Angle getAngle() {
		return angle;
	}

	public Double getBeauty() {
		return beauty;
	}

	public Integer getFace_probability() {
		return face_probability;
	}

	public Face_shape getFace_shape() {
		return face_shape;
	}

	public String getFace_token() {
		return face_token;
	}

	public Face_type getFace_type() {
		return face_type;
	}

	public Gender getGender() {
		return gender;
	}

	public Location getLocation() {
		return location;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public void setAngle(Angle angle) {
		this.angle = angle;
	}

	public void setBeauty(Double beauty) {
		this.beauty = beauty;
	}

	public void setFace_probability(Integer face_probability) {
		this.face_probability = face_probability;
	}

	public void setFace_shape(Face_shape face_shape) {
		this.face_shape = face_shape;
	}

	public void setFace_token(String face_token) {
		this.face_token = face_token;
	}

	public void setFace_type(Face_type face_type) {
		this.face_type = face_type;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "Face_list [age=" + age + ", angle=" + angle + ", beauty=" + beauty + ", face_probability="
				+ face_probability + ", face_shape=" + face_shape + ", face_token=" + face_token + ", face_type="
				+ face_type + ", gender=" + gender + ", location=" + location + "]";
	}
}
