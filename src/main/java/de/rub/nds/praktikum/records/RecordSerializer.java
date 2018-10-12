package de.rub.nds.praktikum.records;

import de.rub.nds.praktikum.messages.Serializer;

/**
 * A serializer class which transforms a record object into its byte[]
 * representation
 *
 */
public class RecordSerializer extends Serializer<Record> {

    private final Record record;

    /**
     * Constructor
     *
     * @param record the record that should be serialized
     */
    public RecordSerializer(Record record) {
        this.record = record;
    }

    @Override
    protected byte[] serializeBytes() {
        throw new UnsupportedOperationException("Add code here");
    }

}
