package top.it138.face.Recognize;

import top.it138.face.exception.RecognitionException;

public interface RecognitionUnit{
	String getName();
	Double recognize(String face1Path, String face2Path) throws RecognitionException;
	Double[] recognize(String[] compareFacesPath, String unknowFacePath) throws RecognitionException;
}
