package de.rub.nds.praktikum.messages;

import de.rub.nds.praktikum.util.Util;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 *
 * @author robert
 */
@Category(de.rub.nds.praktikum.Aufgabe2.class)
public class AlertSerializerTest {

    @Test
    public void testSerializeBytes() {
        Alert alert = new Alert((byte) 2, (byte) 20);
        AlertSerializer serializer = new AlertSerializer(alert);
        byte[] serializedAlert = serializer.serialize();
        Assert.assertArrayEquals(Util.hexStringToByteArray("0214"), serializedAlert);
    }

}
