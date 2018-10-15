package de.rub.nds.praktikum.messages;

import de.rub.nds.praktikum.constants.AlertDescription;
import de.rub.nds.praktikum.constants.AlertLevel;
import de.rub.nds.praktikum.util.Util;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.experimental.categories.Category;

/**
 *
 */
@Category(de.rub.nds.praktikum.Aufgabe2.class)
public class AlertParserTest {

    @Test
    public void testParse() {
        AlertParser parser = new AlertParser(Util.hexStringToByteArray("0214"));
        Alert parse = parser.parse();
        assertTrue("The level of this alert is warning", parse.getLevel() == AlertLevel.FATAL.getValue());
        assertTrue("The description of this alert is BAD_RECORD_MAC", parse.getDescription() == AlertDescription.BAD_RECORD_MAC.getValue());
        parser = new AlertParser(Util.hexStringToByteArray("0130"));
        parse = parser.parse();
        assertTrue("The level of this alert is warning", parse.getLevel() == AlertLevel.WARNING.getValue());
        assertTrue("The description of this alert is UNKNOWN_CA", parse.getDescription() == AlertDescription.UNKNOWN_CA.getValue());

    }

}
