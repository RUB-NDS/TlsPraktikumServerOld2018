package de.rub.nds.praktikum.messages;

import de.rub.nds.praktikum.exception.SerializerException;
import de.rub.nds.praktikum.util.Util;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * The Serializer is responsible to write an Object T into a byte[] form. This
 * is comparable to byte[] serialization.
 *
 * @param <T> Type of the Object to write
 */
public abstract class Serializer<T> {

    /**
     * The ByteArrayOutputStream with which the byte[] is constructed.
     */
    private ByteArrayOutputStream outputStream;

    /**
     * Constructor for the Serializer
     */
    public Serializer() {
        outputStream = new ByteArrayOutputStream();
    }

    /**
     * This method is responsible to write the appropriate bytes to the output
     * Stream This should be done by calling the different append methods.
     *
     * @return The already serialized Bytes
     */
    protected abstract byte[] serializeBytes();

    /**
     * Adds a byte[] representation of an int to the final byte[]. If the
     * Integer is greater than the specified length only the lower length bytes
     * are serialized.
     *
     * @param i The Integer that should be appended
     * @param length The number of bytes which should be reserved for this
     * Integer
     */
    protected final void appendInt(int i, int length) {
        byte[] bytes = Util.convertIntToBytes(i, length);
        int reconvertedInt = Util.convertToInt(bytes);
        if (reconvertedInt != i) {
            throw new SerializerException("Int " + i + " is to big for a field of length " + length);
        }
        appendBytes(bytes);
    }

    /**
     * Adds a byte to the final byte[].
     *
     * @param b Byte which should be added
     */
    protected final void appendByte(byte b) {
        outputStream.write(b);
    }

    /**
     * Adds a byte[] to the final byte[].
     *
     * @param bytes bytes that should be added
     */
    protected final void appendBytes(byte[] bytes) {
        try {
            outputStream.write(bytes);
        } catch (IOException ex) {
            throw new SerializerException("Could not write to Bytestream", ex);
        }
    }

    /**
     * Returns a byte[] of all already serialized bytes
     *
     * @return all already serailized bytes
     */
    protected byte[] getAlreadySerialized() {
        return outputStream.toByteArray();
    }

    /**
     * Creates the final byte[]
     *
     * @return The final byte[]
     */
    public byte[] serialize() {
        outputStream = new ByteArrayOutputStream();
        serializeBytes();
        return getAlreadySerialized();
    }
}
