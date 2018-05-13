package top.it138.face.service;

import top.it138.face.exception.RecognitionException;

//API调用的人脸识别函数
public interface RecognitionService {
	/**
	 * 识别相似度
	 * @param face1Path
	 * @param face2Path
	 * @return
	 * @throws RecognitionException
	 */
	Double distance(String face1Path, String face2Path) throws RecognitionException;
	Double[] distance(String[] compareFacesPath, String unknowFacePath) throws RecognitionException;
	Double distance(byte[] face1, byte[] face2) throws RecognitionException;
	Double[] distance(byte[][] compareFaces, byte[] unknowFace) throws RecognitionException;
	Double[] distance(String[] compareFaces, byte[] unknowFace) throws RecognitionException;
	
	/**
	 * 识别是否同一个人,distance>0.5返回true
	 * @param face1Path
	 * @param face2Path
	 * @return
	 * @throws RecognitionException
	 */
	Boolean compare(String face1Path, String face2Path) throws RecognitionException;
	Boolean[] compare(String[] compareFacesPath, String unknowFacePath) throws RecognitionException;
	Boolean compare(byte[] face1, byte[] face2) throws RecognitionException;
	Boolean[] compare(byte[][] compareFaces, byte[] unknowFace) throws RecognitionException;
	
	/**
	 * 识别是否同一个人,distance>tolerance返回true
	 * @param face1Path
	 * @param face2Path
	 * @param tolerance
	 * @return
	 * @throws RecognitionException
	 */
	Boolean compare(String face1Path, String face2Path, double tolerance) throws RecognitionException;
	Boolean[] compare(String[] compareFacesPath,  String unknowFacePath, double tolerance) throws RecognitionException;
	Boolean compare(byte[] face1, byte[] face2, double tolerance) throws RecognitionException;
	Boolean[] compare(byte[][] compareFaces, byte[] unknowFace, double tolerance) throws RecognitionException;
}