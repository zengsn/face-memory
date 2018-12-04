package com.gdp.util;

public class OpencvRecognitionUtil {

	/**
	 * 对比两张人脸照片是否是同个人
	 * 
	 * @param resPic	人脸照片1
	 * @param tarPic	人脸照片2
	 * @return
	 */
	/*public static boolean matcher(String resPic, String tarPic) {
		String result = "";
		
//		String s1 = "H:\\eclipse-workspace\\zMyJavaWebLearning\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\"
//        		+ "wtpwebapps\\uploads\\o7uNr5ZOT-MWxK93SRQ5sUe7yt\\201808121126.jpg";
//        String s2 = "H:\\eclipse-workspace\\zMyJavaWebLearning\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\"
//        		+ "wtpwebapps\\uploads\\o7uNr5ZOT-MWxK93SRQ5sUe7ytQE\\201808051116.jpg";
        //CV.cutPic(s1);
        //CV.cutPic(s2);
		FaceRecognitionFactory factory = new FaceRecognitionFactory();
        FaceRecognition faceRecognition = factory.getInstance();
        try {
			faceRecognition.loadModel();
			result = faceRecognition.judge(resPic, tarPic);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        FaceRecognition faceRecognition1 = factory.getInstance();
        System.out.println(faceRecognition);
        System.out.println(faceRecognition1);
        if("match".equals(result))
        	return true;
        else 
        	return false;
	}*/
	
	/**
	 * 测试用
	 */
/*	public static void main(String[] args) {
		matcher("H:\\eclipse-workspace\\zMyJavaWebLearning\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\"
        		+ "wtpwebapps\\uploads\\o7uNr5ZOT-MWxK93SRQ5sUe7yt\\201808121126.jpg",
        		"H:\\eclipse-workspace\\zMyJavaWebLearning\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\"
                		+ "wtpwebapps\\uploads\\o7uNr5ZOT-MWxK93SRQ5sUe7ytQE\\201808051116.jpg");
	}*/
}
