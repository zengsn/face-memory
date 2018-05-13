package top.it138.face.Recognize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import top.it138.face.exception.FaceException;
import top.it138.face.exception.RecognitionException;
import top.it138.face.service.PhotoService;

@Service("myRecognitionUnit")
public class MyRecognitionUnit implements RecognitionUnit {
	@Autowired
	PhotoService photoService;

	@Override
	public String getName() {
		return "python";
	}

	@Override
	public Double recognize(String face1Path, String face2Path) throws RecognitionException {
		
		Double[] distance;
		try {
			distance = photoService.faceDistance(new String[] {face1Path}, face2Path);
		} catch (FaceException e) {
			throw new RecognitionException("人脸识别时发生异常,检查路径是否正确，python服务器是否开启", e);
		}
		return distance[0];
	}

	@Override
	public Double[] recognize(String[] compareFacesPath, String unknowFacePath) throws RecognitionException {
		try {
			return photoService.faceDistance(compareFacesPath, unknowFacePath);
		} catch (FaceException e) {
			throw new RecognitionException("人脸识别时发生异常,检查路径是否正确，python服务器是否开启", e);
		}
	}

}
