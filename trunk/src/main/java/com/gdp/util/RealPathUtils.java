package com.gdp.util;

/**
 * 初始化 uploads 文件夹在本地磁盘的路径 
 * 
 * @author Jashon
 * @since 2018-09-28
 */
public class RealPathUtils {

	public static String uploadsPath = "";
	
	static  {
		// 获取 项目根目录 在磁盘中的实际路径
		String url = Thread.currentThread().getContextClassLoader().getResource("../../").getPath();
//		url = url.replaceFirst("/", "");
		if (url.contains("facememory")) {
			uploadsPath = url.replace("facememory", "uploads");			
		} else {
			uploadsPath = url.replace("ROOT", "uploads");
		}
		LogUtils.logger.info("[RealPathUtils] \n uploads 文件夹的真实路径: " + uploadsPath);
	}
/** 旧方法	
	// 服务器端项目根目录的本地路径
	  String rootpath = request.getSession().getServletContext().getRealPath("/");
//-->	// 获取服务器 uploads/ 本地路径
	  String path =  rootpath.replace("facememory", "uploads");
*/

    /**
     * 测试用主函数, 没有实际用途
     * @param args
     */
	public static void main(String[] args) {
		String string = "H:/eclipse-workspace/zMyJavaWebLearning/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/uploads/abbr/oxebW5Rpjc-_L0-DiM740kuCgtd0/201810121738.jpg";
		int  i = string.lastIndexOf("/");
		String string2 = string.substring(0, i);
		System.out.println(string2);
	}
}
