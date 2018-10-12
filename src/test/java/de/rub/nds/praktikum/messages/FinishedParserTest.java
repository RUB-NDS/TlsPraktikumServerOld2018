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
public class FinishedParserTest {

    @Test
    public void testParse() {
        FinishedParser parser = new FinishedParser(Util.hexStringToByteArray("FFEEDDCCBBAA9988776655443322110000112233445566778899AABBCCDDEEFF"));
        Finished parse = parser.parse();
        assertArrayEquals(Util.hexStringToByteArray("FFEEDDCCBBAA9988776655443322110000112233445566778899AABBCCDDEEFF"), parse.getVerifyData());
    }

}
