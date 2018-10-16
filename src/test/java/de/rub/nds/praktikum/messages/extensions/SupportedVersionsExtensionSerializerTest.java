package de.rub.nds.praktikum.messages.extensions;

import de.rub.nds.praktikum.constants.ProtocolVersion;
import de.rub.nds.praktikum.util.Util;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.experimental.categories.Category;

/**
 *
 */
@Category(de.rub.nds.praktikum.Aufgabe2.class)
public class SupportedVersionsExtensionSerializerTest {

    @Test
    public void testSerializeBytes() {
        SupportedVersionsExtension extension = new SupportedVersionsExtension(ProtocolVersion.TLS_1_2);
        SupportedVersionsExtensionSerializer serializer = new SupportedVersionsExtensionSerializer(extension);
        assertArrayEquals("The content of the supported version extension is just the version bytes", Util.hexStringToByteArray("0303"), serializer.serialize());
        extension = new SupportedVersionsExtension(ProtocolVersion.TLS_1_3);
        serializer = new SupportedVersionsExtensionSerializer(extension);
        assertArrayEquals("The content of the supported version extension is just the version bytes", Util.hexStringToByteArray("0304"), serializer.serialize());
        //NOTE: The Extension looks different for Clients and Servers! Clients have an additional length field
    }

}
