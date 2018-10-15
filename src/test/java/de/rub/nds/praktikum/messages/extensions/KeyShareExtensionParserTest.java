package de.rub.nds.praktikum.messages.extensions;

import de.rub.nds.praktikum.exception.ParserException;
import de.rub.nds.praktikum.util.Util;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 *
 * @author robert
 */
@Category(de.rub.nds.praktikum.Aufgabe2.class)
public class KeyShareExtensionParserTest {

    @Test
    public void testParseValid() {
        KeyShareExtensionParser parser = new KeyShareExtensionParser(Util.hexStringToByteArray("000E0002000301234500040003678901"));
        KeyShareExtension keyShareExtension = parser.parse();
        assertTrue("The KeyShare extension contains 2 entries, but we parsed: " + keyShareExtension.getEntryList().size(), keyShareExtension.getEntryList().size() == 2);
        assertArrayEquals("The group of the first Entry is wrong", Util.hexStringToByteArray("0002"), keyShareExtension.getEntryList().get(0).getGroupBytes());
        assertArrayEquals("The keyshare of the first Entry is wrong", Util.hexStringToByteArray("012345"), keyShareExtension.getEntryList().get(0).getKeyShare());
        assertArrayEquals("The group of the second Entry is wrong", Util.hexStringToByteArray("0004"), keyShareExtension.getEntryList().get(1).getGroupBytes());
        assertArrayEquals("The keyshare of the second Entry is wrong", Util.hexStringToByteArray("678901"), keyShareExtension.getEntryList().get(1).getKeyShare());
    }
    
    @Test(expected = ParserException.class)
    public void testParseInvalidLengthLong() {
        KeyShareExtensionParser parser = new KeyShareExtensionParser(Util.hexStringToByteArray("000F0002000301234500040003678901"));
        KeyShareExtension keyShareExtension = parser.parse();
    }
    
    @Test(expected = ParserException.class)
    public void testParseInvalidLengthShort() {
        KeyShareExtensionParser parser = new KeyShareExtensionParser(Util.hexStringToByteArray("000D0002000301234500040003678901"));
        KeyShareExtension keyShareExtension = parser.parse();
    }
    
    @Test(expected = ParserException.class)
    public void testParseInvalidKeyShareLengthLong() {
        KeyShareExtensionParser parser = new KeyShareExtensionParser(Util.hexStringToByteArray("000E000200FF01234500040003678901"));
        KeyShareExtension keyShareExtension = parser.parse();
    }
    
    @Test(expected = ParserException.class)
    public void testParseInvalidKeyShareLengthShort() {
        KeyShareExtensionParser parser = new KeyShareExtensionParser(Util.hexStringToByteArray("000E0002000001234500040003678901"));
        KeyShareExtension keyShareExtension = parser.parse();
    }
    
    @Test(expected = ParserException.class)
    public void testParseValidWithGarbageData() {
        KeyShareExtensionParser parser = new KeyShareExtensionParser(Util.hexStringToByteArray("000E0002000301234500040003678901AABBCC"));
        KeyShareExtension keyShareExtension = parser.parse();
    }
}
