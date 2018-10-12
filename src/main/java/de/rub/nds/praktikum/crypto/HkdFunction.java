package de.rub.nds.praktikum.crypto;

/**
 * The Hkdf Function as used in TLS 1.3
 */
public class HkdFunction {

    /**
     * Key label
     */
    public static final String KEY = "key";

    /**
     * IV label
     */
    public static final String IV = "iv";

    /**
     * Finished labal
     */
    public static final String FINISHED = "finished";

    /**
     * Derived label
     */
    public static final String DERIVED = "derived";

    /**
     * client handshake traffic secret label
     */
    public static final String CLIENT_HANDSHAKE_TRAFFIC_SECRET = "c hs traffic";

    /**
     * server handshake traffic secret label
     */
    public static final String SERVER_HANDSHAKE_TRAFFIC_SECRET = "s hs traffic";

    /**
     * client application traffic secret label
     */
    public static final String CLIENT_APPLICATION_TRAFFIC_SECRET = "c ap traffic";

    /**
     * server application traffic secret label
     */
    public static final String SERVER_APPLICATION_TRAFFIC_SECRET = "s ap traffic";

    /**
     * Computes HKDF-Extract output as defined in RFC 5869
     *
     * @param salt The Salt
     * @param ikm The IKM
     * @return The HKDF-Extracted ouput
     */
    public static byte[] extract(byte[] salt, byte[] ikm) {
        throw new UnsupportedOperationException("Add code here");
    }

    /**
     * Computes HKDF-Expand output as defined in RFC 5869
     *
     * @param prk THE prk
     * @param info The info
     * @param outLen The output Length
     * @return The expanded bytes
     */
    public static byte[] expand(byte[] prk, byte[] info, int outLen) {
        throw new UnsupportedOperationException("Add code here");
    }

    /**
     * Computes the HKDF-Label as defined in TLS 1.3
     */
    private static byte[] labelEncoder(byte[] hashValue, String labelIn, int outLen) {
        throw new UnsupportedOperationException("Add code here");
    }

    /**
     * Computes Derive-Secret output as defined in TLS 1.3
     *
     * @param prk the provided prk
     * @param labelIn the label
     * @param toHash the data that should be hased
     * @return the derived secret
     */
    public static byte[] deriveSecret(byte[] prk, String labelIn,
            byte[] toHash) {
        throw new UnsupportedOperationException("Add code here");
    }

    /**
     * Computes HKDF-Expand-Label output as defined in TLS 1.3
     *
     * @param prk The Prk
     * @param labelIn The InputLabel
     * @param hashValue The Hashvalue
     * @param outLen The output length
     * @return The expaneded Label bytes
     */
    public static byte[] expandLabel(byte[] prk, String labelIn, byte[] hashValue,
            int outLen) {
        throw new UnsupportedOperationException("Add code here");
    }

    private HkdFunction() {
    }
}
