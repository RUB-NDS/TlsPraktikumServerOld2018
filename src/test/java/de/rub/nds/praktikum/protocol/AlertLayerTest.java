/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.rub.nds.praktikum.protocol;

import de.rub.nds.praktikum.constants.AlertDescription;
import de.rub.nds.praktikum.constants.AlertLevel;
import de.rub.nds.praktikum.constants.TlsState;
import de.rub.nds.praktikum.util.Util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.experimental.categories.Category;

/**
 *
 * @author robert
 */
@Category(de.rub.nds.praktikum.Aufgabe2.class)
public class AlertLayerTest {

    private AlertLayer layer;
    private ByteArrayOutputStream outputStream;
    private ByteArrayInputStream inputStream;
    private SessionContext context;

    @Before
    public void setUp() {
        context = new SessionContext(null, null);
        outputStream = new ByteArrayOutputStream();
        inputStream = new ByteArrayInputStream(new byte[]{02, 14});
        RecordLayer recordLayer = new RecordLayer(outputStream, inputStream, context, 0);
        layer = new AlertLayer(context, recordLayer);
    }

    @Test
    public void testSendAlert() throws Exception {
        layer.sendAlert(AlertLevel.WARNING, AlertDescription.BAD_RECORD_MAC);
        assertArrayEquals(outputStream.toByteArray(), Util.hexStringToByteArray("15030300020114"));
        assertTrue("Warning alerts should be fine", context.getTlsState() != TlsState.ERROR);
        layer.sendAlert(AlertLevel.FATAL, AlertDescription.BAD_RECORD_MAC);
        assertArrayEquals(outputStream.toByteArray(), Util.hexStringToByteArray("1503030002011415030300020214"));
        assertTrue("Warning fatal alerts should move us to the error state", context.getTlsState() == TlsState.ERROR);
    }

    @Test
    public void testProcessByteStream() {
        layer.processByteStream(Util.hexStringToByteArray("0114"));
        //this is just a warning alert
        assertFalse(context.getTlsState() == TlsState.ERROR);
        layer.processByteStream(Util.hexStringToByteArray("0214"));
        assertTrue(context.getTlsState() == TlsState.ERROR);

    }

}
