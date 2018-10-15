package de.rub.nds.praktikum.messages;

import de.rub.nds.praktikum.util.Util;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.experimental.categories.Category;

/**
 *
 * @author robert
 */
@Category(de.rub.nds.praktikum.Aufgabe4.class)
public class CertificateVerifySerializerTest {

    @Test
    public void testSerializeBytes() {
        CertificateVerify message = new CertificateVerify(new byte[]{0x01, 0x02}, Util.hexStringToByteArray("AABBCCDD"));
        CertificateVerifySerializer serializer = new CertificateVerifySerializer(message);
        assertArrayEquals("A simple CertificateVerify Message with a SignatureHashAlgorithm of 0x0102 and a signature of 0xAABCCCDD", Util.hexStringToByteArray("01020004AABBCCDD"), serializer.serialize());
    }

}
