package top.it138.face.interceptor;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.pagehelper.util.StringUtil;
import com.google.gson.Gson;

import top.it138.face.annotation.LogAfter;
import top.it138.face.annotation.LogBefore;
import top.it138.face.entity.Log;
import top.it138.face.entity.User;
import top.it138.face.service.LogService;
import top.it138.face.util.RequestUtil;
import top.it138.face.util.SessionUtil;

/**
 * 方法拦截器生成日志
 * 
 * @author Lenovo
 *
 */
@Aspect
@Component
public class LogAopInterceptor {
	protected Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	private LogService logService;
	
	// 切入点

	@Pointcut("@annotation(top.it138.face.annotation.LogAfter)")
	public void logAfterMethod() {}
	
	@Pointcut("@annotation(top.it138.face.annotation.LogBefore)")
	public void logBeforeMethod() {}
	

	/**
	 * 后置通知 用于拦截Controller层记录用户的操作
	 * 
	 * @param joinPoint
	 *            切点
	 */
	@After("logAfterMethod()")
	public void after(JoinPoint joinPoint) {
		Method method = findMethod(joinPoint);
		LogAfter logit = method.getAnnotation(LogAfter.class);
		if (logit == null) {
			return;
		}

		

		String operation = logit.operation();
		String message = logit.message();
		
		logToDataBase(joinPoint, operation, message);
	}
	
	@Before("logBeforeMethod()")
	public void before(JoinPoint joinPoint) {
		Method method = findMethod(joinPoint);
		LogBefore logit = method.getAnnotation(LogBefore.class);
		if (logit == null) {
			return;
		}

		

		String operation = logit.operation();
		String message = logit.message();
		
		logToDataBase(joinPoint, operation, message);
	}

	private void logToDataBase(JoinPoint joinPoint, String operation, String message) {
		Class<?> targetClass = joinPoint.getTarget().getClass();
		String methodName = joinPoint.getSignature().getName();
		
		if (StringUtil.isEmpty(operation)) {
			operation = targetClass.getName() + "." + methodName;
		}

		// get current user
		User user = SessionUtil.getCurrentUser();
		String userName = "未登录";
		if (user != null) {
			userName = user.getUsername();
		}
		Log log = new Log();
		log.setUser(userName);
		log.setIp(RequestUtil.getIp());
		log.setTime(new Date());
		log.setOperation(operation);
		log.setMessage(message);
		Gson gson = new Gson();
		//arg
		Object[] arguments = joinPoint.getArgs();
		//String json = gson.toJson(arguments);
		String json = "";
		log.setParams(json);


		// *========控制台输出=========*//
		logger.info(log);
		// *========数据库日志=========*//
		logService.save(log);
	}

	public Method findMethod(JoinPoint joinPoint) {
		Class<?> targetClass = joinPoint.getTarget().getClass();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		int size = arguments.length;
		Class<?>[] argType = new Class[size];
		for (int i = 0; i < size; i++) {
			argType[i] = arguments[i].getClass();
		}

		try {
			Method method = targetClass.getMethod(methodName, argType);
			return method;
		} catch (Exception ignore) {
		}
		
		try {
			Method[] methods = targetClass.getMethods();
			for (Method m : methods) {
				if (m.getName().equals(methodName) && m.getParameterTypes().length == argType.length) {
					return m;
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

}
