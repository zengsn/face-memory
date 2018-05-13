package top.it138.face.exception;

public class FaceException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public FaceException() {
        super();
    }

    public FaceException(String message) {
        super(message);
    }

    public FaceException(String message, Throwable cause) {
        super(message, cause);
    }

    public FaceException(Throwable cause) {
        super(cause);
    }

    protected FaceException(String message, Throwable cause,
                        boolean enableSuppression,
                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
