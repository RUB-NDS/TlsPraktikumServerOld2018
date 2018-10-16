package de.rub.nds.praktikum.messages.extensions;

import de.rub.nds.praktikum.constants.ProtocolVersion;
import de.rub.nds.praktikum.exception.ParserException;
import de.rub.nds.praktikum.util.Util;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.experimental.categories.Category;

/**
 *
 */
@Category(de.rub.nds.praktikum.Aufgabe2.class)
public class SupportedVersionsExtensionParserTest {

    @Test
    public void testParse() {
        SupportedVersionsExtensionParser parser = new SupportedVersionsExtensionParser(Util.hexStringToByteArray("0403030304"));
        SupportedVersionsExtension supportedVersions = parser.parse();
        assertTrue("The extension advertises exactly 2 supported versions", supportedVersions.getSupportedVersions().size() == 2);
        assertTrue("The first entry in the Extension is TLS 1.2", supportedVersions.getSupportedVersions().get(0) == ProtocolVersion.TLS_1_2);
        assertTrue("The second entry in the Extension is TLS 1.3", supportedVersions.getSupportedVersions().get(1) == ProtocolVersion.TLS_1_3);
    }

    @Test(expected = ParserException.class)
    public void testParseInvalidLength() {
        SupportedVersionsExtensionParser parser = new SupportedVersionsExtensionParser(Util.hexStringToByteArray("0603030304"));
        parser.parse();
    }

    @Test(expected = ParserException.class)
    public void testParseUnevenLength() {
        SupportedVersionsExtensionParser parser = new SupportedVersionsExtensionParser(Util.hexStringToByteArray("03030303"));
        parser.parse();
    }

    @Test(expected = ParserException.class)
    public void testParseToLessLength() {
        SupportedVersionsExtensionParser parser = new SupportedVersionsExtensionParser(Util.hexStringToByteArray("0003030304"));
        parser.parse();
    }

    @Test(expected = ParserException.class)
    public void testParseWithGarbageDataLength() {
        SupportedVersionsExtensionParser parser = new SupportedVersionsExtensionParser(Util.hexStringToByteArray("0403030304FFFF"));
        parser.parse();
    }

}
