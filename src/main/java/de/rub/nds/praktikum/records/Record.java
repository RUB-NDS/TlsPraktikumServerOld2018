package de.rub.nds.praktikum.records;

/**
 * This class is an abstraction of a tls record. During decryption/encryption
 * the type and data values of this record may change
 *
 */
public class Record {

    private byte type;
    private final byte[] version;
    private byte[] data;

    /**
     * Constructor
     *
     * @param type the protocol type from which this record origins
     * @param version the record version
     * @param data the data that should be transmitted
     */
    public Record(byte type, byte[] version, byte[] data) {
        this.type = type;
        this.version = version;
        this.data = data;
    }

    /**
     * Set the type of the record
     *
     * @param type type of the record
     */
    public void setType(byte type) {
        this.type = type;
    }

    /**
     * Returns the type of the record
     *
     * @return the type of the record
     */
    public byte getType() {
        return type;
    }

    /**
     * Returns the version of the record
     *
     * @return the version of the record
     */
    public byte[] getVersion() {
        return version;
    }

    /**
     * Returns the record payload
     *
     * @return the record payload
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Sets the record payload
     *
     * @param data the record payload
     */
    public void setData(byte[] data) {
        this.data = data;
    }
}
