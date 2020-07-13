package Exception;

public class IllegalStagingStateException extends Exception {
	private static final long serialVersionUID = 1L;
	private String message;

	public IllegalStagingStateException(String message) {
		super();
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}
}
