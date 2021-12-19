package customExceptions;

public class AccessDeniedException extends Exception {
	public AccessDeniedException(String msg) {
		super(msg);
	}
}
