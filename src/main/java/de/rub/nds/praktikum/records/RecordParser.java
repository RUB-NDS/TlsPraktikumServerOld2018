package de.rub.nds.praktikum.records;

import de.rub.nds.praktikum.messages.Parser;

/**
 * A parser class for tls records. This transforms a byte array into a record
 * object
 *
 */
public class RecordParser extends Parser<Record> {

    /**
     * Cosntructor
     *
     * @param array The byte[] that should be parsed
     */
    public RecordParser(byte[] array) {
        super(array);
    }

    @Override
    public Record parse() {
        throw new UnsupportedOperationException("Add code here");
    }
}
