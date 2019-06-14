package top.it138.face.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
	private static Properties systemProperties = null;
	
	
	public static Properties getProperties(String resourceName) {
		InputStream is = PropertiesUtil.class.getClassLoader().getResourceAsStream(resourceName);
		Properties p = new Properties();
		try {
			p.load(is);
			return p;
		} catch (IOException e) {
			throw new RuntimeException("获取Properties资源失败",e);
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Properties getSystemProperties() {
		/*if (systemProperties == null) {
			synchronized (PropertiesUtil.class) {
				if (systemProperties == null) {
					systemProperties = getProperties("system.properties");
				}
			}
		}*/
		systemProperties = getProperties("system.properties");
		return systemProperties;
	}
	
	public static void main(String[] args) {
		System.out.println(getSystemProperties());
	}
}
