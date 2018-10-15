package de.rub.nds.praktikum.records;

import de.rub.nds.praktikum.util.Util;
import org.junit.Assert;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(de.rub.nds.praktikum.Aufgabe1.class)
public class RecordParserTest {

    public RecordParserTest() {
    }

    @Test
    public void testParse() {
        RecordParser parser = new RecordParser(Util.hexStringToByteArray("050303000401020304"));
        Record parse = parser.parse();
        assertTrue(parse.getType() == 5);
        Assert.assertArrayEquals(parse.getVersion(), new byte[]{0x03, 0x03});
        Assert.assertArrayEquals(parse.getData(), Util.hexStringToByteArray("01020304"));
    }
}
