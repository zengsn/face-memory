package top.it138.face.dto;

import java.io.File;
import java.io.IOException;

import org.aspectj.util.FileUtil;

import top.it138.face.util.Constants;

public class PhotoData {
	private String path;
	private byte[] imgBytes;
	private String suffix;
	

	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public byte[] getImgBytes() {
		if (imgBytes == null) {
			readByes();
		}
		return imgBytes;
	}
	public void setImgBytes(byte[] imgBytes) {
		this.imgBytes = imgBytes;
	}
	
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public void readByes() {
		File file = new File(path);
		try {
			imgBytes = FileUtil.readAsByteArray(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
