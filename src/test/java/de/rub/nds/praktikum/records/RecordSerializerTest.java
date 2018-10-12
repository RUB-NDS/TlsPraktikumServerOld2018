package de.rub.nds.praktikum.records;

import de.rub.nds.praktikum.util.Util;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author robert
 */
public class RecordSerializerTest {

    public RecordSerializerTest() {
    }

    @Test
    public void testSerializeBytes() {
        Record record = new Record((byte)5, new byte[]{0x03, 0x03}, new byte[]{01, 02, 03,04});
        RecordSerializer serializer = new RecordSerializer(record);
        byte[] serializeBytes = serializer.serializeBytes();
        assertArrayEquals(serializeBytes, Util.hexStringToByteArray("050303000401020304"));
    }

}
