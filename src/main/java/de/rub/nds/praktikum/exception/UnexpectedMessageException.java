package de.rub.nds.praktikum.exception;

/**
 * We received a valid message, but this message does not match our current
 * statemachine position and is therefore unexpected
 *
 */
public class UnexpectedMessageException extends RuntimeException {

    /**
     * Default constructor
     */
    public UnexpectedMessageException() {
    }

    /**
     * Contructor
     *
     * @param message excpetion message
     */
    public UnexpectedMessageException(String message) {
        super(message);
    }

    /**
     * Constructor
     *
     * @param message exception message
     * @param cause the throwable which caused this exception to be thrown
     */
    public UnexpectedMessageException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor
     *
     * @param cause the throwable which caused this exception to be thrown
     */
    public UnexpectedMessageException(Throwable cause) {
        super(cause);
    }
}
