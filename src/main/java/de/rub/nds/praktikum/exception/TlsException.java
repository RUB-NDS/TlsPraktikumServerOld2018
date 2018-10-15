package de.rub.nds.praktikum.exception;

/**
 * The default exception class which is thrown when something goes wrong during
 * the handshake
 *
 */
public class TlsException extends RuntimeException {

    /**
     * Default constructor
     */
    public TlsException() {
    }

    /**
     * Contructor
     *
     * @param message excpetion message
     */
    public TlsException(String message) {
        super(message);
    }

    /**
     * Constructor
     *
     * @param message exception message
     * @param cause the throwable which caused this exception to be thrown
     */
    public TlsException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor
     *
     * @param cause the throwable which caused this exception to be thrown
     */
    public TlsException(Throwable cause) {
        super(cause);
    }
}
