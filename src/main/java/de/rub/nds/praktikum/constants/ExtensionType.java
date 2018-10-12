package de.rub.nds.praktikum.constants;

import java.util.Arrays;

public enum ExtensionType {

    /**
     * Supported Groups Extension
     */
    SUPPORTED_GROUPS(new byte[]{0x00, 0x0A}),
    /**
     * Supported Versions Extension
     */
    SUPPORTED_VERSIONS(new byte[]{0x00, 0x2B}),
    /**
     * KeyShare Extension
     */
    KEY_SHARE(new byte[]{0x00, 0x33});

    private final byte[] value;

    private ExtensionType(byte[] value) {
        this.value = value;
    }

    /**
     * Returns the byte[] value of this extension type
     *
     * @return the byte[] value of this extnesion type
     */
    public byte[] getValue() {
        return value;
    }

    /**
     * Converts a byte[] into the according extension type
     *
     * @param value The byte[] to convert
     * @return Null if the extension type is not recognized
     */
    public static ExtensionType convert(byte[] value) {
        if (value.length != 2) {
            throw new IllegalArgumentException("ExtensionType value is not 2 bytes long");
        }
        for (ExtensionType extensionType : ExtensionType.values()) {
            if (Arrays.equals(extensionType.getValue(), value)) {
                return extensionType;
            }
        }
        return null;
    }

}
