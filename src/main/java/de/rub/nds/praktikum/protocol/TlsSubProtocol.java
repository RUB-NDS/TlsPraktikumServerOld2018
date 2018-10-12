package de.rub.nds.praktikum.protocol;

/**
 * The TLS protocol consists of multiple protocols which are layered in parallel
 * to each other on top of the record layer. These protocol inherit from this
 * class.
 *
 */
public abstract class TlsSubProtocol {

    private final byte type;

    /**
     * Constructor
     *
     * @param type The type of the protocol
     */
    public TlsSubProtocol(byte type) {
        this.type = type;
    }

    /**
     * Returns the type of the protocol
     *
     * @return the type of the protocol
     */
    public byte getType() {
        return type;
    }

    /**
     * If the record layer decapuslates a byte stream from the record the
     * resulting bytes are passed to this method.
     *
     * @param stream byte[] extracted from the record layer for this protocol
     */
    public abstract void processByteStream(byte[] stream);
}
