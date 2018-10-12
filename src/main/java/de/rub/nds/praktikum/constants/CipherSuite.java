package de.rub.nds.praktikum.constants;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public enum CipherSuite {

    /**
     * TLS_AES_128_GCM_SHA256=0x1301
     */
    TLS_AES_128_GCM_SHA256(new byte[]{0x13, 0x01}),
    /**
     * TLS_AES_256_GCM_SHA384=0x1302
     */
    TLS_AES_256_GCM_SHA384(new byte[]{0x13, 0x02}),
    /**
     * TLS_CHACHA20_POLY1305_SHA256=0x1303
     */
    TLS_CHACHA20_POLY1305_SHA256(new byte[]{0x13, 0x03}),
    /**
     * TLS_AES_128_CCM_SHA256=1304
     */
    TLS_AES_128_CCM_SHA256(new byte[]{0x13, 0x04}),
    /**
     * TLS_AES_128_CCM_8_SHA256=0x1305
     */
    TLS_AES_128_CCM_8_SHA256(new byte[]{0x13, 0x05});

    private final byte[] value;

    private CipherSuite(byte[] value) {
        this.value = value;
    }

    /**
     * Returns the byte[] value of the ciphersuite
     *
     * @return the byte[] value of the ciphersuite
     */
    public byte[] getValue() {
        return value;
    }

    /**
     * Converts a byte[] into a ciphersuite. If the byte[] is not recognized
     * null is returned
     *
     * @param value the value to convert
     * @return the according ciphersuite. Null if the ciphersuite is not
     * recognized
     */
    public static CipherSuite convert(byte[] value) {
        if (value.length != 2) {
            throw new IllegalArgumentException("CipherSuite value is not 2 bytes long");
        }
        for (CipherSuite cipherSuite : CipherSuite.values()) {
            if (Arrays.equals(cipherSuite.getValue(), value)) {
                return cipherSuite;
            }
        }
        return null;
    }

    /**
     * Converts a byte[] into a list of ciphersuites. The byte[] has to be a
     * multiple of 2 bytes long. If the byte array is empty an empty List is
     * returned
     *
     * @param values byte[] which should be converted
     * @return A List with the converted Ciphersuites
     */
    public static List<CipherSuite> convertToList(byte[] values) {
        List<CipherSuite> cipherSuites = new LinkedList<>();
        int pointer = 0;
        if (values.length % 2 != 0) {
            throw new IllegalArgumentException("Ciphersuite is !");
        }
        while (pointer < values.length) {
            byte[] suite = new byte[2];
            suite[0] = values[pointer];
            suite[1] = values[pointer + 1];
            CipherSuite tempSuite = convert(suite);
            cipherSuites.add(tempSuite);
            pointer += 2;
        }
        return cipherSuites;
    }
}
