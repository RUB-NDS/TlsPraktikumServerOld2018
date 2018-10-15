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
public class ClientHelloParserTest {

    private ClientHelloParser parser;

    @Test
    public void testParse() {
        ClientHelloParser parser = new ClientHelloParser(Util.hexStringToByteArray("03031ae8cc70c418da0ea1fbc86fcc165ce83a8105aa73af2cd3bad0c914f5b0db5520779b65b6b9796bd974acca5119f54ebd65a73ec9e372faf8fca04e051f271e15003e130213031301c02cc030009fcca9cca8ccaac02bc02f009ec024c028006bc023c0270067c00ac0140039c009c0130033009d009c003d003c0035002f00ff010000a70000000e000c0000096c6f63616c686f7374000b000403000102000a000c000a001d0017001e00190018002300000016000000170000000d0030002e040305030603080708080809080a080b080408050806040105010601030302030301020103020202040205020602002b0009080304030303020301002d00020101003300260024001d002014a89af64d5c41a76e9584445888700e3165cb7d7e09f4bdebc57f2fdb35682c"));
        ClientHello clientHello = parser.parse();
        assertArrayEquals("Version is not equal", Util.hexStringToByteArray("0303"), clientHello.getVersion());
        assertArrayEquals("Random is not equal", Util.hexStringToByteArray("1ae8cc70c418da0ea1fbc86fcc165ce83a8105aa73af2cd3bad0c914f5b0db55"), clientHello.getRandom());
        assertArrayEquals("SessionId is not equal", Util.hexStringToByteArray("779b65b6b9796bd974acca5119f54ebd65a73ec9e372faf8fca04e051f271e15"), clientHello.getSessionId());
        assertArrayEquals("Ciphersuites are not equal", Util.hexStringToByteArray("130213031301c02cc030009fcca9cca8ccaac02bc02f009ec024c028006bc023c0270067c00ac0140039c009c0130033009d009c003d003c0035002f00ff"), clientHello.getCiphersuites());
        assertArrayEquals("Compressiomethods are not equal", Util.hexStringToByteArray("00"), clientHello.getCompressionMethods());
        assertTrue("We parsed 3 extensions. The client hello actually contains 10 extension, but we only implement 3 this is fine for us", clientHello.getExtensionList().size() == 3);
    }

}
