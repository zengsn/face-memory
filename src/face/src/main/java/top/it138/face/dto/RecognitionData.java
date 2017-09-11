package top.it138.face.dto;

import java.util.List;

/**
 * 识别对象
 * @author Lenovo
 *
 */
public class RecognitionData {
	private Long personId;
	private List<PhotoData> photos;
	private PhotoData RecognitionPhoto;
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public List<PhotoData> getPhotos() {
		return photos;
	}
	public void setPhotos(List<PhotoData> photos) {
		this.photos = photos;
	}
	public PhotoData getRecognitionPhoto() {
		return RecognitionPhoto;
	}
	public void setRecognitionPhoto(PhotoData recognitionPhoto) {
		RecognitionPhoto = recognitionPhoto;
	}
	
	
}
