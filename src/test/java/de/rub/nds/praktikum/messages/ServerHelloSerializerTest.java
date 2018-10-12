package de.rub.nds.praktikum.messages;

import de.rub.nds.praktikum.constants.CipherSuite;
import de.rub.nds.praktikum.constants.NamedGroup;
import de.rub.nds.praktikum.constants.ProtocolVersion;
import de.rub.nds.praktikum.messages.extensions.Extension;
import de.rub.nds.praktikum.messages.extensions.KeyShareEntry;
import de.rub.nds.praktikum.messages.extensions.KeyShareExtension;
import de.rub.nds.praktikum.messages.extensions.SupportedVersionsExtension;
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
@Category(de.rub.nds.praktikum.Aufgabe2.class)
public class ServerHelloSerializerTest {

    @Test
    public void testSerializeBytes() {
        List<Extension> extensionList = new LinkedList<>();
        ServerHello hello = new ServerHello(ProtocolVersion.TLS_1_1.getValue(), Util.hexStringToByteArray("00112233445566778899AABBCCDDEEFFFFEEDDCCBBAA99887766554433221100"), Util.hexStringToByteArray("FFEEDDCCBBAA9988776655443322110000112233445566778899AABBCCDDEEFF"), CipherSuite.TLS_AES_128_GCM_SHA256.getValue(), new byte[1], extensionList);
        ServerHelloSerializer serializer = new ServerHelloSerializer(hello);
        assertArrayEquals("A normal ServerHello message without extensions", Util.hexStringToByteArray("030200112233445566778899AABBCCDDEEFFFFEEDDCCBBAA9988776655443322110020FFEEDDCCBBAA9988776655443322110000112233445566778899AABBCCDDEEFF1301000000"), serializer.serialize());
        extensionList.add(new SupportedVersionsExtension(ProtocolVersion.TLS_1_3));
        extensionList.add(new KeyShareExtension(new KeyShareEntry(NamedGroup.ECDH_X25519.getValue(), Util.hexStringToByteArray("208753ed74d295851cad0ba76ef847406111a314e1771a679d5f33ea394d3344"))));
        hello = new ServerHello(ProtocolVersion.TLS_1_2.getValue(), Util.hexStringToByteArray("3b2910cd0a3711b8a249244a9a5b1fd120580167552e171616e05dfde5b1a727"), Util.hexStringToByteArray("779b65b6b9796bd974acca5119f54ebd65a73ec9e372faf8fca04e051f271e15"), CipherSuite.TLS_AES_256_GCM_SHA384.getValue(), new byte[1], extensionList);
        serializer = new ServerHelloSerializer(hello);
        assertArrayEquals("A real ServerHello message with extensions", Util.hexStringToByteArray("03033b2910cd0a3711b8a249244a9a5b1fd120580167552e171616e05dfde5b1a72720779b65b6b9796bd974acca5119f54ebd65a73ec9e372faf8fca04e051f271e15130200002e002b0002030400330024001d0020208753ed74d295851cad0ba76ef847406111a314e1771a679d5f33ea394d3344"), serializer.serialize());
    }
}
