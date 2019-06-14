package top.it138.face.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import top.it138.face.common.CommonResult;
import top.it138.face.exception.APIException;

@ControllerAdvice
public class GlobalExceptionHandler {
	private final Logger log = LoggerFactory.getLogger(getClass());

	@ExceptionHandler(APIException.class)
	@ResponseBody
	public Object handleBizExp(HttpServletRequest request, Exception ex) {
		log.error("API exception handler  " + ex.getMessage());
		return new CommonResult<Exception>(false, ex);
	}
}
