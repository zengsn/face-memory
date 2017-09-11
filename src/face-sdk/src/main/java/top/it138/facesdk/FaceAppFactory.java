package top.it138.facesdk;

public class FaceAppFactory {
	/**
	 * 使用sdk的第一步
	 * @param appKey
	 * @param appSecret
	 * @return
	 */
	public static FaceApp getBykeyAndSecret(String appKey, String appSecret) {
		return new FaceApp(appKey, appSecret);
	}
}
