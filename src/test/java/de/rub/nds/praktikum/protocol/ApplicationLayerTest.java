/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.rub.nds.praktikum.protocol;

import de.rub.nds.praktikum.Aufgabe4;
import de.rub.nds.praktikum.constants.ProtocolType;
import de.rub.nds.praktikum.constants.TlsState;
import de.rub.nds.praktikum.records.Record;
import de.rub.nds.praktikum.util.Util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.experimental.categories.Category;

/**
 *
 */
public class ApplicationLayerTest {

    private ApplicationLayer layer;
    private ByteArrayOutputStream outputStream;
    private ByteArrayInputStream inputStream;
    private SessionContext context;

    @Before
    public void setUp() {
        context = new SessionContext(null, null);
        context.setTlsState(TlsState.CONNECTED);
        outputStream = new ByteArrayOutputStream();
        inputStream = new ByteArrayInputStream(Util.hexStringToByteArray("0123456789"));
        RecordLayer recordLayer = new RecordLayer(outputStream, inputStream, context, 0);
        layer = new ApplicationLayer(context, recordLayer);
    }

    @Test
    @Category(Aufgabe4.class)
    public void testSendData() throws Exception {
        layer.sendData(Util.hexStringToByteArray("AABBCC"));
        assertArrayEquals(outputStream.toByteArray(), Util.hexStringToByteArray("1703030003AABBCC"));
    }

    @Test
    @Category(Aufgabe4.class)
    public void testProcessByteStream() {
        layer.processByteStream(Util.hexStringToByteArray("FFDDEE"));
        assertArrayEquals(Util.hexStringToByteArray("FFDDEE"), layer.fetchAppData());
    }

    @Test
    @Category(Aufgabe4.class)
    public void testReceiveData() {
        Record r = new Record(ProtocolType.APPLICATION_DATA.getByteValue(), new byte[]{3, 3}, Util.hexStringToByteArray("DDEEFF"));
        assertArrayEquals(Util.hexStringToByteArray("DDEEFF"), layer.receiveData(r));
    }
}
