package de.rub.nds.praktikum.exception;

/**
 * This exception should be thrown if we cannot parse the provided bytes into a
 * valid message. This can be the case if we did not receive the complete
 * message yet or when the message is badly formatted
 *
 */
public class ParserException extends RuntimeException {

    /**
     * Default constructor
     */
    public ParserException() {
    }

    /**
     * Contructor
     *
     * @param message excpetion message
     */
    public ParserException(String message) {
        super(message);
    }

    /**
     * Constructor
     *
     * @param message exception message
     * @param cause the throwable which caused this exception to be thrown
     */
    public ParserException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor
     *
     * @param cause the throwable which caused this exception to be thrown
     */
    public ParserException(Throwable cause) {
        super(cause);
    }
}
