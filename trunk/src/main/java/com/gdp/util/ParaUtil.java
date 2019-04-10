package com.gdp.util;

/**
 * 系统使用到的参数
 * 
 * @author Jashon
 * @since 2018-07-26
 */
public class ParaUtil {
	
	public static String WXID;
	
	public static String domain_name = "";
	
	/** 小程序唯一标识 : wx4e3fd6b77ad9a410 */
	public static final String APPID = "wxc034dbc625f719df";
	/** 小程序的 app secret : ca2f5b7a4660686d09a6452e83bff09c*/
	public static final String SECRET = "7a2bfc3426678d908b9fc612fe4b314f";
	
	/** face++ api key and secret */
	public static final String API_KEY = "yI1T3VFNPWBip3gWft2TLZexP1bQr_-7";
	public static final String API_SECRET = "4LAopO04vPC1RyzYv1bWWJfYuarqSfuj";
	
	/** 人脸信息状态  */
	public static final Integer FACE_INFO_CREATE = 1;	// 创建人脸信息记录, 未上传图片
	public static final Integer FACE_INFO_UPLOADED = 2;	// 已上传图片
	public static final Integer FACE_INFO_WAITING = 3;	// 待用户操作, 用户第一张含人脸照片, 未确定是否是本人
	public static final Integer FACE_INFO_NOSELF = 4;	// 该图片中人脸不是本人
	public static final Integer FACE_INFO_NOT_CLEAR = 5;// 该图片不含人脸
	public static final Integer FACE_INFO_FINISH = 0;	// 识别完成
	
	/**
	 * 上传图片保存根目录
	 */
//	public static final String UPLOAD_PATH = "H:\\DevInstallation\\apache-tomcat-8.5.23\\webapps\\uploads/";
	// 生产环境图片上传存放物理路径
	public static final String UPLOAD_PATH = "/apps/tomcat8/tomcat8.5/webappsmyface/uploads/";

	
	
	
	
	/** 百度 access_token 有效期为30天. 2018.08.24 */
	/** 没有继续使用百度接口 */
	public static final String ACCESS_TOKEN = "24.47a3b5282b740ef2b53d6b3c94489f76.2592000.1537752018.282335-11567959";

	/** 腾讯接口AppID */
//	public static final String APPID_TENCENT = "2107938287";
	/** 腾讯接口AppKey */
//	public static final String APPKEY_TENCENT = "fC3EMXBUYkXcZdiE";
	
	
}
