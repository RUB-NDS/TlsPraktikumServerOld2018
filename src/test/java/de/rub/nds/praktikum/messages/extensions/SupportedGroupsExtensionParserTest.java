package de.rub.nds.praktikum.messages.extensions;

import de.rub.nds.praktikum.constants.NamedGroup;
import de.rub.nds.praktikum.exception.ParserException;
import de.rub.nds.praktikum.util.Util;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.experimental.categories.Category;

/**
 *
 */
@Category(de.rub.nds.praktikum.Aufgabe2.class)
public class SupportedGroupsExtensionParserTest {

    @Test
    public void testParseValid() {
        SupportedGroupsExtensionParser parser = new SupportedGroupsExtensionParser(Util.hexStringToByteArray("0002001D"));
        SupportedGroupsExtension extension = parser.parse();
        assertTrue("There is exactly one entry in the list", extension.getNamedGroupList().size() == 1);
        assertTrue("The entry in the list is X25519", extension.getNamedGroupList().get(0) == NamedGroup.ECDH_X25519);
        parser = new SupportedGroupsExtensionParser(Util.hexStringToByteArray("0004001D001D"));
        extension = parser.parse();
        assertTrue("There are exactly two entries in the list", extension.getNamedGroupList().size() == 2);
        assertTrue("The first entry is X25519", extension.getNamedGroupList().get(0) == NamedGroup.ECDH_X25519);
        assertTrue("The second entry is X25519 aswell", extension.getNamedGroupList().get(1) == NamedGroup.ECDH_X25519);
    }

    @Test(expected = ParserException.class)
    public void testParseInvalidLength() {
        SupportedGroupsExtensionParser parser = new SupportedGroupsExtensionParser(Util.hexStringToByteArray("0004001D"));
        parser.parse();
    }

    @Test(expected = ParserException.class)
    public void testParseUnevenLength() {
        SupportedGroupsExtensionParser parser = new SupportedGroupsExtensionParser(Util.hexStringToByteArray("0003001DAD"));
        parser.parse();
    }

    @Test(expected = ParserException.class)
    public void testParseToLessLength() {
        SupportedGroupsExtensionParser parser = new SupportedGroupsExtensionParser(Util.hexStringToByteArray("0000001DAD"));
        parser.parse();
    }

    @Test(expected = ParserException.class)
    public void testParseWithGarbageDataLength() {
        SupportedGroupsExtensionParser parser = new SupportedGroupsExtensionParser(Util.hexStringToByteArray("00021DADFFFF"));
        parser.parse();
    }
}
