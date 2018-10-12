package de.rub.nds.praktikum.messages;

import de.rub.nds.praktikum.util.Util;
import java.util.LinkedList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.experimental.categories.Category;

/**
 *
 * @author robert
 */
@Category(de.rub.nds.praktikum.Aufgabe4.class)
public class CertificateMessageSerializerTest {

    @Test
    public void testSerializeBytes() {
        List<byte[]> certbyteList = new LinkedList<>();
        certbyteList.add(new byte[]{1, 2, 3});
        CertificateMessage message = new CertificateMessage(certbyteList);
        CertificateMessageSerializer serializer = new CertificateMessageSerializer(message);
        System.out.println(Util.bytesToHexString(serializer.serialize()));
        assertArrayEquals("A certificate message with the certificate \"010203\"", Util.hexStringToByteArray("000000080000030102030000"), serializer.serialize());
        certbyteList.add(new byte[]{4, 5, 6});
        message = new CertificateMessage(certbyteList);
        serializer = new CertificateMessageSerializer(message);
        assertArrayEquals("A certificate message with two certificates \"010203\" and \"040506\"", Util.hexStringToByteArray("0000001000000301020300000000030405060000"), serializer.serialize());
    }

}
