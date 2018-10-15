/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.rub.nds.praktikum.protocol;

import de.rub.nds.praktikum.Aufgabe1;
import de.rub.nds.praktikum.Aufgabe2;
import de.rub.nds.praktikum.Aufgabe4;
import de.rub.nds.praktikum.constants.CipherSuite;
import de.rub.nds.praktikum.constants.NamedGroup;
import de.rub.nds.praktikum.constants.TlsState;
import de.rub.nds.praktikum.crypto.KeyGenerator;
import de.rub.nds.praktikum.exception.TlsException;
import de.rub.nds.praktikum.messages.extensions.KeyShareEntry;
import de.rub.nds.praktikum.util.Util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.ECGenParameterSpec;
import java.util.LinkedList;
import java.util.List;
import org.bouncycastle.crypto.tls.Certificate;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.test.TestRandomData;
import org.junit.After;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 */
public class TlsProtocolTest {

    private TlsProtocol protocol;
    private Socket socket;
    private static KeyPair pair;

    @BeforeClass
    public static void setUpClass() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException {
        Security.addProvider(new BouncyCastleProvider());
        ECGenParameterSpec ecGenSpec = new ECGenParameterSpec("secp256r1");
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", "BC");
        keyPairGenerator.initialize(ecGenSpec, new TestRandomData(Util.hexStringToByteArray("c1a0d20c70a2fdfbd45553db21ce66c3aa1a53c082c92e1bfb178360b8723ed4")));
        pair = keyPairGenerator.generateKeyPair();

    }

    @Before
    public void setUp() throws IOException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        socket = mock(Socket.class);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(new byte[]{});
        when(socket.getInputStream()).thenReturn(byteArrayInputStream);

        when(socket.getOutputStream()).thenReturn(byteArrayOutputStream);
        protocol = new TlsProtocol(socket, Certificate.EMPTY_CHAIN, pair.getPrivate(), 10);

    }

    @After
    public void tearDown() {
    }

    @Test(expected = TlsException.class) //Test that connection times out if we do not receive a client hello
    @Category(Aufgabe2.class)
    public void testInitSessionNoClientHello() throws Exception {
        protocol.getContext().setTlsState(TlsState.START);
        protocol.stepConnectionState();
    }

    @Test
    @Category(Aufgabe2.class)
    public void testInitSessionClientHello() throws Exception {
        socket = mock(Socket.class);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Util.hexStringToByteArray("16030301320100012e0303d2070dda5da15b5b1e8df24392f06794436f684f4cde088fd852d7c0b6fdff4c20781bf656122613ab8dfdea009961ebe4bcacc71f1f5547c8a2f753273f2f68ad003e130213031301c02cc030009fcca9cca8ccaac02bc02f009ec024c028006bc023c0270067c00ac0140039c009c0130033009d009c003d003c0035002f00ff010000a70000000e000c0000096c6f63616c686f7374000b000403000102000a000c000a001d0017001e00190018002300000016000000170000000d0030002e040305030603080708080809080a080b080408050806040105010601030302030301020103020202040205020602002b0009080304030303020301002d00020101003300260024001d0020c7ba2d3c2543a66a3e1575dab429f61d3a0d6e680c83e86608330079d9c00b1c"));
        when(socket.getInputStream()).thenReturn(byteArrayInputStream);
        when(socket.getOutputStream()).thenReturn(byteArrayOutputStream);
        protocol = new TlsProtocol(socket, Certificate.EMPTY_CHAIN, pair.getPrivate(), 10);
        protocol.getContext().setTlsState(TlsState.START);
        protocol.stepConnectionState();
        assertTrue(protocol.getContext().getTlsState() == TlsState.RECVD_CH);
    }

    @Test
    @Category(Aufgabe2.class)
    public void testInitSendServerHello() throws Exception {
        socket = mock(Socket.class);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(new byte[0]);
        when(socket.getInputStream()).thenReturn(byteArrayInputStream);
        when(socket.getOutputStream()).thenReturn(byteArrayOutputStream);
        protocol = new TlsProtocol(socket, Certificate.EMPTY_CHAIN, pair.getPrivate(), 10);
        protocol.getContext().setTlsState(TlsState.RECVD_CH);
        List<CipherSuite> suiteList = new LinkedList<>();
        List<KeyShareEntry> keyShareEntryList = new LinkedList<>();
        keyShareEntryList.add(new KeyShareEntry(NamedGroup.ECDH_X25519.getValue(), Util.hexStringToByteArray("AABBCC00112200AABBCC00112200AABBCC00112200AABBCC0011220000001122")));
        protocol.getContext().setClientCipherSuiteList(suiteList);
        protocol.getContext().setKeyShareEntryList(keyShareEntryList);
        protocol.getContext().setClientSessionId(Util.hexStringToByteArray("AABBCCDDEEFF0011AABBCCDDEEFF0011AABBCCDDEEFF0011AABBCCDDEEFF0011"));
        suiteList.add(CipherSuite.TLS_AES_128_GCM_SHA256);
        protocol.getContext().setClientCipherSuiteList(suiteList);
        protocol.stepConnectionState();
        assertTrue(protocol.getContext().getTlsState() == TlsState.NEGOTIATED);
    }

    @Test
    @Category(Aufgabe4.class)
    public void testInitFinished() throws Exception {
        socket = mock(Socket.class);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Util.hexStringToByteArray("1603030024140000203ec350bc78c95325caf5547ac4c4753b276ec2ae49dc0953173864cd7414a9df"));
        when(socket.getInputStream()).thenReturn(byteArrayInputStream);
        when(socket.getOutputStream()).thenReturn(byteArrayOutputStream);
        protocol = new TlsProtocol(socket, Certificate.EMPTY_CHAIN, pair.getPrivate(), 10);
        protocol.getContext().setClientHandshakeTrafficSecret(Util.hexStringToByteArray("c677a34f169db51f85411ddcacb9c461b603f72923d2a00dc918a915052e37a8"));
        protocol.getContext().setServerHandshakeTrafficSecret(Util.hexStringToByteArray("a0b47ba9e740d01c1da4960a174d79d03e71d178d18afa5f77a45cdcad3bff03"));
        protocol.getContext().setClientApplicationTrafficSecret(Util.hexStringToByteArray("0011"));
        protocol.getContext().setServerApplicationTrafficSecret(Util.hexStringToByteArray("2211"));
        protocol.getContext().setTlsState(TlsState.WAIT_FINISHED);
        KeyGenerator.adjustFinishedKeys(protocol.getContext());
        protocol.stepConnectionState();
        assertTrue(protocol.getContext().getTlsState() == TlsState.CONNECTED);
    }

    @Test
    @Category(Aufgabe4.class)
    public void testSendData() throws Exception {
        socket = mock(Socket.class);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(new byte[0]);
        when(socket.getInputStream()).thenReturn(byteArrayInputStream);
        when(socket.getOutputStream()).thenReturn(byteArrayOutputStream);
        protocol = new TlsProtocol(socket, Certificate.EMPTY_CHAIN, pair.getPrivate(), 10);
        protocol.getContext().setTlsState(TlsState.CONNECTED);
        protocol.sendData(new byte[]{(byte) 0xFF});
        assertArrayEquals(Util.hexStringToByteArray("1703030001FF"), byteArrayOutputStream.toByteArray());
    }

    @Test
    @Category(Aufgabe4.class)
    public void testReceiveData() throws Exception {
        socket = mock(Socket.class);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Util.hexStringToByteArray("1703030001FF"));
        when(socket.getInputStream()).thenReturn(byteArrayInputStream);
        when(socket.getOutputStream()).thenReturn(byteArrayOutputStream);
        protocol = new TlsProtocol(socket, Certificate.EMPTY_CHAIN, pair.getPrivate(), 10);
        protocol.getContext().setTlsState(TlsState.CONNECTED);
        byte[] receivedData = protocol.receiveData();
        assertArrayEquals(Util.hexStringToByteArray("FF"), receivedData);
    }

    @Test(expected = TlsException.class)
    @Category(Aufgabe4.class)
    public void testReceiveDataNonAppData() throws Exception {
        socket = mock(Socket.class);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Util.hexStringToByteArray("1603030001FF"));
        when(socket.getInputStream()).thenReturn(byteArrayInputStream);
        when(socket.getOutputStream()).thenReturn(byteArrayOutputStream);
        protocol = new TlsProtocol(socket, Certificate.EMPTY_CHAIN, pair.getPrivate(), 10);
        protocol.getContext().setTlsState(TlsState.CONNECTED);
        protocol.receiveData();
    }

    @Test(expected = TlsException.class)
    @Category(Aufgabe4.class)
    public void testReceiveDataNotConnected() throws Exception {
        socket = mock(Socket.class);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Util.hexStringToByteArray("1603030001FF"));
        when(socket.getInputStream()).thenReturn(byteArrayInputStream);
        when(socket.getOutputStream()).thenReturn(byteArrayOutputStream);
        protocol = new TlsProtocol(socket, Certificate.EMPTY_CHAIN, pair.getPrivate(), 10);
        protocol.getContext().setTlsState(TlsState.START);
        protocol.receiveData();
    }

    @Test(expected = TlsException.class)
    @Category(Aufgabe4.class)
    public void testSendDataNotConnected() throws Exception {
        socket = mock(Socket.class);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(new byte[0]);
        when(socket.getInputStream()).thenReturn(byteArrayInputStream);
        when(socket.getOutputStream()).thenReturn(byteArrayOutputStream);
        protocol = new TlsProtocol(socket, Certificate.EMPTY_CHAIN, pair.getPrivate(), 10);
        protocol.getContext().setTlsState(TlsState.ERROR);
        protocol.sendData(new byte[]{(byte) 0xFF});
    }
}
