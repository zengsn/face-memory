package top.it138.facecheck;

public class Group {
    private Long id;
    private String groupName;
    private Integer maxNum;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Integer getMaxNum() {
		return maxNum;
	}
	public void setMaxNum(Integer maxNum) {
		this.maxNum = maxNum;
	}
	@Override
	public String toString() {
		return "Group [id=" + id + ", groupName=" + groupName + ", maxNum=" + maxNum + "]";
	}
    
	
}