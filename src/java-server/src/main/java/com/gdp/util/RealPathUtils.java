package com.gdp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 初始化 uploads 文件夹在本地磁盘的路径 
 * 
 * @author Jashon
 * @since 2018-09-28
 */
public class RealPathUtils {

	private static Logger logger = LoggerFactory.getLogger(RealPathUtils.class);

//	@Value("jdbc.url")
	public static String uploadsPath = "";
	
	static  {
		// 获取 项目根目录 在磁盘中的实际路径
		String url = Thread.currentThread().getContextClassLoader().getResource("../../").getPath();

		if (url.contains("facememory")) {
			uploadsPath = url.replace("facememory", "uploads");			
		} else {
			uploadsPath = url.replace("ROOT", "uploads");
		}
		logger.info("uploads 文件夹的真实路径: " + uploadsPath);
	}


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
