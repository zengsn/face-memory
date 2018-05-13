package top.it138.face.exception;

public class RecognitionException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public RecognitionException() {
        super();
    }

    public RecognitionException(String message) {
        super(message);
    }

    public RecognitionException(String message, Throwable cause) {
        super(message, cause);
    }

    public RecognitionException(Throwable cause) {
        super(cause);
    }

    protected RecognitionException(String message, Throwable cause,
                        boolean enableSuppression,
                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
