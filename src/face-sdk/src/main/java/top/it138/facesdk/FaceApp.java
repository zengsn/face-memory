package top.it138.facesdk;

import java.util.List;

public class FaceApp {
	@SuppressWarnings("unused")
	private String appKey;
	@SuppressWarnings("unused")
	private String appSecret;
	private Client client;
	
	FaceApp(String appKey, String appSecret) {
		this.appKey = appKey;
		this.appSecret = appSecret;
		this.client = new Client(appKey, appSecret);
	}
	
	/**
	 * 根据personId获得PERSON对象，需要联网
	 * @param id personId
	 * @return
	 * @throws FaceAppException
	 */
	public Person getPersonById(Long id) throws FaceAppException {
		return client.getPersonById(id);
	}
	
	/**
	 * 创建新的PERSONId，需要联网
	 * @param name 名字 不能为空
	 * @param desc 描述
	 * @return 新建的PERSONId
	 * @throws FaceAppException
	 */
	public Long createNewPersonId(String name, String desc) throws FaceAppException {
		return client.createNewPersonId(name, desc);
	}
	
	/**
	 * 获取该appkey中所有的PERSON
	 * @return list person
	 * @throws FaceAppException
	 */
	public List<Person> getAllPersonInApp() throws FaceAppException {
		return client.getPersons();
	}
}
