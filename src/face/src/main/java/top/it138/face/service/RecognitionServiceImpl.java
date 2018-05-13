package top.it138.face.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import top.it138.face.Recognize.RecognitionUnit;
import top.it138.face.exception.RecognitionException;
import top.it138.face.util.PropertiesUtil;
import top.it138.face.util.UuidUtil;

@Service
public class RecognitionServiceImpl implements RecognitionService {
	@Resource(name = "myRecognitionUnit")
	private RecognitionUnit recognitionUnit;
	private File tmpPath;

	private static final double DEFAULT_TOLERANCE = 0.5;

	private void init() {
		String tmpPathStr = PropertiesUtil.getSystemProperties().getProperty("photoTmpPath");
		tmpPath = new File(tmpPathStr);
		if (!tmpPath.exists()) {
			tmpPath.mkdirs();
		}
	}

	public RecognitionServiceImpl() {
		init();
	}

	public RecognitionServiceImpl(RecognitionUnit recognitionUnit) {
		super();
		init();
		this.recognitionUnit = recognitionUnit;
	}

	public RecognitionUnit getRecognitionUnit() {
		return recognitionUnit;
	}

	public void setRecognitionUnit(RecognitionUnit recognitionUnit) {
		this.recognitionUnit = recognitionUnit;
	}

	@Override
	public Double distance(String face1Path, String face2Path) throws RecognitionException {
		return recognitionUnit.recognize(face1Path, face2Path);
	}

	@Override
	public Double[] distance(String[] compareFacesPath, String unknowFacePath) throws RecognitionException {
		return recognitionUnit.recognize(compareFacesPath, unknowFacePath);
	}

	@Override
	public Double distance(byte[] face1, byte[] face2) throws RecognitionException {
		return this.distance(new byte[][] { face1 }, face2)[0];
	}

	@Override
	public Double[] distance(byte[][] compareFaces, byte[] unknowFace) throws RecognitionException {
		List<String> compareList = new ArrayList<String>(compareFaces.length);
		// compareFaces
		for (byte[] bys : compareFaces) {
			File f = saveFileRadmdomName(bys);
			compareList.add(f.getAbsolutePath());
		}

		File unknowFaceFile = saveFileRadmdomName(unknowFace);
		try {
			// 识别结果
			Double[] result = this.distance(compareList.toArray(new String[0]), unknowFaceFile.getAbsolutePath());
			return result;
		} finally {

			// 删除临时文件
			for (String path : compareList) {
				File f1 = new File(path);
				if (f1.exists()) {
					f1.delete();
				}
			}
			if (unknowFaceFile.exists()) {
				unknowFaceFile.delete();
			}
		}

	}

	/**
	 * 随机名字保存图片文件
	 * 
	 * @param bys
	 * @return
	 * @throws RecognitionException
	 */
	private File saveFileRadmdomName(byte[] bys) throws RecognitionException {
		String ramdomName = UuidUtil.getUUID() + ".jpg";
		File f = new File(tmpPath, ramdomName);
		try {
			FileUtils.writeByteArrayToFile(f, bys);
		} catch (IOException e) {
			throw new RecognitionException("ByteArray写入临时文件夹出错", e);
		}
		return f;
	}

	@Override
	public Boolean compare(String face1Path, String face2Path) throws RecognitionException {
		return this.distance(face1Path, face2Path) > DEFAULT_TOLERANCE;
	}

	@Override
	public Boolean[] compare(String[] compareFacesPath, String unknowFacePath) throws RecognitionException {
		return this.compare(compareFacesPath, unknowFacePath, DEFAULT_TOLERANCE);
	}

	@Override
	public Boolean compare(byte[] face1, byte[] face2) throws RecognitionException {
		return this.distance(face1, face2) > DEFAULT_TOLERANCE;
	}

	@Override
	public Boolean[] compare(byte[][] compareFaces, byte[] unknowFace) throws RecognitionException {
		return this.compare(compareFaces, unknowFace, DEFAULT_TOLERANCE);
	}

	@Override
	public Boolean compare(String face1Path, String face2Path, double tolerance) throws RecognitionException {
		return this.distance(face1Path, face2Path) > tolerance;
	}

	@Override
	public Boolean compare(byte[] face1, byte[] face2, double tolerance) throws RecognitionException {
		return this.distance(face1, face2) > tolerance;
	}

	@Override
	public Boolean[] compare(byte[][] compareFaces, byte[] unknowFace, double tolerance) throws RecognitionException {
		Double[] distance = this.distance(compareFaces, unknowFace);
		Boolean[] bs = new Boolean[distance.length];
		for (int i = 0; i < distance.length; i++) {
			bs[i] = distance[i] > tolerance;
		}
		return bs;
	}

	@Override
	public Boolean[] compare(String[] compareFacesPath, String unknowFacePath, double tolerance)
			throws RecognitionException {
		Double[] distance = this.distance(compareFacesPath, unknowFacePath);
		Boolean[] bs = new Boolean[distance.length];
		for (int i = 0; i < distance.length; i++) {
			bs[i] = distance[i] > tolerance;
		}
		return bs;
	}

	@Override
	public Double[] distance(String[] compareFaces, byte[] unknowFace) throws RecognitionException {
		File f = saveFileRadmdomName(unknowFace);
		try {
			Double[] distance = this.distance(compareFaces, f.getAbsolutePath());
			return distance;
		} finally {
			//删除临时文件
			if (f.exists()) {
				f.delete();
			}
		}

	}

}
