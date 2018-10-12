package de.rub.nds.praktikum.constants;

/**
 * Alert level
 */
public enum AlertLevel {

    /**
     * The alert is non fatal, the connection could proceed
     */
    WARNING((byte) 1),
    /**
     * The alert is fatal, the connection has to be terminated
     */
    FATAL((byte) 2);

    private final byte value;

    private AlertLevel(byte value) {
        this.value = value;
    }

    public byte getValue() {
        return value;
    }

}
