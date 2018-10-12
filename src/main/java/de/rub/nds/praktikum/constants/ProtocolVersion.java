package de.rub.nds.praktikum.constants;

import java.util.Arrays;

public enum ProtocolVersion {

    /**
     * TLS 1.0 = 0x0301
     */
    TLS_1_0(new byte[]{0x03, 0x01}),
    /**
     * TLS 1.1 = 0x0302
     */
    TLS_1_1(new byte[]{0x03, 0x02}),
    /**
     * TLS 1.2 = 0x0303
     */
    TLS_1_2(new byte[]{0x03, 0x03}),
    /**
     * TLS 1.3 = 0x0304
     */
    TLS_1_3(new byte[]{0x03, 0x04});

    private final byte[] value;

    private ProtocolVersion(byte[] value) {
        this.value = value;
    }

    /**
     * Returns the byte[] value of the protocol version
     *
     * @return the byte[] value of the protocol version
     */
    public byte[] getValue() {
        return value;
    }

    /**
     * Converts a byte[] into a protocol version. If the value is not recognized
     * null is returned
     *
     * @param value the value to convert
     * @return the converted value, null if the value is not recognized
     */
    public static ProtocolVersion convert(byte[] value) {
        if (value.length != 2) {
            throw new IllegalArgumentException("ProtocolVersion value is not 2 bytes long");
        }
        for (ProtocolVersion protocolVersion : ProtocolVersion.values()) {
            if (Arrays.equals(protocolVersion.getValue(), value)) {
                return protocolVersion;
            }
        }
        return null;
    }
}
