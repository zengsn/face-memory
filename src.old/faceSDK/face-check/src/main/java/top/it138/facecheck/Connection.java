package top.it138.facecheck;

import java.io.IOException;
import java.net.URL;
import java.util.List;


public interface Connection {
	/**
	 * 识别两张人脸为同一个人的可靠度
	 * @param img1 图片1字节数组
	 * @param img2 图片2字节数组
	 * @return 0.0-1.0的double类型表示可靠度，却大越可靠
	 * @throws RecoginitionException
	 */
	double distance(byte[] img1, byte[] img2) throws RecoginitionException;
	/**
	 * 识别两张人脸为同一个人的可靠度
	 * @param img1 图片1Base64编码
	 * @param img2 图片2Base64编码
	 * @return 0.0-1.0的double类型表示可靠度，却大越可靠
	 * @throws RecoginitionException
	 */
	double distance(String Base64Img1, String base64Img1) throws RecoginitionException;
	/**
	 * 识别两张人脸为同一个人的可靠度
	 * @param img1 图片1URL
	 * @param img2 图片2URL
	 * @return 0.0-1.0的double类型表示可靠度，却大越可靠
	 * @throws RecoginitionException
	 */
	public double distance(URL url1, URL url2) throws RecoginitionException;
	/**
	 * 获取当前appKey下所有的分组
	 * @return
	 * @throws IOException
	 */
	List<Group> getAllGroups() throws IOException;
	/**
	 * 获取指定下分组所有的人员
	 * @param GroupId
	 * @return
	 * @throws IOException
	 */
	List<Person> getPersons(Long GroupId)  throws IOException;
	/**
	 * 获取当前appKey下所有的人员
	 * @return
	 * @throws IOException
	 */
	List<Person> getAllPersons()  throws IOException;
	
	/**
	 * 在form指定的分组查找指定照片的人员是谁
	 * @param form
	 * @return 找到返回Person，找不到返回null
	 * @throws RecoginitionException
	 */
	Person find(FindForm form) throws RecoginitionException;
}
