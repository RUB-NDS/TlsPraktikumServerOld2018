package de.rub.nds.praktikum.protocol;

import de.rub.nds.praktikum.constants.ProtocolType;
import de.rub.nds.praktikum.constants.ProtocolVersion;
import de.rub.nds.praktikum.records.Record;
import de.rub.nds.praktikum.util.Util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.security.Security;
import java.util.List;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author robert
 */
public class RecordLayerTest {

    private RecordLayer recordLayer;

    private ByteArrayOutputStream outputStream;

    private ByteArrayInputStream inputStream;

    /**
     *
     */
    @Before
    public void setUp() {
        Security.addProvider(new BouncyCastleProvider());
        outputStream = new ByteArrayOutputStream();

    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testEncrypt() throws Exception {
        Record record = new Record(ProtocolType.HANDSHAKE.getByteValue(), new byte[]{03, 03}, Util.hexStringToByteArray("080000240022000a00140012001d00170018001901000101010201030104001c00024001000000000b0001b9000001b50001b0308201ac30820115a003020102020102300d06092a864886f70d01010b0500300e310c300a06035504031303727361301e170d3136303733303031323335395a170d3236303733303031323335395a300e310c300a0603550403130372736130819f300d06092a864886f70d010101050003818d0030818902818100b4bb498f8279303d980836399b36c6988c0c68de55e1bdb826d3901a2461eafd2de49a91d015abbc9a95137ace6c1af19eaa6af98c7ced43120998e187a80ee0ccb0524b1b018c3e0b63264d449a6d38e22a5fda430846748030530ef0461c8ca9d9efbfae8ea6d1d03e2bd193eff0ab9a8002c47428a6d35a8d88d79f7f1e3f0203010001a31a301830090603551d1304023000300b0603551d0f0404030205a0300d06092a864886f70d01010b05000381810085aad2a0e5b9276b908c65f73a7267170618a54c5f8a7b337d2df7a594365417f2eae8f8a58c8f8172f9319cf36b7fd6c55b80f21a03015156726096fd335e5e67f2dbf102702e608ccae6bec1fc63a42a99be5c3eb7107c3c54e9b9eb2bd5203b1c3b84e0a8b2f759409ba3eac9d91d402dcc0cc8f8961229ac9187b42b4de100000f00008408040080754040d0ddab8cf0e2da2bc4995b868ad745c8e1564e33cde17880a42392cc624aeef6b67bb3f0ae71d9d54a2309731d87dc59f642d733be2eb27484ad8a8c8eb3516a7ac57f2625e2b5c0888a8541f4e734f73d054761df1dd02f0e3e9a33cfa10b6e3eb4ebf7ac053b01fdabbddfc54133bcd24c8bbdceb223b2aa03452a2914000020ac86acbc9cd25a45b57ad5b64db15d4405cf8c80e314583ebf3283ef9a99310c"));
        SessionContext context = new SessionContext(null, null);
        context.setServerWriteIv(Util.hexStringToByteArray("f7f6884c4981716c2d0d29a4"));
        context.setServerWriteKey(Util.hexStringToByteArray("c66cb1aec519df44c91e10995511ac8b"));
        context.setSelectedVersion(ProtocolVersion.TLS_1_3);
        inputStream = new ByteArrayInputStream(new byte[0]);
        recordLayer = new RecordLayer(outputStream, inputStream, context, 0);
        recordLayer.activateEncryption();
        recordLayer.resetSequencnumbers();
        recordLayer.encrypt(record);
        assertArrayEquals(Util.hexStringToByteArray("f10b26d8fcaf67b5b828f712122216a1cd14187465b77637cbcd78539128bb93246dcca1af56f1eaa271666077455bc54965d85f05f9bd36d6996171eb536aff613eeddc42bad5a2d2227c4606f1215f980e7afaf56bd3b85a51be130003101a758d077b1c891d8e7a22947e5a229851fd42a9dd422608f868272abf92b3d43fb46ac420259346067f66322fd708885680f4b4433c29116f2dfa529e09bba53c7cd920121724809eaddcc84307ef46fc51a0b33d99d39db337fcd761ce0f2b02dc73dedb6fddb77c4f8099bde93d5bee08bcf2131f29a2a37ff07949e8f8bcdd3e8310b8bf8b3444c85aaf0d2aeb2d4f36fd14d5cb51fcebff418b3827136ab9529e9a3d3f35e4c0ae749ea2dbc94982a1281d3e6daab719aa4460889321a008bf10fa06ac0c61cc122cc90d5e22c0030c986ae84a33a0c47df174bcfbd50bf78ffdf24051ab423db63d5815db2f830040f30521131c98c66f16c362addce2fba0602cf0a7dddf22e8def7516cdfee95b4056cc9ad38c95352335421b5b1ffbadf75e5212fdad7a75f52a2801486a1eec3539580bee0e4b337cda6085ac9eccd1a0f1a46cebfbb5cdfa3251ac28c3bc826148c6d8c1eb6a06f77f6ff632c6a83e283e8f9df7c6dbabf1c6ea40629a85b43ab0c73d34f9d5072832a104eda3f75f5d83da6e14822a18e14099d749eafd823ca2ac7542086501eca206ce7887920008573757ce2f230a890782b99cc682377beee812756d04f9025135fb599d746fefe7316c922ac265ca0d29021375adb63c1509c3e242dfb92b8dee891f7368c4058399b8db9075f2dcc8216194e503b6652d87d2cb41f99adfdcc5be5ec7e1e6326ac22d70bd3ba652827532d669aff005173597f8039c3ea4922d3ec757670222f6ac29b93e90d7ad3f6dd96328e429cfcfd5cca22707fe2d86ad1dcb0be756e8e"), record.getData());
    }
    
    /**
     *
     * @throws Exception
     */
    @Test
    public void testDecrypt() throws Exception {
        Record record = new Record(ProtocolType.HANDSHAKE.getByteValue(), new byte[]{03, 03}, Util.hexStringToByteArray("f10b26d8fcaf67b5b828f712122216a1cd14187465b77637cbcd78539128bb93246dcca1af56f1eaa271666077455bc54965d85f05f9bd36d6996171eb536aff613eeddc42bad5a2d2227c4606f1215f980e7afaf56bd3b85a51be130003101a758d077b1c891d8e7a22947e5a229851fd42a9dd422608f868272abf92b3d43fb46ac420259346067f66322fd708885680f4b4433c29116f2dfa529e09bba53c7cd920121724809eaddcc84307ef46fc51a0b33d99d39db337fcd761ce0f2b02dc73dedb6fddb77c4f8099bde93d5bee08bcf2131f29a2a37ff07949e8f8bcdd3e8310b8bf8b3444c85aaf0d2aeb2d4f36fd14d5cb51fcebff418b3827136ab9529e9a3d3f35e4c0ae749ea2dbc94982a1281d3e6daab719aa4460889321a008bf10fa06ac0c61cc122cc90d5e22c0030c986ae84a33a0c47df174bcfbd50bf78ffdf24051ab423db63d5815db2f830040f30521131c98c66f16c362addce2fba0602cf0a7dddf22e8def7516cdfee95b4056cc9ad38c95352335421b5b1ffbadf75e5212fdad7a75f52a2801486a1eec3539580bee0e4b337cda6085ac9eccd1a0f1a46cebfbb5cdfa3251ac28c3bc826148c6d8c1eb6a06f77f6ff632c6a83e283e8f9df7c6dbabf1c6ea40629a85b43ab0c73d34f9d5072832a104eda3f75f5d83da6e14822a18e14099d749eafd823ca2ac7542086501eca206ce7887920008573757ce2f230a890782b99cc682377beee812756d04f9025135fb599d746fefe7316c922ac265ca0d29021375adb63c1509c3e242dfb92b8dee891f7368c4058399b8db9075f2dcc8216194e503b6652d87d2cb41f99adfdcc5be5ec7e1e6326ac22d70bd3ba652827532d669aff005173597f8039c3ea4922d3ec757670222f6ac29b93e90d7ad3f6dd96328e429cfcfd5cca22707fe2d86ad1dcb0be756e8e"));
        SessionContext context = new SessionContext(null, null);
        context.setClientWriteIv(Util.hexStringToByteArray("f7f6884c4981716c2d0d29a4"));
        context.setClientWriteKey(Util.hexStringToByteArray("c66cb1aec519df44c91e10995511ac8b"));
        context.setSelectedVersion(ProtocolVersion.TLS_1_3);
        inputStream = new ByteArrayInputStream(new byte[0]);
        recordLayer = new RecordLayer(outputStream, inputStream, context, 0);
        recordLayer.activateEncryption();
        recordLayer.resetSequencnumbers();
        recordLayer.decrypt(record);
        assertArrayEquals(Util.hexStringToByteArray("080000240022000a00140012001d00170018001901000101010201030104001c00024001000000000b0001b9000001b50001b0308201ac30820115a003020102020102300d06092a864886f70d01010b0500300e310c300a06035504031303727361301e170d3136303733303031323335395a170d3236303733303031323335395a300e310c300a0603550403130372736130819f300d06092a864886f70d010101050003818d0030818902818100b4bb498f8279303d980836399b36c6988c0c68de55e1bdb826d3901a2461eafd2de49a91d015abbc9a95137ace6c1af19eaa6af98c7ced43120998e187a80ee0ccb0524b1b018c3e0b63264d449a6d38e22a5fda430846748030530ef0461c8ca9d9efbfae8ea6d1d03e2bd193eff0ab9a8002c47428a6d35a8d88d79f7f1e3f0203010001a31a301830090603551d1304023000300b0603551d0f0404030205a0300d06092a864886f70d01010b05000381810085aad2a0e5b9276b908c65f73a7267170618a54c5f8a7b337d2df7a594365417f2eae8f8a58c8f8172f9319cf36b7fd6c55b80f21a03015156726096fd335e5e67f2dbf102702e608ccae6bec1fc63a42a99be5c3eb7107c3c54e9b9eb2bd5203b1c3b84e0a8b2f759409ba3eac9d91d402dcc0cc8f8961229ac9187b42b4de100000f00008408040080754040d0ddab8cf0e2da2bc4995b868ad745c8e1564e33cde17880a42392cc624aeef6b67bb3f0ae71d9d54a2309731d87dc59f642d733be2eb27484ad8a8c8eb3516a7ac57f2625e2b5c0888a8541f4e734f73d054761df1dd02f0e3e9a33cfa10b6e3eb4ebf7ac053b01fdabbddfc54133bcd24c8bbdceb223b2aa03452a2914000020ac86acbc9cd25a45b57ad5b64db15d4405cf8c80e314583ebf3283ef9a99310c"), record.getData());
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testReceiveData() throws Exception {
        SessionContext context = new SessionContext(null, null);
        inputStream = new ByteArrayInputStream(Util.hexStringToByteArray("33FFFF0001CC"));
        recordLayer = new RecordLayer(outputStream, inputStream, context, 0);
        List<Record> receivedRecords = recordLayer.receiveData();
        assertTrue(receivedRecords.size() == 1);
        Record testRecord = receivedRecords.get(0);
        assertTrue(testRecord.getType() == 0x33);
        assertArrayEquals(testRecord.getVersion(), Util.hexStringToByteArray("FFFF"));
        assertArrayEquals(testRecord.getData(), Util.hexStringToByteArray("CC"));
        inputStream = new ByteArrayInputStream(Util.hexStringToByteArray("33FFFF0001CC33FFFF0001CC33FFFF0001CC33FFFF0001CC33FFFF0001CC33FFFF0001CC33FFFF0001CC33FFFF0001CC"));
        recordLayer = new RecordLayer(outputStream, inputStream, context, 0);
        receivedRecords = recordLayer.receiveData();
        assertTrue(receivedRecords.size() == 8);
        
    }

    /**
     *
     */
    @Test
    public void testUpdateEncryptionCipher() {
    }

    /**
     *
     */
    @Test
    public void testUpdateDecryptionCipher() {
    }

    /**
     *
     */
    @Test
    public void testGetAdditionalWriteAuthenticatedData() {
    }

    /**
     *
     */
    @Test
    public void testGetEncryptionIV() {
    }

    /**
     *
     */
    @Test
    public void testGetDecryptionIV() {
    }
}
