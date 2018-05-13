package top.it138.face.dto;

import java.util.List;

public class FindForm {
	private String imgUrl;
	private String imgBase64;
	private Boolean isAllPerson;
	private List<Long> GroupId;
	
	public FindForm() {
		isAllPerson = true;
	}
	
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getImgBase64() {
		return imgBase64;
	}
	public void setImgBase64(String imgBase64) {
		this.imgBase64 = imgBase64;
	}
	public Boolean getIsAllPerson() {
		return isAllPerson == null ? false : isAllPerson;
	}
	public void setIsAllPerson(Boolean isAllPerson) {
		this.isAllPerson = isAllPerson;
	}
	public List<Long> getGroupId() {
		return GroupId;
	}
	public void setGroupId(List<Long> groupId) {
		GroupId = groupId;
	}

}
