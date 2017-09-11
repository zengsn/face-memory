package top.it138.face.exception;

import org.springframework.security.core.AuthenticationException;

public class CaptchaException extends AuthenticationException {
	private static final long serialVersionUID = -677349805125962166L;
	public CaptchaException(String message) {
        super(message);
    }

    public CaptchaException(String message, Throwable cause) {
        super(message, cause);
    }

}
