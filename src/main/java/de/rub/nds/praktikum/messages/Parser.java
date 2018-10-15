package de.rub.nds.praktikum.messages;

import de.rub.nds.praktikum.exception.ParserException;
import de.rub.nds.praktikum.util.Util;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.util.Arrays;

/**
 * Abstract Parser class which can be used to read a byte array.
 *
 * @param <T> Type of the Object that should be parsed
 */
public abstract class Parser<T> {

    /**
     * Current position in the byte array
     */
    private int pointer;
    /**
     * Array that should be parsed
     */
    private final byte[] array;
    
    /**
     * Constructor for the Parser
     *
     * @param array Array that should be parsed
     */
    public Parser(byte[] array) {
        this.pointer = 0;
        this.array = array;
    }

    /**
     * Parses a number of bytes from the Array and returns them as a byte[].
     * Throws a ParserException if the number of bytes cannot be parsed. Moves
     * the pointer accordingly.
     *
     * @param length Number of bytes to be parsed
     * @return A subbyteArray of according size from the Array
     */
    protected byte[] parseByteArrayField(int length) {
        if (length == 0) {
            return new byte[0];
        }
        if (length < 0) {
            throw new ParserException("Cannot parse field of size " + length);
        }
        int nextPointer = pointer + length;
        if (!enoughBytesLeft(length)) {
            throw new ParserException("Parsing over the end of the array. Current Pointer:" + pointer
                    + " ToParse Length:" + length + " ArrayLength:" + array.length);
        }
        byte[] result = Arrays.copyOfRange(array, pointer, nextPointer);
        pointer = nextPointer;
        return result;
    }

    /**
     * Parses a number of bytes from the Array and returns them as a int. Throws
     * a ParserException if the number of bytes cannot be parsed. Moves the
     * pointer accordingly.
     *
     * @param length Number of bytes to be parsed
     * @return An integer representation of the subbyteArray
     */
    protected int parseIntField(int length) {
        if (length == 0) {
            throw new ParserException("Cannot parse int of size 0");
        }
        return Util.convertToInt(parseByteArrayField(length));
    }

    /**
     * Parses a number of bytes from the Array and returns them as a positive
     * BigInteger. Throws a ParserException if the number of bytes cannot be
     * parsed. Moves the pointer accordingly.
     *
     * @param length Number of bytes to be parsed
     * @return A BigInteger representation of the subbyteArray
     */
    protected BigInteger parseBigIntField(int length) {
        if (length == 0) {
            throw new ParserException("Cannot parse BigInt of size 0");
        }
        return new BigInteger(1, parseByteArrayField(length));
    }

    /**
     * Parses a number of bytes from the Array and returns them as a byte.
     * Throws a ParserException if the number of bytes cannot be parsed. Moves
     * the pointer accordingly.
     *
     * @return An integer representation of the subbyteArray
     */
    protected byte parseByteField() {
        return (byte) Util.convertToInt(parseByteArrayField(1));
    }

    /**
     *
     * @param endSequence
     * @return
     */
    protected String parseStringTill(byte endSequence) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        while (true) {
            byte b = parseByteField();
            stream.write(b);
            if (b == endSequence) {
                return new String(stream.toByteArray());
            }
        }
    }

    /**
     * Returns the current position of the pointer in the array
     *
     * @return Current position of the pointer in the array
     */
    public int getPointer() {
        return pointer;
    }

    /**
     * Set the current position of the pointer in the array
     *
     * @param pointer The new position of the pointer in the array
     */
    public void setPointer(int pointer) {
        this.pointer = pointer;
    }

    /**
     * Returns a byte[] of the already parsed bytes.
     *
     * @return Array of the already parsed bytes.
     */
    protected byte[] getAlreadyParsed() {
        return Arrays.copyOfRange(array, 0, pointer);
    }

    /**
     * Checks if there are atleast count bytes left to read
     *
     * @param count Number of bytes to check for
     * @return True if there are atleast count bytes left to read
     */
    protected boolean enoughBytesLeft(int count) {
        return getBytesLeft() >= count;
    }

    /**
     * Parses n bytes or all remaining bytes
     *
     * @param n number of bytes to parse maxiamlly
     * @return the parsed bytes
     */
    protected byte[] parseArrayOrTillEnd(int n) {
        if (n >= 0 && n < getBytesLeft()) {
            return parseByteArrayField(n);
        } else {
            return parseByteArrayField(getBytesLeft());
        }
    }

    /**
     * Returns how many bytes are left to be parsed
     *
     * @return The number of bytes left to be parsed
     */
    protected int getBytesLeft() {
        return array.length - pointer;
    }

    /**
     * Returns the parsed object.
     *
     * @return The parsed object
     */
    public abstract T parse();
}
