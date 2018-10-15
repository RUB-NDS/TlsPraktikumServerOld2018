package de.rub.nds.praktikum.messages;

/**
 * Handshake messages follow a certain pattern in tls. They all first contain a
 * type followed by a length field of the size of the remaining bytes
 *
 */
public abstract class HandshakeMessage {

    private final byte type;

    /**
     * The type of the handshake message
     *
     * @param type type of the handshake message
     */
    public HandshakeMessage(byte type) {
        this.type = type;
    }

    /**
     * Returns the type of the handshake message
     *
     * @return the type of the handshake message
     */
    public byte getType() {
        return type;
    }
}
