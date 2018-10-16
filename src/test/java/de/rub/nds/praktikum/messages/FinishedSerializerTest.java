package de.rub.nds.praktikum.messages;

import de.rub.nds.praktikum.util.Util;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.experimental.categories.Category;

/**
 *
 */
@Category(de.rub.nds.praktikum.Aufgabe4.class)
public class FinishedSerializerTest {

    @Test
    public void testSerializeBytes() {
        Finished message = new Finished(Util.hexStringToByteArray("FFEEDDCCBBAA9988776655443322110000112233445566778899AABBCCDDEEFF"));
        FinishedSerializer serializer = new FinishedSerializer(message);
        assertArrayEquals("The content of the finished message is just the verify data without a length field", Util.hexStringToByteArray("FFEEDDCCBBAA9988776655443322110000112233445566778899AABBCCDDEEFF"), serializer.serialize());
    }

}
