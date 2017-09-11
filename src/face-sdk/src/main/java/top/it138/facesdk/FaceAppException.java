package top.it138.facesdk;

public class FaceAppException extends Exception {
	private static final long serialVersionUID = 1L;

	public FaceAppException(String message) {
		super(message);
	}

	public FaceAppException(String message, Throwable cause) {
		super(message, cause);
	}
}
