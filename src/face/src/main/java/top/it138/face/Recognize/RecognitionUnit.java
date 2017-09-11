package top.it138.face.Recognize;

import top.it138.face.dto.RecognitionData;

public interface RecognitionUnit{
	String getName();
	Double recognize(RecognitionData data);
}
