package de.rub.nds.praktikum.crypto;

import de.rub.nds.praktikum.protocol.SessionContext;
import de.rub.nds.praktikum.util.Util;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 *
 */
@Category(de.rub.nds.praktikum.Aufgabe3.class)
public class KeyGeneratorTest {

    @Test
    public void testAdjustHandskakeKeysInSession() {
        SessionContext context = new SessionContext(null, null);
        context.updateDigest(Util.hexStringToByteArray("010000c00303d4b9503c5e95c9eecc99ce6376ccad4dcc06d7c8f1fa44b0d95600e9a0586c67000006130113031302010000910000000b0009000006736572766572ff01000100000a00140012001d0017001800190100010101020103010400230000003300260024001d0020b0f5019fb0f1e5376b8b1dfb905f1d915161bac37707dad8907bd71b9807b345002b0003020304000d0020001e040305030603020308040805080604010501060102010402050206020202002d00020101001c00024001")); //A ClientHello
        context.updateDigest(Util.hexStringToByteArray("020000560303eefce7f7b37ba1d1632e96677825ddf73988cfc79825df566dc5430b9a045a1200130100002e00330024001d00209d3c940d89690b84d08a60993c144eca684d1081287c834d5311bcf32bb9da1a002b00020304"));//A ServerHello
        context.setSharedEcdheSecret(Util.hexStringToByteArray("8151d1464c1b55533623b9c2246a6a0e6e7e185063e14afdaff0b6e1c61a8642")); //The computed shared secret
        KeyGenerator.adjustHandshakeSecrets(context);
        assertArrayEquals("The digest is computed wrongly", Util.hexStringToByteArray("c6c918ad2f4199d5598eaf0116cb7a5c2c14cb54781218888db7030dd50d5e6d"), context.getDigest());
        assertArrayEquals("The HandshakeSecret is computed wrongly", Util.hexStringToByteArray("5b4f965df03c682c46e6ee86c311636615a1d2bbb24345c25205953c879e8d06"), context.getHandshakeSecret());
        assertArrayEquals("The ClientHandshakeTrafficSecret is computed wrongly", Util.hexStringToByteArray("e2e23207bd93fb7fe4fc2e297afeab160e522b5ab75d64a86e75bcac3f3e5103"), context.getClientHandshakeTrafficSecret());
        assertArrayEquals("The ServerHandshakeTrafficSecret is computed wrongly", Util.hexStringToByteArray("3b7a839c239ef2bf0b7305a0e0c4e5a8c6c69330a753b308f5e3a83aa2ef6979"), context.getServerHandshakeTrafficSecret());
        context.updateDigest(Util.hexStringToByteArray("020000560303eefce7f7b37ba1d1632e96677825ddf73988cfc79825df566dc5430b9a045a1200130100002e00330024001d00209d3c940d89690b84d08a60993c144eca684d1081287c834d5311bcf32bb9da1a002b00020304"));
        KeyGenerator.adjustHandshakeKeys(context);
        assertArrayEquals("The ServerWriteKey is computed wrongly", Util.hexStringToByteArray("c66cb1aec519df44c91e10995511ac8b"), context.getServerWriteKey());
        assertArrayEquals("The ServerWriteIV is computed wrongly", Util.hexStringToByteArray("f7f6884c4981716c2d0d29a4"), context.getServerWriteIv());
        assertArrayEquals("The ClientWriteKey is computed wrongly", Util.hexStringToByteArray("2679a43e1d76784034ea1797d5ad2649"), context.getClientWriteKey());
        assertArrayEquals("The ServerWriteIv is computed wrongly", Util.hexStringToByteArray("5482405290dd0d2f81c0d942"), context.getClientWriteIv());
    }

    @Test
    public void testAdjustApplicationKeysInSession() {
        SessionContext context = new SessionContext(null, null);
        context.updateDigest(Util.hexStringToByteArray("12345678"));
        context.setHandshakeSecret(Util.hexStringToByteArray("00112233445566778899AABBCCDDEEFFFFEEDDCCBBAA99887766554433221100"));
        KeyGenerator.adjustApplicationSecrets(context);
        assertArrayEquals("The digest is computed wrongly", Util.hexStringToByteArray("b2ed992186a5cb19f6668aade821f502c1d00970dfd0e35128d51bac4649916c"), context.getDigest());
        assertArrayEquals("The MasterSecret is computed wrongly", Util.hexStringToByteArray("ce6fc3946457414160afc7f1ff8138c72c04be86d8afd96439643edd208ed0fa"), context.getMasterSecret());
        assertArrayEquals("The ClientApplicationTrafficSecret is computed wrongly", Util.hexStringToByteArray("7fa5865bda5bb6df40c17196774f84479790017f03de96708b7e3baf65116aef"), context.getClientApplicationTrafficSecret());
        assertArrayEquals("The ServerApplicationTrafficSecret is computed wrongly", Util.hexStringToByteArray("bb6e5bb47d531e88d9be3f681780e16dd7f05b86cebd58d93e5f72fb5948a018"), context.getServerApplicationTrafficSecret());
        KeyGenerator.adjustApplicationKeys(context);
        assertArrayEquals("The ServerWriteKey is computed wrongly", Util.hexStringToByteArray("65e5988c180a36e19da0d8cbe8923524"), context.getServerWriteKey());
        assertArrayEquals("The ServerWriteIV is computed wrongly", Util.hexStringToByteArray("e4bcb0bd7b415d82e7cd0ced"), context.getServerWriteIv());
        assertArrayEquals("The ClientWriteKey is computed wrongly", Util.hexStringToByteArray("9a8ce13d02b655b78d5852d05ae0f692"), context.getClientWriteKey());
        assertArrayEquals("The ServerWriteIv is computed wrongly", Util.hexStringToByteArray("3d749f23f56719d3f32e27ee"), context.getClientWriteIv());
    }
}
