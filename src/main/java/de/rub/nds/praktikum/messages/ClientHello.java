package de.rub.nds.praktikum.messages;

import de.rub.nds.praktikum.constants.HandshakeMessageType;
import de.rub.nds.praktikum.messages.extensions.Extension;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a TLS client hello message. The client hello message
 * contains all the cryptographic parameters proposed by the client as well as a
 * list of extensions.
 *
 */
public class ClientHello extends HandshakeMessage {

    private final byte[] version;
    private final byte[] random;
    private final byte[] sessionId;
    private final byte[] ciphersuites;
    private final byte[] compressionMethods;
    private final List<Extension> extensionList;

    /**
     * Constructor
     *
     * @param version the highest supported protocol version of the client
     * @param random 32 random bytes used as a nonce
     * @param sessionId a session id which can be used to resume sessions
     * @param ciphersuites a list of client supported ciphersuites
     * @param compressionMethods a list of client supported compression methods
     * @param extensionList a list of client supported extensions
     */
    public ClientHello(byte[] version, byte[] random, byte[] sessionId, byte[] ciphersuites, byte[] compressionMethods, List<Extension> extensionList) {
        super(HandshakeMessageType.CLIENT_HELLO.getValue());
        this.version = version;
        this.random = random;
        this.sessionId = sessionId;
        this.ciphersuites = ciphersuites;
        this.compressionMethods = compressionMethods;
        this.extensionList = extensionList;
    }

    /**
     * Returns the highest supported version by the client. Note that this does
     * not respect protocol version extension messages
     *
     * @return the highest supported version by the client
     */
    public byte[] getVersion() {
        return version;
    }

    /**
     * Returns the random bytes
     *
     * @return the random bytes
     */
    public byte[] getRandom() {
        return random;
    }

    /**
     * Returns the sessionId
     *
     * @return the sessionId
     */
    public byte[] getSessionId() {
        return sessionId;
    }

    /**
     * Returns the ciphersuites
     *
     * @return the ciphersuites
     */
    public byte[] getCiphersuites() {
        return ciphersuites;
    }

    /**
     * Returns the compression methods
     *
     * @return the compression methods
     */
    public byte[] getCompressionMethods() {
        return compressionMethods;
    }

    /**
     * Returns the extension list
     *
     * @return the extension list
     */
    public List<Extension> getExtensionList() {
        return Collections.unmodifiableList(extensionList);
    }
}
