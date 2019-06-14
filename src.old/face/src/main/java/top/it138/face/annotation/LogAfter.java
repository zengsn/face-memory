package top.it138.face.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target(ElementType.METHOD)
public @interface LogAfter {
	/**
	 * 方法记录到日志的操作名
	 * @return
	 */
	String operation() default "";
	/**
	 * 方法记录到日志的信息
	 * @return
	 */
	String message() default "";
}
