package de.rub.nds.praktikum.messages;

import de.rub.nds.praktikum.constants.HandshakeMessageType;
import de.rub.nds.praktikum.messages.extensions.Extension;
import java.util.Collections;
import java.util.List;

/**
 * This class represents a TLS server hello message. The server hello message
 * contains the algorithmic selection of the server as well as a list of
 * extension messages
 *
 */
public class ServerHello extends HandshakeMessage {

    private final byte[] version;
    private final byte[] random;
    private final byte[] sessionId;
    private final byte[] ciphersuite;
    private final byte[] compression;
    private final List<Extension> extensionList;

    /**
     * Constructor
     *
     * @param version the selected protocol version
     * @param random the random bytes (32) of the server
     * @param sessionId the session id (reflected from the client)
     * @param ciphersuite the selected ciphersuite
     * @param compression the selected compression (always 0x00)
     * @param extensionList the extension list of the server
     */
    public ServerHello(byte[] version, byte[] random, byte[] sessionId, byte[] ciphersuite, byte[] compression, List<Extension> extensionList) {
        super(HandshakeMessageType.SERVER_HELLO.getValue());
        this.version = version;
        this.random = random;
        this.sessionId = sessionId;
        this.ciphersuite = ciphersuite;
        this.compression = compression;
        this.extensionList = extensionList;
    }

    /**
     * Returns the selected protocol version
     *
     * @return the selected protocol version
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
     * Returns the session id
     *
     * @return the session id
     */
    public byte[] getSessionId() {
        return sessionId;
    }

    /**
     * Returns the selected ciphersuite
     *
     * @return the selected ciphersuite
     */
    public byte[] getCiphersuite() {
        return ciphersuite;
    }

    /**
     * Returns the selected compression algorithm
     *
     * @return the selected compression algorithm
     */
    public byte[] getCompression() {
        return compression;
    }

    /**
     * Returns the extension list of the server
     *
     * @return the extension list
     */
    public List<Extension> getExtensionList() {
        return Collections.unmodifiableList(extensionList);
    }

}
