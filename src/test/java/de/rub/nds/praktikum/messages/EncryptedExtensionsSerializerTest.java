package de.rub.nds.praktikum.messages;

import de.rub.nds.praktikum.util.Util;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.experimental.categories.Category;

/**
 *
 * @author robert
 */
@Category(de.rub.nds.praktikum.Aufgabe2.class)
public class EncryptedExtensionsSerializerTest {
    
    @Test
    public void testSerializeBytes() {
        EncryptedExtensions message = new EncryptedExtensions();
        EncryptedExtensionsSerializer serializer = new EncryptedExtensionsSerializer(message);
        assertArrayEquals(Util.hexStringToByteArray("0000"),serializer.serialize());
    }
    
}
