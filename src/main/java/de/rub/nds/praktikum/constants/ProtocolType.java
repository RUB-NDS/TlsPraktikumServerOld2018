package de.rub.nds.praktikum.constants;

public enum ProtocolType {

    /**
     * ChangeCipherSpec Protocol
     */
    CHANGE_CIPHER_SPEC((byte) 20),
    /**
     * Alert Protocol
     */
    ALERT((byte) 21),
    /**
     * Handshake Protocol
     */
    HANDSHAKE((byte) 22),
    /**
     * ApplicationData Protocol
     */
    APPLICATION_DATA((byte) 23);

    private final byte value;

    private ProtocolType(byte value) {
        this.value = value;
    }

    /**
     * Returns the byte value of this ProtocolType as seen in the TLS record
     * layer
     *
     * @return the byte vlaue of the protocol type
     */
    public byte getByteValue() {
        return value;
    }
}
