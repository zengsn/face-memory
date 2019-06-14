package top.it138.facecheck;

import java.util.Properties;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;

public class DriverManager {
	private final static CopyOnWriteArrayList<DriverInfo> registeredDrivers = new CopyOnWriteArrayList<DriverInfo>();
	private static volatile int loginTimeout = 0;
	private static volatile java.io.PrintWriter logWriter = null;
	//用于同步日志的锁
	private final static  Object logSync = new Object();
	/**
	 * 注册驱动
	 * 
	 * @param driver
	 * @throws RecoginitionException
	 */
	public static synchronized void registerDriver(Driver driver) {
		if (driver != null) {
			registeredDrivers.addIfAbsent(new DriverInfo(driver));
		}
	}

	public static int getLoginTimeout() {
		return loginTimeout;
	}

	public static void setLogWriter(java.io.PrintWriter out) {
		logWriter = out;
	}

	public static void setLoginTimeout(int loginTimeout) {
		DriverManager.loginTimeout = loginTimeout;
	}

	public static java.io.PrintWriter getLogWriter() {
		return logWriter;
	}


	/**
	 * 反注册驱动
	 * 
	 * @param driver
	 * @throws RecoginitionException
	 */
	public static synchronized void deregisterDriver(Driver driver) throws RecoginitionException{
		if (driver != null) {
			registeredDrivers.remove(new DriverInfo(driver));
		}
	}

	public static Connection getConnection(Properties info, Class<?> caller) throws RecoginitionException {
		ClassLoader callerCL = caller != null ? caller.getClassLoader() : null;
		synchronized (DriverManager.class) {
			// synchronize加载类加载器
			if (callerCL == null) {
				callerCL = Thread.currentThread().getContextClassLoader();
			}
		}

		RecoginitionException reason = null;

		for (DriverInfo aDriver : registeredDrivers) {
			if (isDriverAllowed(aDriver.driver, callerCL)) {
				try {
					Connection con = aDriver.driver.connect(info);
					if (con != null) {
						// Success!
						return (con);
					}
				} catch (RecoginitionException ex) {
					if (reason == null) {
						reason = ex;
					}
				}
			} else {
				println("    skipping: " + aDriver.getClass().getName());
			}
		}
		//运行到这里，获取Connection失败
		if (reason != null)    {
            println("getConnection failed: " + reason);
            throw reason;
        }
		
		throw new RecoginitionException("没有合适的驱动进行连接");

	}
	
	public static Connection getConnection(Properties info) throws RecoginitionException {
		return getConnection(info, DriverManager.class);

	}

	/**
	 * 打印日志
	 * @param string
	 */
	private static void println(String string) {
		if (logWriter != null) {
			logWriter.println(string);
			logWriter.flush();
		}
		
	}

	private static boolean isDriverAllowed(Driver driver, Class<?> caller) {
		ClassLoader callerCL = caller != null ? caller.getClassLoader() : null;
		return isDriverAllowed(driver, callerCL);
	}

	private static boolean isDriverAllowed(Driver driver, ClassLoader classLoader) {
		boolean result = false;
		if (driver != null) {
			Class<?> aClass = null;
			try {
				aClass = Class.forName(driver.getClass().getName(), true, classLoader);
			} catch (Exception ex) {
				result = false;
			}

			result = (aClass == driver.getClass()) ? true : false;
		}

		return result;
	}

	@SuppressWarnings("restriction")
	@CallerSensitive
    public static java.util.Enumeration<Driver> getDrivers() {
       Vector<Driver> vec = new Vector<Driver>();
       Class<?> callerClass = Reflection.getCallerClass();
       for (DriverInfo aDriver : registeredDrivers) {
    	   if (isDriverAllowed(aDriver.driver, callerClass)) {
    		   vec.add(aDriver.driver);
    	   } else {
               println("    skipping: " + aDriver.getClass().getName());
           }
       }
       
       return vec.elements();
    }

}
