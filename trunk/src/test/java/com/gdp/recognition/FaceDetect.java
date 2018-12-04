package com.gdp.recognition;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import javax.net.ssl.SSLException;

import com.gdp.util.ParaUtil;

public class FaceDetect {

		
		public static void main(String[] args) throws Exception{
			
	        File file = new File("H:\\eclipse-workspace\\zMyJavaWebLearning\\.metadata\\.plugins\\org.eclipse.wst.server.core\\"
	        		+ "tmp0\\wtpwebapps\\facememory\\uploads\\o7uNr5ZOT-MWxK93SRQ5sUe7ytQE\\201808050056.jpg");
/*"face_rectangle": {"width": 386, "top": 468, "left": 193, "height": 386},
{"image_id": "R0ILL3Ay6FuLHkYZ+JPZ/g==", "request_id": "1536059743,aab51a10-ca41-411f-b3a9-eaf3c1dc1364", 
	       "time_used": 317, "faces": [{"landmark": {"mouth_upper_lip_left_contour2": {"y": 715, "x": 339}, "mouth_upper_lip_top": {"y": 704, "x": 380}, "mouth_upper_lip_left_contour1": {"y": 700, "x": 363}, "left_eye_upper_left_quarter": {"y": 548, "x": 270}, "left_eyebrow_lower_middle": {"y": 505, "x": 271}, "mouth_upper_lip_left_contour3": {"y": 729, "x": 351}, "right_eye_top": {"y": 535, "x": 477}, "left_eye_bottom": {"y": 564, "x": 290}, "right_eyebrow_lower_left_quarter": {"y": 495, "x": 460}, "right_eye_pupil": {"y": 545, "x": 478}, "mouth_lower_lip_right_contour1": {"y": 729, "x": 418}, "mouth_lower_lip_right_contour3": {"y": 752, "x": 410}, "mouth_lower_lip_right_contour2": {"y": 746, "x": 433}, "contour_chin": {"y": 855, "x": 391}, "contour_left9": {"y": 849, "x": 343}, "left_eye_lower_right_quarter": {"y": 560, "x": 308}, "mouth_lower_lip_top": {"y": 728, "x": 382}, "right_eyebrow_upper_middle": {"y": 464, "x": 495}, "left_eyebrow_left_corner": {"y": 521, "x": 216}, "right_eye_bottom": {"y": 556, "x": 478}, "contour_left7": {"y": 802, "x": 270}, "contour_left6": {"y": 768, "x": 246}, "contour_left5": {"y": 729, "x": 229}, "contour_left4": {"y": 688, "x": 217}, "contour_left3": {"y": 646, "x": 209}, "contour_left2": {"y": 604, "x": 204}, "contour_left1": {"y": 562, "x": 205}, "left_eye_lower_left_quarter": {"y": 563, "x": 271}, "contour_right1": {"y": 548, "x": 562}, "contour_right3": {"y": 633, "x": 563}, "contour_right2": {"y": 590, "x": 564}, "mouth_left_corner": {"y": 738, "x": 322}, "contour_right4": {"y": 677, "x": 558}, "contour_right7": {"y": 796, "x": 511}, "right_eyebrow_left_corner": {"y": 494, "x": 428}, "nose_right": {"y": 648, "x": 440}, "nose_tip": {"y": 623, "x": 379}, "contour_right5": {"y": 720, "x": 549}, "nose_contour_lower_middle": {"y": 665, "x": 381}, "left_eyebrow_lower_left_quarter": {"y": 511, "x": 243}, "mouth_lower_lip_left_contour3": {"y": 752, "x": 359}, "right_eye_right_corner": {"y": 545, "x": 515}, "right_eye_lower_right_quarter": {"y": 553, "x": 498}, "mouth_upper_lip_right_contour2": {"y": 715, "x": 429}, "right_eyebrow_lower_right_quarter": {"y": 501, "x": 520}, "left_eye_left_corner": {"y": 557, "x": 254}, "mouth_right_corner": {"y": 737, "x": 456}, "mouth_upper_lip_right_contour3": {"y": 729, "x": 418}, "right_eye_lower_left_quarter": {"y": 554, "x": 458}, "left_eyebrow_right_corner": {"y": 494, "x": 330}, "left_eyebrow_lower_right_quarter": {"y": 501, "x": 301}, "right_eye_center": {"y": 547, "x": 477}, "nose_left": {"y": 651, "x": 325}, "mouth_lower_lip_left_contour1": {"y": 729, "x": 351}, "left_eye_upper_right_quarter": {"y": 545, "x": 309}, "right_eyebrow_lower_middle": {"y": 496, "x": 492}, "left_eye_top": {"y": 543, "x": 290}, "left_eye_center": {"y": 555, "x": 290}, "contour_left8": {"y": 830, "x": 303}, "contour_right9": {"y": 846, "x": 439}, "right_eye_left_corner": {"y": 549, "x": 440}, "mouth_lower_lip_bottom": {"y": 755, "x": 383}, "left_eyebrow_upper_left_quarter": {"y": 495, "x": 236}, "left_eye_pupil": {"y": 552, "x": 292}, "right_eyebrow_upper_left_quarter": {"y": 470, "x": 457}, "contour_right8": {"y": 825, "x": 479}, "right_eyebrow_right_corner": {"y": 509, "x": 546}, "right_eye_upper_left_quarter": {"y": 539, "x": 456}, "left_eyebrow_upper_middle": {"y": 483, "x": 267}, "right_eyebrow_upper_right_quarter": {"y": 477, "x": 528}, "nose_contour_left1": {"y": 554, "x": 347}, "nose_contour_left2": {"y": 618, "x": 336}, "mouth_upper_lip_right_contour1": {"y": 700, "x": 398}, "nose_contour_right1": {"y": 553, "x": 410}, "nose_contour_right2": {"y": 615, "x": 426}, "mouth_lower_lip_left_contour2": {"y": 746, "x": 339}, "contour_right6": {"y": 760, "x": 534}, "nose_contour_right3": {"y": 659, "x": 412}, "nose_contour_left3": {"y": 661, "x": 352}, "left_eye_right_corner": {"y": 555, "x": 325}, "left_eyebrow_upper_right_quarter": {"y": 482, "x": 300}, "right_eye_upper_right_quarter": {"y": 538, "x": 497}, "mouth_upper_lip_bottom": {"y": 727, "x": 381}}, 
	       "attributes": {"emotion": {"sadness": 70.953, "neutral": 28.95, "disgust": 0.022, "anger": 0.054, "surprise": 0.008, "fear": 0.006, "happiness": 0.006}, 
	       "beauty": {"female_score": 63.011, "male_score": 65.118}, "gender": {"value": "Male"}, "age": {"value": 21}, 
	       "mouthstatus": {"close": 94.571, "surgical_mask_or_respirator": 0.0, "open": 0.0, "other_occlusion": 5.429}, 
	       "glass": {"value": "None"}, "skinstatus": {"dark_circle": 3.411, "stain": 1.073, "acne": 0.917, "health": 17.044}, 
	       "headpose": {"yaw_angle": 3.5554106, "pitch_angle": -2.4587588, "roll_angle": -2.3181174}, 
	       "blur": {"blurness": {"threshold": 50.0, "value": 1.395}, "motionblur": {"threshold": 50.0, "value": 1.395}, 
	       "gaussianblur": {"threshold": 50.0, "value": 1.395}}, "smile": {"threshold": 50.0, "value": 0.585},
	        "eyestatus": {"left_eye_status": {"normal_glass_eye_open": 0.003, "no_glass_eye_close": 0.06, 
	        "occlusion": 15.144, "no_glass_eye_open": 84.782, "normal_glass_eye_close": 0.001, "dark_glasses": 0.01},
	         "right_eye_status": {"normal_glass_eye_open": 0.015, "no_glass_eye_close": 2.22, "occlusion": 0.128, "no_glass_eye_open": 97.578, "normal_glass_eye_close": 0.005, "dark_glasses": 0.054}}, 
	         "facequality": {"threshold": 70.1, "value": 92.053}, "ethnicity": {"value": "ASIAN"}, 
	         "eyegaze": {"right_eye_gaze": {"position_x_coordinate": 0.484, "vector_z_component": 0.906, "vector_x_component": -0.115, "vector_y_component": 0.407, "position_y_coordinate": 0.451}, 
	         "left_eye_gaze": {"position_x_coordinate": 0.543, "vector_z_component": 0.894, "vector_x_component": 0.204, "vector_y_component": 0.399, "position_y_coordinate": 0.474}}}, 
	         "face_rectangle": {"width": 386, "top": 468, "left": 193, "height": 386}, 
	         "face_token": "aa57655cd2481ba52a9b7cba4871d9ad"}]}
			*/
			byte[] buff = getBytesFromFile(file);
			String url = "https://api-cn.faceplusplus.com/facepp/v3/detect";
	        HashMap<String, String> map = new HashMap<>();
	        HashMap<String, byte[]> byteMap = new HashMap<>();
	        map.put("api_key", ParaUtil.API_KEY);
	        map.put("api_secret", ParaUtil.API_SECRET);
			map.put("return_landmark", "1");
	        map.put("return_attributes", "gender,age,smiling,headpose,facequality,blur,eyestatus,emotion,ethnicity,beauty,mouthstatus,eyegaze,skinstatus");
	        byteMap.put("image_file", buff);
	        try{
	            byte[] bacd = post(url, map, byteMap);
	            String str = new String(bacd);
	            System.out.println(str);
	        }catch (Exception e) {
	        	e.printStackTrace();
			}
		}
		
		private final static int CONNECT_TIME_OUT = 30000;
	    private final static int READ_OUT_TIME = 50000;
	    private static String boundaryString = getBoundary();
	    protected static byte[] post(String url, HashMap<String, String> map, HashMap<String, byte[]> fileMap) throws Exception {
	        HttpURLConnection conne;
	        URL url1 = new URL(url);
	        conne = (HttpURLConnection) url1.openConnection();
	        conne.setDoOutput(true);
	        conne.setUseCaches(false);
	        conne.setRequestMethod("POST");
	        conne.setConnectTimeout(CONNECT_TIME_OUT);
	        conne.setReadTimeout(READ_OUT_TIME);
	        conne.setRequestProperty("accept", "*/*");
	        conne.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundaryString);
	        conne.setRequestProperty("connection", "Keep-Alive");
	        conne.setRequestProperty("user-agent", "Mozilla/4.0 (compatible;MSIE 6.0;Windows NT 5.1;SV1)");
	        DataOutputStream obos = new DataOutputStream(conne.getOutputStream());
	        Iterator iter = map.entrySet().iterator();
	        while(iter.hasNext()){
	            Map.Entry<String, String> entry = (Map.Entry) iter.next();
	            String key = entry.getKey();
	            String value = entry.getValue();
	            obos.writeBytes("--" + boundaryString + "\r\n");
	            obos.writeBytes("Content-Disposition: form-data; name=\"" + key
	                    + "\"\r\n");
	            obos.writeBytes("\r\n");
	            obos.writeBytes(value + "\r\n");
	        }
	        if(fileMap != null && fileMap.size() > 0){
	            Iterator fileIter = fileMap.entrySet().iterator();
	            while(fileIter.hasNext()){
	                Map.Entry<String, byte[]> fileEntry = (Map.Entry<String, byte[]>) fileIter.next();
	                obos.writeBytes("--" + boundaryString + "\r\n");
	                obos.writeBytes("Content-Disposition: form-data; name=\"" + fileEntry.getKey()
	                        + "\"; filename=\"" + encode(" ") + "\"\r\n");
	                obos.writeBytes("\r\n");
	                obos.write(fileEntry.getValue());
	                obos.writeBytes("\r\n");
	            }
	        }
	        obos.writeBytes("--" + boundaryString + "--" + "\r\n");
	        obos.writeBytes("\r\n");
	        obos.flush();
	        obos.close();
	        InputStream ins = null;
	        int code = conne.getResponseCode();
	        try{
	            if(code == 200){
	                ins = conne.getInputStream();
	            }else{
	                ins = conne.getErrorStream();
	            }
	        }catch (SSLException e){
	            e.printStackTrace();
	            return new byte[0];
	        }
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        byte[] buff = new byte[4096];
	        int len;
	        while((len = ins.read(buff)) != -1){
	            baos.write(buff, 0, len);
	        }
	        byte[] bytes = baos.toByteArray();
	        ins.close();
	        return bytes;
	    }
	    private static String getBoundary() {
	        StringBuilder sb = new StringBuilder();
	        Random random = new Random();
	        for(int i = 0; i < 32; ++i) {
	            sb.append("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-".charAt(random.nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_".length())));
	        }
	        return sb.toString();
	    }
	    private static String encode(String value) throws Exception{
	        return URLEncoder.encode(value, "UTF-8");
	    }
	    
	    public static byte[] getBytesFromFile(File f) {
	        if (f == null) {
	            return null;
	        }
	        try {
	            FileInputStream stream = new FileInputStream(f);
	            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
	            byte[] b = new byte[1000];
	            int n;
	            while ((n = stream.read(b)) != -1)
	                out.write(b, 0, n);
	            stream.close();
	            out.close();
	            return out.toByteArray();
	        } catch (IOException e) {
	        }
	        return null;
	    }
}
