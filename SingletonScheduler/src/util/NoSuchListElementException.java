package util;

/**
 * Custom unchecked exception to be thrown when there's not such list element.
 * Offers a constructor method with a default message and a parameterized
 * constructor method to allow for custom messages.
 * 
 * @author Patrick Smith
 *
 */
public class NoSuchListElementException extends RuntimeException {

	/** ID used for serialization */
	private static final long serialVersionUID = 1L;

	/**
	 * Non-parameterized constructor method that uses a default message for
	 * exceptions.
	 */
	public NoSuchListElementException() {
		this("No such element in list.");
	}

	/**
	 * Parameterized constructor method that allows for custom messages.
	 * 
	 * @param message Custom message for use in throwing an exception.
	 */
	public NoSuchListElementException(String message) {
		super(message);
	}

}
