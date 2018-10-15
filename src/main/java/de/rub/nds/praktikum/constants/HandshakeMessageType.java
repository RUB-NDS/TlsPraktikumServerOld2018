package de.rub.nds.praktikum.constants;

public enum HandshakeMessageType {

    /**
     * ClientHello message
     */
    CLIENT_HELLO((byte) 1),
    /**
     * ServerHello message
     */
    SERVER_HELLO((byte) 2),
    /**
     * Certificate message
     */
    CERTIFICATE((byte) 11),
    /**
     * CertificateVerify message
     */
    CERTIFICATE_VERIFY((byte) 15),
    /**
     * Finished message
     */
    FINISHED((byte) 20),
    /**
     * EncryptedExtensions message
     */
    ENCRYPTED_EXTENSIONS((byte) 8);

    private final byte value;

    private HandshakeMessageType(byte value) {
        this.value = value;
    }

    /**
     * Returns the byte value of the message type
     *
     * @return the byte value of the message type
     */
    public byte getValue() {
        return value;
    }
}
