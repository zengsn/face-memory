package top.it138.facesdk;

public class Person {
	private Long id;
	private String personCode;
	private Long appId;
	private String name;
	private String des;
	private Client client;

	Client getClient() {
		return client;
	}

	void setClient(Client client) {
		this.client = client;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPersonCode() {
		return personCode;
	}

	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}

	public Long getAppId() {
		return appId;
	}

	public void setAppId(Long appId) {
		this.appId = appId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", personCode=" + personCode + ", appId=" + appId + ", name=" + name + ", des="
				+ des + "]";
	}

	/**
	 * 删除person，删除后不能调用其他方法，不然会出错,需要联网
	 * 
	 * @throws FaceAppException
	 */
	public void deleteThisPerson() throws FaceAppException {
		client.deletePerson(id);
	}

	public void deleteAllPhotos() throws FaceAppException {
		client.deleteAllPhotos(id);
	}

	/**
	 * 添加照片，需要联网
	 * 
	 * @param iamgeByte
	 * @param type
	 * @throws FaceAppException
	 */
	public void addPhoto(byte[] iamgeByte, String type) throws FaceAppException {
		client.addPhoto(id, iamgeByte, type);
	}

	/**
	 * 识别
	 * 
	 * @param iamgeByte
	 *            待识别照片的byte数组
	 * @param type
	 *            照片的格式（只支持jpg，png)
	 * @return 代表是同一个人的概率
	 * @throws FaceAppException
	 */
	public Double recognition(byte[] iamgeByte, String type) throws FaceAppException {
		return client.recognition(id, iamgeByte, type);
	}
}
