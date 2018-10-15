package de.rub.nds.praktikum.exception;

/**
 * This exception is thrown if something goes wrong during the serialisation of
 * a message this should never happen
 *
 */
public class SerializerException extends RuntimeException {

    /**
     * Default constructor
     */
    public SerializerException() {
    }

    /**
     * Contructor
     *
     * @param message excpetion message
     */
    public SerializerException(String message) {
        super(message);
    }

    /**
     * Constructor
     *
     * @param message exception message
     * @param cause the throwable which caused this exception to be thrown
     */
    public SerializerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor
     *
     * @param cause the throwable which caused this exception to be thrown
     */
    public SerializerException(Throwable cause) {
        super(cause);
    }
}
