package de.rub.nds.praktikum.constants;

import java.util.Arrays;

public enum NamedGroup {

    /**
     * X25519=0x0029
     */
    ECDH_X25519(new byte[]{(byte) 0, (byte) 29});

    private final byte[] value;

    private NamedGroup(byte[] value) {
        this.value = value;
    }

    /**
     * Returns the byte[] value of this group
     *
     * @return the byte[] value of this group
     */
    public byte[] getValue() {
        return value;
    }

    /**
     * Converts a byte[] to a NamedGroup. Returns null if the group is not
     * recognized
     *
     * @param value the byte[] to convert
     * @return Null if the value is not regocnized
     */
    public static NamedGroup convert(byte[] value) {
        if (value.length != 2) {
            throw new IllegalArgumentException("NamedGroup value is not 2 bytes long");
        }
        for (NamedGroup namedGroup : NamedGroup.values()) {
            if (Arrays.equals(namedGroup.getValue(), value)) {
                return namedGroup;
            }
        }
        return null;
    }
}
