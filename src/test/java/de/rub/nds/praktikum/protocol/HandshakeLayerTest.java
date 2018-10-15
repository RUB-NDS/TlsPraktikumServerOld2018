/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.rub.nds.praktikum.protocol;

import de.rub.nds.praktikum.Aufgabe2;
import de.rub.nds.praktikum.Aufgabe3;
import de.rub.nds.praktikum.Aufgabe4;
import de.rub.nds.praktikum.constants.CipherSuite;
import de.rub.nds.praktikum.constants.NamedGroup;
import de.rub.nds.praktikum.constants.ProtocolType;
import de.rub.nds.praktikum.constants.ProtocolVersion;
import de.rub.nds.praktikum.constants.TlsState;
import de.rub.nds.praktikum.crypto.KeyGenerator;
import de.rub.nds.praktikum.exception.TlsException;
import de.rub.nds.praktikum.exception.UnexpectedMessageException;
import de.rub.nds.praktikum.messages.Parser;
import de.rub.nds.praktikum.messages.extensions.KeyShareEntry;
import de.rub.nds.praktikum.records.Record;
import de.rub.nds.praktikum.records.RecordParser;
import de.rub.nds.praktikum.util.Util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.ECGenParameterSpec;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bouncycastle.crypto.tls.Certificate;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.test.TestRandomData;
import org.junit.Assert;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 *
 * @author robert
 */
public class HandshakeLayerTest {

    private HandshakeLayer handshakeLayer;
    private ByteArrayOutputStream outputStream;
    private ByteArrayInputStream inputStream;
    private SessionContext context;
    private RecordLayer recordLayer;

    @Before
    public void setUp() {
        Security.addProvider(new BouncyCastleProvider());
        context = new SessionContext(null, null);
        outputStream = new ByteArrayOutputStream();
        inputStream = new ByteArrayInputStream(Util.hexStringToByteArray("0123456789"));
        recordLayer = new RecordLayer(outputStream, inputStream, context, 0);
    }

    public HandshakeLayerTest() {
    }

    @Test
    @Category(Aufgabe2.class)
    public void testSendServerHello() {
        List<CipherSuite> suiteList = new LinkedList<>();
        suiteList.add(CipherSuite.TLS_AES_128_GCM_SHA256);
        List<KeyShareEntry> keyShareEntryList = new LinkedList<>();
        keyShareEntryList.add(new KeyShareEntry(NamedGroup.ECDH_X25519.getValue(), Util.hexStringToByteArray("AABBCC00112200AABBCC00112200AABBCC00112200AABBCC0011220000001122")));
        context.setClientCipherSuiteList(suiteList);
        context.setKeyShareEntryList(keyShareEntryList);
        context.setClientSessionId(Util.hexStringToByteArray("AABBCCDDEEFF0011AABBCCDDEEFF0011AABBCCDDEEFF0011AABBCCDDEEFF0011"));
        handshakeLayer = new HandshakeLayer(context, recordLayer);
        handshakeLayer.sendServerHello();
        byte[] serverHelloBytes = outputStream.toByteArray();
        System.out.println("Send SH as:" + Util.bytesToHexString(serverHelloBytes));
        assertTrue(serverHelloBytes.length == 133);//We only implement a minimal version of the SH with only one supported named group - it should contain exactly 133 bytes (with record header)
        //Since we do not have a SH parser we need to create one manually
        RecordParser parser = new RecordParser(serverHelloBytes);
        Record parsedRecord = parser.parse();
        System.out.println("RecordPayload:" + Util.bytesToHexString(parsedRecord.getData()));
        Assert.assertTrue(parsedRecord.getData().length == 0x7A);
        Assert.assertArrayEquals(Util.hexStringToByteArray("0303"), parsedRecord.getVersion());
        Assert.assertTrue(parsedRecord.getType() == ProtocolType.HANDSHAKE.getByteValue());
        Parser tempParser = new Parser(parsedRecord.getData()) {
            @Override
            public Object parse() {
                assertTrue(2 == parseByteField()); //type
                assertTrue(0x76 == parseIntField(3)); //length
                Assert.assertArrayEquals(ProtocolVersion.TLS_1_2.getValue(), parseByteArrayField(2)); //version
                parseByteArrayField(32); //parse random bytes this should just not throw an exception
                //we cannot test here if this is random
                assertTrue(32 == parseByteField()); //session id length
                Assert.assertArrayEquals(Util.hexStringToByteArray("AABBCCDDEEFF0011AABBCCDDEEFF0011AABBCCDDEEFF0011AABBCCDDEEFF0011"), parseByteArrayField(32)); //sessionID
                Assert.assertArrayEquals(CipherSuite.TLS_AES_128_GCM_SHA256.getValue(), parseByteArrayField(2)); //ciphersuite
                Assert.assertArrayEquals(new byte[]{0x00}, parseByteArrayField(1)); //compression
                assertTrue(0x002e == parseIntField(2)); //Extensions
                //we do not parse extension bytes - since they re somewhat random / random order
                return null;
            }
        };
        tempParser.parse();
        assertTrue(context.getSelectedVersion() == ProtocolVersion.TLS_1_3); //This should be true since we only support TLS 1.3
        assertTrue(context.getSuite() == CipherSuite.TLS_AES_128_GCM_SHA256); //this should be true since we only support this one
    }

    @Test
    @Category(Aufgabe3.class)
    public void testSendServerHelloTask3() {
        List<CipherSuite> suiteList = new LinkedList<>();
        suiteList.add(CipherSuite.TLS_AES_128_GCM_SHA256);
        List<KeyShareEntry> keyShareEntryList = new LinkedList<>();
        keyShareEntryList.add(new KeyShareEntry(NamedGroup.ECDH_X25519.getValue(), Util.hexStringToByteArray("AABBCC00112200AABBCC00112200AABBCC00112200AABBCC0011220000001122")));
        context.setClientCipherSuiteList(suiteList);
        context.setKeyShareEntryList(keyShareEntryList);
        context.setClientSessionId(Util.hexStringToByteArray("AABBCCDDEEFF0011AABBCCDDEEFF0011AABBCCDDEEFF0011AABBCCDDEEFF0011"));
        handshakeLayer = new HandshakeLayer(context, recordLayer);
        handshakeLayer.sendServerHello();
        byte[] serverHelloBytes = outputStream.toByteArray();
        System.out.println("Send SH as:" + Util.bytesToHexString(serverHelloBytes));
        assertTrue(serverHelloBytes.length == 133);//We only implement a minimal version of the SH with only one supported named group - it should contain exactly 133 bytes (with record header)
        //Since we do not have a SH parser we need to create one manually
        RecordParser parser = new RecordParser(serverHelloBytes);
        Record parsedRecord = parser.parse();
        System.out.println("RecordPayload:" + Util.bytesToHexString(parsedRecord.getData()));
        Assert.assertTrue(parsedRecord.getData().length == 0x7A);
        Assert.assertArrayEquals(Util.hexStringToByteArray("0303"), parsedRecord.getVersion());
        Assert.assertTrue(parsedRecord.getType() == ProtocolType.HANDSHAKE.getByteValue());
        Parser tempParser = new Parser(parsedRecord.getData()) {
            @Override
            public Object parse() {
                assertTrue(2 == parseByteField()); //type
                assertTrue(0x76 == parseIntField(3)); //length
                Assert.assertArrayEquals(ProtocolVersion.TLS_1_2.getValue(), parseByteArrayField(2)); //version
                parseByteArrayField(32); //parse random bytes this should just not throw an exception
                //we cannot test here if this is random
                assertTrue(32 == parseByteField()); //session id length
                Assert.assertArrayEquals(Util.hexStringToByteArray("AABBCCDDEEFF0011AABBCCDDEEFF0011AABBCCDDEEFF0011AABBCCDDEEFF0011"), parseByteArrayField(32)); //sessionID
                Assert.assertArrayEquals(CipherSuite.TLS_AES_128_GCM_SHA256.getValue(), parseByteArrayField(2)); //ciphersuite
                Assert.assertArrayEquals(new byte[]{0x00}, parseByteArrayField(1)); //compression
                assertTrue(0x002e == parseIntField(2)); //Extensions
                //we do not parse extension bytes - since they re somewhat random / random order
                return null;
            }
        };
        tempParser.parse();
        assertTrue(context.getSelectedVersion() == ProtocolVersion.TLS_1_3); //This should be true since we only support TLS 1.3
        assertTrue(context.getSuite() == CipherSuite.TLS_AES_128_GCM_SHA256); //this should be true since we only support this one
        assertTrue(context.getSharedEcdheSecret() != null);
        assertTrue(context.getHandshakeSecret() != null);
        assertTrue(context.getClientHandshakeTrafficSecret() != null);
        assertTrue(context.getServerHandshakeTrafficSecret() != null);
        assertTrue(context.getClientWriteIv() != null);
        assertTrue(context.getClientWriteKey() != null);
    }

    @Test
    @Category(Aufgabe4.class)
    public void testSendEncryptedExtensionsTask4() {
        handshakeLayer = new HandshakeLayer(context, recordLayer);
        handshakeLayer.sendEncryptedExtensions();
        byte[] encryptedExtensionBytes = outputStream.toByteArray();
        System.out.println("Send EncryptedExtensions as:" + Util.bytesToHexString(encryptedExtensionBytes));
        assertTrue(encryptedExtensionBytes.length == 11);//We send an empty encryptedExtension message in a single record
        RecordParser parser = new RecordParser(encryptedExtensionBytes);
        Record parsedRecord = parser.parse();
        System.out.println("RecordPayload:" + Util.bytesToHexString(parsedRecord.getData()));
        Assert.assertTrue(parsedRecord.getData().length == 6);
        Assert.assertArrayEquals(Util.hexStringToByteArray("0303"), parsedRecord.getVersion());
        Assert.assertTrue(parsedRecord.getType() == ProtocolType.HANDSHAKE.getByteValue());
        Parser tempParser = new Parser(parsedRecord.getData()) {
            @Override
            public Object parse() {
                Assert.assertArrayEquals(Util.hexStringToByteArray("080000020000"), parseByteArrayField(6));
                return null;
            }
        };
        tempParser.parse();
    }

    @Test
    @Category(Aufgabe4.class)
    public void testSendCertificates() {
        handshakeLayer = new HandshakeLayer(context, recordLayer);
        context.setCertificate(Certificate.EMPTY_CHAIN);
        handshakeLayer.sendCertificates();
        byte[] certificateBytes = outputStream.toByteArray();
        System.out.println("Send CertificateMessage as:" + Util.bytesToHexString(certificateBytes));
        assertTrue(certificateBytes.length == 13);//We send an empty encryptedExtension message in a single record
        RecordParser parser = new RecordParser(certificateBytes);
        Record parsedRecord = parser.parse();
        System.out.println("RecordPayload:" + Util.bytesToHexString(parsedRecord.getData()));
        Assert.assertTrue(parsedRecord.getData().length == 8);
        Assert.assertArrayEquals(Util.hexStringToByteArray("0303"), parsedRecord.getVersion());
        Assert.assertTrue(parsedRecord.getType() == ProtocolType.HANDSHAKE.getByteValue());
        Parser tempParser = new Parser(parsedRecord.getData()) {
            @Override
            public Object parse() {
                Assert.assertArrayEquals(Util.hexStringToByteArray("0b00000400000000"), parseByteArrayField(8));
                return null;
            }
        };
        tempParser.parse();
    }

    @Test
    @Category(Aufgabe4.class)
    public void testSendCertificateVerify() {
        handshakeLayer = new HandshakeLayer(context, recordLayer);
        ECGenParameterSpec ecGenSpec = new ECGenParameterSpec("secp256r1");
        KeyPair pair = null;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", "BC");
            keyPairGenerator.initialize(ecGenSpec, new TestRandomData(Util.hexStringToByteArray("c1a0d20c70a2fdfbd45553db21ce66c3aa1a53c082c92e1bfb178360b8723ed4")));
            pair = keyPairGenerator.generateKeyPair();
            context.setKey(pair.getPrivate());
        } catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException ex) {
            Logger.getLogger(HandshakeLayerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        final PublicKey pubKey = pair.getPublic();
        handshakeLayer.sendCertificateVerify();
        byte[] certificateVerifyBytes = outputStream.toByteArray();
        System.out.println("Send CertificateVerify as:" + Util.bytesToHexString(certificateVerifyBytes));
        RecordParser parser = new RecordParser(certificateVerifyBytes);
        Record parsedRecord = parser.parse();
        System.out.println("RecordPayload:" + Util.bytesToHexString(parsedRecord.getData()));
        Assert.assertArrayEquals(Util.hexStringToByteArray("0303"), parsedRecord.getVersion());
        Assert.assertTrue(parsedRecord.getType() == ProtocolType.HANDSHAKE.getByteValue());
        Parser tempParser = new Parser(parsedRecord.getData()) {
            @Override
            public Object parse() {
                Assert.assertArrayEquals(Util.hexStringToByteArray("0f"), parseByteArrayField(1));
                int hsLength = parseIntField(3); //The length is somewhat random dependent
                Assert.assertArrayEquals(Util.hexStringToByteArray("0403"), parseByteArrayField(2)); //Signature and Hash algorithm (secp256r1)
                int signatureLength = parseIntField(2);
                byte[] signature = parseByteArrayField(signatureLength);
                try {
                    Signature sig = Signature.getInstance("SHA256withECDSA");
                    sig.initVerify(pubKey);
                    sig.verify(signature);
                } catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException ex) {
                    ex.printStackTrace();
                    Assert.fail();
                }
                assertTrue(signatureLength == hsLength - 4);
                return null;
            }
        };
        tempParser.parse();
    }

    @Test
    @Category(Aufgabe4.class)
    public void testSendFinished() {
        context.setHandshakeSecret(Util.hexStringToByteArray("00111222"));
        context.setSharedEcdheSecret(Util.hexStringToByteArray("CCDDEEFF"));
        context.setServerHandshakeTrafficSecret(Util.hexStringToByteArray("FFDDEEAABBCC"));
        context.setClientHandshakeTrafficSecret(Util.hexStringToByteArray("0123456789"));
        handshakeLayer = new HandshakeLayer(context, recordLayer);
        handshakeLayer.sendFinished();
        byte[] finishedBytes = outputStream.toByteArray();
        System.out.println("Send FinishedMessage as:" + Util.bytesToHexString(finishedBytes));
        assertTrue(finishedBytes.length == 41);//We send an empty encryptedExtension message in a single record
        RecordParser parser = new RecordParser(finishedBytes);
        Record parsedRecord = parser.parse();
        System.out.println("RecordPayload:" + Util.bytesToHexString(parsedRecord.getData()));
        Assert.assertTrue(parsedRecord.getData().length == 36);
        Assert.assertArrayEquals(Util.hexStringToByteArray("0303"), parsedRecord.getVersion());
        Assert.assertTrue(parsedRecord.getType() == ProtocolType.HANDSHAKE.getByteValue());
        Parser tempParser = new Parser(parsedRecord.getData()) {
            @Override
            public Object parse() {
                Assert.assertArrayEquals(Util.hexStringToByteArray("14"), parseByteArrayField(1)); //Type
                Assert.assertArrayEquals(Util.hexStringToByteArray("000020"), parseByteArrayField(3)); //Length
                Assert.assertArrayEquals(Util.hexStringToByteArray("671483aa33b201e6e099928561b160048d85cb7d8419bad34c84e57cd10e8d9e"), parseByteArrayField(32)); //verify Data
                return null;
            }
        };
        tempParser.parse();
        assertTrue(context.getClientFinishedKey() != null);
        assertTrue(context.getServerFinishedKey() != null);
    }

    @Test
    @Category(Aufgabe2.class)
    public void testProcessByteClientHello() {
        handshakeLayer = new HandshakeLayer(context, recordLayer);
        handshakeLayer.processByteStream(Util.hexStringToByteArray("0100012e0303d2070dda5da15b5b1e8df24392f06794436f684f4cde088fd852d7c0b6fdff4c20781bf656122613ab8dfdea009961ebe4bcacc71f1f5547c8a2f753273f2f68ad003e130213031301c02cc030009fcca9cca8ccaac02bc02f009ec024c028006bc023c0270067c00ac0140039c009c0130033009d009c003d003c0035002f00ff010000a70000000e000c0000096c6f63616c686f7374000b000403000102000a000c000a001d0017001e00190018002300000016000000170000000d0030002e040305030603080708080809080a080b080408050806040105010601030302030301020103020202040205020602002b0009080304030303020301002d00020101003300260024001d0020c7ba2d3c2543a66a3e1575dab429f61d3a0d6e680c83e86608330079d9c00b1c"));
        assertTrue(context.getTlsState() == TlsState.RECVD_CH);
        Assert.assertArrayEquals(Util.hexStringToByteArray("d2070dda5da15b5b1e8df24392f06794436f684f4cde088fd852d7c0b6fdff4c"), context.getClientRandom());
        Assert.assertArrayEquals(Util.hexStringToByteArray("781bf656122613ab8dfdea009961ebe4bcacc71f1f5547c8a2f753273f2f68ad"), context.getClientSessionId());
        Assert.assertTrue(context.getClientCompressions().length == 1);
        Assert.assertTrue(context.getClientCompressions()[0] == 0);
        Assert.assertTrue(context.getClientCipherSuiteList().size() == 31); //The list contains only 3 tls.1.3 suites - the rest will be null since we cannot convert them
        Assert.assertTrue(context.getClientNamedGroupList().size() == 5);
        Assert.assertTrue(context.getClientSupportedVersions().size() == 4);
        Assert.assertTrue(context.getKeyShareEntryList().size() == 1);
    }

    @Test(expected = UnexpectedMessageException.class)
    @Category(Aufgabe2.class)
    public void testProcessClientHelloWrongState1() {
        context.setTlsState(TlsState.CONNECTED);
        handshakeLayer = new HandshakeLayer(context, recordLayer);
        handshakeLayer.processByteStream(Util.hexStringToByteArray("0100012e0303d2070dda5da15b5b1e8df24392f06794436f684f4cde088fd852d7c0b6fdff4c20781bf656122613ab8dfdea009961ebe4bcacc71f1f5547c8a2f753273f2f68ad003e130213031301c02cc030009fcca9cca8ccaac02bc02f009ec024c028006bc023c0270067c00ac0140039c009c0130033009d009c003d003c0035002f00ff010000a70000000e000c0000096c6f63616c686f7374000b000403000102000a000c000a001d0017001e00190018002300000016000000170000000d0030002e040305030603080708080809080a080b080408050806040105010601030302030301020103020202040205020602002b0009080304030303020301002d00020101003300260024001d0020c7ba2d3c2543a66a3e1575dab429f61d3a0d6e680c83e86608330079d9c00b1c"));
    }

    @Test(expected = UnexpectedMessageException.class)
    @Category(Aufgabe2.class)
    public void testProcessClientHelloWrongState2() {
        context.setTlsState(TlsState.ERROR);
        handshakeLayer = new HandshakeLayer(context, recordLayer);
        handshakeLayer.processByteStream(Util.hexStringToByteArray("0100012e0303d2070dda5da15b5b1e8df24392f06794436f684f4cde088fd852d7c0b6fdff4c20781bf656122613ab8dfdea009961ebe4bcacc71f1f5547c8a2f753273f2f68ad003e130213031301c02cc030009fcca9cca8ccaac02bc02f009ec024c028006bc023c0270067c00ac0140039c009c0130033009d009c003d003c0035002f00ff010000a70000000e000c0000096c6f63616c686f7374000b000403000102000a000c000a001d0017001e00190018002300000016000000170000000d0030002e040305030603080708080809080a080b080408050806040105010601030302030301020103020202040205020602002b0009080304030303020301002d00020101003300260024001d0020c7ba2d3c2543a66a3e1575dab429f61d3a0d6e680c83e86608330079d9c00b1c"));
    }

    @Test(expected = UnexpectedMessageException.class)
    @Category(Aufgabe2.class)
    public void testProcessClientHelloWrongState3() {
        context.setTlsState(TlsState.NEGOTIATED);
        handshakeLayer = new HandshakeLayer(context, recordLayer);
        handshakeLayer.processByteStream(Util.hexStringToByteArray("0100012e0303d2070dda5da15b5b1e8df24392f06794436f684f4cde088fd852d7c0b6fdff4c20781bf656122613ab8dfdea009961ebe4bcacc71f1f5547c8a2f753273f2f68ad003e130213031301c02cc030009fcca9cca8ccaac02bc02f009ec024c028006bc023c0270067c00ac0140039c009c0130033009d009c003d003c0035002f00ff010000a70000000e000c0000096c6f63616c686f7374000b000403000102000a000c000a001d0017001e00190018002300000016000000170000000d0030002e040305030603080708080809080a080b080408050806040105010601030302030301020103020202040205020602002b0009080304030303020301002d00020101003300260024001d0020c7ba2d3c2543a66a3e1575dab429f61d3a0d6e680c83e86608330079d9c00b1c"));
    }

    @Test(expected = UnexpectedMessageException.class)
    @Category(Aufgabe2.class)
    public void testProcessClientHelloWrongState4() {
        context.setTlsState(TlsState.RECVD_CH);
        handshakeLayer = new HandshakeLayer(context, recordLayer);
        handshakeLayer.processByteStream(Util.hexStringToByteArray("0100012e0303d2070dda5da15b5b1e8df24392f06794436f684f4cde088fd852d7c0b6fdff4c20781bf656122613ab8dfdea009961ebe4bcacc71f1f5547c8a2f753273f2f68ad003e130213031301c02cc030009fcca9cca8ccaac02bc02f009ec024c028006bc023c0270067c00ac0140039c009c0130033009d009c003d003c0035002f00ff010000a70000000e000c0000096c6f63616c686f7374000b000403000102000a000c000a001d0017001e00190018002300000016000000170000000d0030002e040305030603080708080809080a080b080408050806040105010601030302030301020103020202040205020602002b0009080304030303020301002d00020101003300260024001d0020c7ba2d3c2543a66a3e1575dab429f61d3a0d6e680c83e86608330079d9c00b1c"));
    }

    @Test(expected = UnexpectedMessageException.class)
    @Category(Aufgabe2.class)
    public void testProcessClientHelloWrongState5() {
        context.setTlsState(TlsState.WAIT_FINISHED);
        handshakeLayer = new HandshakeLayer(context, recordLayer);
        handshakeLayer.processByteStream(Util.hexStringToByteArray("0100012e0303d2070dda5da15b5b1e8df24392f06794436f684f4cde088fd852d7c0b6fdff4c20781bf656122613ab8dfdea009961ebe4bcacc71f1f5547c8a2f753273f2f68ad003e130213031301c02cc030009fcca9cca8ccaac02bc02f009ec024c028006bc023c0270067c00ac0140039c009c0130033009d009c003d003c0035002f00ff010000a70000000e000c0000096c6f63616c686f7374000b000403000102000a000c000a001d0017001e00190018002300000016000000170000000d0030002e040305030603080708080809080a080b080408050806040105010601030302030301020103020202040205020602002b0009080304030303020301002d00020101003300260024001d0020c7ba2d3c2543a66a3e1575dab429f61d3a0d6e680c83e86608330079d9c00b1c"));
    }

    @Test
    @Category(Aufgabe4.class)
    public void testProcessByteFinished() {
        context.setClientHandshakeTrafficSecret(Util.hexStringToByteArray("c677a34f169db51f85411ddcacb9c461b603f72923d2a00dc918a915052e37a8"));
        context.setServerHandshakeTrafficSecret(Util.hexStringToByteArray("a0b47ba9e740d01c1da4960a174d79d03e71d178d18afa5f77a45cdcad3bff03"));
        context.setClientApplicationTrafficSecret(Util.hexStringToByteArray("0011"));
        context.setServerApplicationTrafficSecret(Util.hexStringToByteArray("2211"));

        KeyGenerator.adjustFinishedKeys(context);
        handshakeLayer = new HandshakeLayer(context, recordLayer);
        context.setTlsState(TlsState.WAIT_FINISHED);
        handshakeLayer.processByteStream(Util.hexStringToByteArray("140000203ec350bc78c95325caf5547ac4c4753b276ec2ae49dc0953173864cd7414a9df"));
        assertTrue(context.getTlsState() == TlsState.CONNECTED);
    }

    @Test(expected = TlsException.class)
    @Category(Aufgabe4.class)
    public void testProcessByteInvalidFinished() {
        context.setClientHandshakeTrafficSecret(Util.hexStringToByteArray("c677a34f169db51f85411ddcacb9c461b603f72923d2a00dc918a915052e37a8"));
        context.setServerHandshakeTrafficSecret(Util.hexStringToByteArray("a0b47ba9e740d01c1da4960a174d79d03e71d178d18afa5f77a45cdcad3bff03"));
        KeyGenerator.adjustFinishedKeys(context);
        handshakeLayer = new HandshakeLayer(context, recordLayer);
        context.setTlsState(TlsState.WAIT_FINISHED);
        handshakeLayer.processByteStream(Util.hexStringToByteArray("140000203ec350bc78c95325caf5547ac4c4753FF76ec2ae49dc0953173864cd7414a9df"));
    }
}
