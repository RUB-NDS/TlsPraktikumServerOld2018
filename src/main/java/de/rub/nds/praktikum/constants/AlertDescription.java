package de.rub.nds.praktikum.constants;

public enum AlertDescription {

    /**
     * The connection has been closed gracefully
     */
    CLOSE_NOTIFY((byte) 0),
    /**
     * An unexpected message was received
     */
    UNEXPECTED_MESSAGE((byte) 10),
    /**
     * The MAC / TAG / Padding of the record were wrong
     */
    BAD_RECORD_MAC((byte) 20),
    /**
     * Deprecated
     */
    DECRYPTION_FAILED_RESERVED((byte) 21),
    /**
     * The Record is too big/small
     */
    RECORD_OVERFLOW((byte) 22),
    /**
     * Could not decompress data
     */
    DECOMPRESSION_FAILURE((byte) 30),
    /**
     * Something went wrong during the Handshake
     */
    HANDSHAKE_FAILURE((byte) 40),
    /**
     * Deprecated
     */
    NO_CERTIFICATE_RESERVED((byte) 41),
    /**
     * The Certificate is not trusted
     */
    BAD_CERTIFICATE((byte) 42),
    /**
     * The Certificate is not supported
     */
    UNSUPPORTED_CERTIFICATE((byte) 43),
    /**
     * The Certificate has been revoked
     */
    CERTIFICATE_REVOKED((byte) 44),
    /**
     * The Certificate has expired
     */
    CERTIFICATE_EXPIRED((byte) 45),
    /**
     * The Certificate type is unknown / untrusted
     */
    CERTIFICATE_UNKNOWN((byte) 46),
    /**
     * A parameter selection was illegal / the message could not be parsed
     */
    ILLEGAL_PARAMETER((byte) 47),
    /**
     * The CA is unknown / untrusted
     */
    UNKNOWN_CA((byte) 48),
    /**
     * The access was denied
     */
    ACCESS_DENIED((byte) 49),
    /**
     * The message could not be decoded
     */
    DECODE_ERROR((byte) 50),
    /**
     * The message could not be decrypted
     */
    DECRYPT_ERROR((byte) 51),
    /**
     * deprecated
     */
    EXPORT_RESTRICTION_RESERVED((byte) 60),
    /**
     * The selected protocol version invalid / there is no valid protocol
     * version
     */
    PROTOCOL_VERSION((byte) 70),
    /**
     * deprecated
     */
    INSUFFICIENT_SECURITY((byte) 71),
    /**
     * Something went wrong internally unrelated to the peer
     */
    INTERNAL_ERROR((byte) 80),
    /**
     * A fallback to a lower version was detected
     */
    INAPPROPRIATE_FALLBACK((byte) 86),
    /**
     * the connection was closed by the user
     */
    USER_CANCELED((byte) 90),
    /**
     * renegotiation is prohibited
     */
    NO_RENEGOTIATION((byte) 100),
    /**
     * a critical extension is missing
     */
    MISSING_EXTENSION((byte) 109),
    /**
     * the extension is unsupported
     */
    UNSUPPORTED_EXTENSION((byte) 110),
    /**
     * the certificate cannot be obtained
     */
    CERTIFICATE_UNOBTAINABLE((byte) 111),
    /**
     * the server name does not match / is not recognized
     */
    UNRECOGNIZED_NAME((byte) 112),
    /**
     * the certificate status is invalid
     */
    BAD_CERTIFICATE_STATUS_RESPONSE((byte) 113),
    /**
     * the certificate hash value is not matching
     */
    BAD_CERTIFICATE_HASH_VALUE((byte) 114),
    /**
     * the psk identity is unknown
     */
    UNKNOWN_PSK_IDENTITY((byte) 115),
    /**
     * a certificate is required while non was found
     */
    CERTIFICATE_REQUIRED((byte) 116),
    /**
     * no alpn was negotiated
     */
    NO_APPLICATION_PROTOCOL((byte) 120);

    private final byte value;

    private AlertDescription(byte value) {
        this.value = value;
    }

    /**
     * Returns the byte value of the AlertDescription
     *
     * @return byte value of the AlertDescription
     */
    public byte getValue() {
        return value;
    }
}
