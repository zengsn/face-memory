package top.it138.face.vo;

/**
 * 分页的前端请求参数
 * @author Lenovo
 *
 */
public class PageRequest {
	private int pageNum;
	private int pageSize;
	
	/**
	 * 默认构造页数和页码
	 */
	public PageRequest() {
		pageNum = 1;  
		pageSize = 10;
	}
	
	/**
	 * 获得页码
	 * @return
	 */
	public int getPageNum() {
		return pageNum;
	}
	
	/**
	 * 设置页码
	 * @return
	 */
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	
	/**
	 * 获得分页大小
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}
	
	/**
	 * 设置分页大小
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
}
