package top.it138.face.service;

import java.io.IOException;
import java.util.List;

import top.it138.face.entity.Photo;
import top.it138.face.exception.FaceException;

public interface PhotoService extends BaseService<Photo>{
	List<Photo> selectByPersonId(Long personId);
	
	/**
	 * 照片中人脸数目
	 * @param path
	 * @return
	 */
	int photoFaceNum(String path) throws FaceException;
	
	/**
	 * 照片中剪切出人脸
	 * @param srcPath 指定照片的路径
	 * @param dstPaht 指定人脸的存储路径
	 * @return
	 */
	void photoCutFace(String srcPath, String dstPaht) throws FaceException;
	
	
	void deleteFaceByCode(String code) throws IOException;
	
	void deleteFacceById(Long id) throws IOException;

	Double[] faceDistance(String[] compareFaces, String unknowFace) throws FaceException;
}
