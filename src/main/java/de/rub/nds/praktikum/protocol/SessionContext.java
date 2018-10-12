package de.rub.nds.praktikum.protocol;

import de.rub.nds.praktikum.constants.CipherSuite;
import de.rub.nds.praktikum.constants.NamedGroup;
import de.rub.nds.praktikum.constants.ProtocolVersion;
import de.rub.nds.praktikum.constants.TlsState;
import de.rub.nds.praktikum.exception.TlsException;
import de.rub.nds.praktikum.messages.extensions.KeyShareEntry;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.bouncycastle.crypto.tls.Certificate;

/**
 * This class stores all the necessary information for a tls session like
 * cryptographic values, negotiated keys or other session related information.
 * Each session has its own session context
 *
 */
public class SessionContext {

    private byte[] sharedEcdheSecret = null;

    private byte[] clientRandom = null;

    private byte[] serverRandom = null;

    private CipherSuite suite = null;

    private byte[] clientCompressions = null;

    private byte[] clientSessionId = null;

    private TlsState tlsState = null;

    private List<KeyShareEntry> keyShareEntryList = null;

    private List<ProtocolVersion> clientSupportedVersions = null;

    private List<NamedGroup> clientNamedGroupList = null;

    private List<CipherSuite> clientCipherSuiteList = null;

    private List<CipherSuite> serverSupportedCipherSuites = null;

    private byte[] ephemeralPrivateKey;

    private byte[] ephemeralPublicKey;

    private byte[] clientWriteKey;

    private byte[] serverWriteKey;

    private byte[] clientWriteIv;

    private byte[] serverWriteIv;

    private ProtocolVersion selectedVersion;

    private MessageDigest transcriptDigest;

    private byte[] handshakeSecret;

    private byte[] clientHandshakeTrafficSecret;

    private byte[] serverHandshakeTrafficSecret;

    private byte[] clientApplicationTrafficSecret;

    private byte[] serverApplicationTrafficSecret;

    private byte[] masterSecret;

    private byte[] clientFinishedKey;

    private byte[] serverFinishedKey;

    private Certificate certificate;

    private PrivateKey key;

    /**
     * Consturctor
     *
     * @param cert the server certificate
     * @param key the private key for the server certificate
     */
    public SessionContext(Certificate cert, PrivateKey key) {
        this.certificate = cert;
        this.key = key;
        try {
            tlsState = TlsState.START;
            serverSupportedCipherSuites = new LinkedList<>();
            serverSupportedCipherSuites.add(CipherSuite.TLS_AES_128_GCM_SHA256);
            transcriptDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ex) {
            throw new TlsException("Could not initialize session context",ex);
        }
    }

    /**
     * Returns the selected protocol version
     *
     * @return the selected protocol version
     */
    public ProtocolVersion getSelectedVersion() {
        return selectedVersion;
    }

    /**
     * Sets the selected protocol version
     *
     * @param selectedVersion the protocolversion
     */
    public void setSelectedVersion(ProtocolVersion selectedVersion) {
        this.selectedVersion = selectedVersion;
    }

    /**
     * Updates the transcript digest with the provided date[]
     *
     * @param data the data with which to update the transcript
     */
    public void updateDigest(byte[] data) {
        throw new UnsupportedOperationException("Add code here");
    }

    /**
     * Returns the current transcript hash
     *
     * @return the current transcript hash
     */
    public byte[] getDigest() {
        throw new UnsupportedOperationException("Add code here");
    }

    /**
     * Returns the client write key
     *
     * @return the client write key
     */
    public byte[] getClientWriteKey() {
        return clientWriteKey;
    }

    /**
     * Sets the client write key
     *
     * @param clientWriteKey the client write key
     */
    public void setClientWriteKey(byte[] clientWriteKey) {
        this.clientWriteKey = clientWriteKey;
    }

    /**
     * Returns the server write key
     *
     * @return the server write key
     */
    public byte[] getServerWriteKey() {
        return serverWriteKey;
    }

    /**
     * Sets the server write key
     *
     * @param serverWriteKey the server write key
     */
    public void setServerWriteKey(byte[] serverWriteKey) {
        this.serverWriteKey = serverWriteKey;
    }

    /**
     * Returns the client write Iv
     *
     * @return the client write Iv
     */
    public byte[] getClientWriteIv() {
        return clientWriteIv;
    }

    /**
     * Sets the client write Iv
     *
     * @param clientWriteIv the client write Iv
     */
    public void setClientWriteIv(byte[] clientWriteIv) {
        this.clientWriteIv = clientWriteIv;
    }

    /**
     * Returns the Server write Iv
     *
     * @return the Server write Iv
     */
    public byte[] getServerWriteIv() {
        return serverWriteIv;
    }

    /**
     *
     * @param serverWriteIv
     */
    public void setServerWriteIv(byte[] serverWriteIv) {
        this.serverWriteIv = serverWriteIv;
    }

    /**
     * Returns the ephemeral private key
     *
     * @return the ephemeral private key
     */
    public byte[] getEphemeralPrivateKey() {
        return ephemeralPrivateKey;
    }

    /**
     * Sets the ephemeral private key
     *
     * @param ephemeralPrivateKey the ephemeral private key
     */
    public void setEphemeralPrivateKey(byte[] ephemeralPrivateKey) {
        this.ephemeralPrivateKey = ephemeralPrivateKey;
    }

    /**
     * Returns the ephemeral public key
     *
     * @return the ephemeral public key
     */
    public byte[] getEphemeralPublicKey() {
        return ephemeralPublicKey;
    }

    /**
     * Sets the ephemeral public Key
     *
     * @param ephemeralPublicKey the ephemeral public key
     */
    public void setEphemeralPublicKey(byte[] ephemeralPublicKey) {
        this.ephemeralPublicKey = ephemeralPublicKey;
    }

    /**
     * Returngs the client named group list
     *
     * @return the client named group list
     */
    public List<NamedGroup> getClientNamedGroupList() {
        return Collections.unmodifiableList(clientNamedGroupList);
    }

    /**
     * Sets the client named group list
     *
     * @param clientNamedGroupList the client named group list
     */
    public void setClientNamedGroupList(List<NamedGroup> clientNamedGroupList) {
        this.clientNamedGroupList = clientNamedGroupList;
    }

    /**
     * Returns the client random
     *
     * @return the client random
     */
    public byte[] getClientRandom() {
        return clientRandom;
    }

    /**
     * Sets the client random
     *
     * @param clientRandom the client random
     */
    public void setClientRandom(byte[] clientRandom) {
        this.clientRandom = clientRandom;
    }

    /**
     * Returns the server random
     *
     * @return the server random
     */
    public byte[] getServerRandom() {
        return serverRandom;
    }

    /**
     * Sets the server random
     *
     * @param serverRandom the server random
     */
    public void setServerRandom(byte[] serverRandom) {
        this.serverRandom = serverRandom;
    }

    /**
     * Returns the selected ciphersuite
     *
     * @return the selected ciphersuite
     */
    public CipherSuite getSuite() {
        return suite;
    }

    /**
     * Sets the ciphersuite
     *
     * @param suite the ciphersuite
     */
    public void setSuite(CipherSuite suite) {
        this.suite = suite;
    }

    /**
     * Returns the current TlsState of the connection
     *
     * @return the current TlsState
     */
    public TlsState getTlsState() {
        return tlsState;
    }

    /**
     * Sets the current TlsState of the connection
     *
     * @param tlsState the current TlsState
     */
    public void setTlsState(TlsState tlsState) {
        this.tlsState = tlsState;
    }

    /**
     * Returns the list of client supported ciphersuites
     *
     * @return the list of client supported ciphersuites
     */
    public List<CipherSuite> getClientCipherSuiteList() {
        return Collections.unmodifiableList(clientCipherSuiteList);
    }

    /**
     * Sets the list of client supported ciphersuites
     *
     * @param clientCipherSuiteList the list of client supported ciphersuites
     */
    public void setClientCipherSuiteList(List<CipherSuite> clientCipherSuiteList) {
        this.clientCipherSuiteList = clientCipherSuiteList;
    }

    /**
     * Sets the client supported compressions
     *
     * @return the client supported compressions
     */
    public byte[] getClientCompressions() {
        return clientCompressions;
    }

    /**
     * Sets the client supported compressions
     *
     * @param clientCompressions the client supported compressions
     */
    public void setClientCompressions(byte[] clientCompressions) {
        this.clientCompressions = clientCompressions;
    }

    /**
     * Returns the session id from the client
     *
     * @return the session id from the client
     */
    public byte[] getClientSessionId() {
        return clientSessionId;
    }

    /**
     * Sets the session id from the client
     *
     * @param clientSessionId the session id from the client
     */
    public void setClientSessionId(byte[] clientSessionId) {
        this.clientSessionId = clientSessionId;
    }

    /**
     * Returns the key share entry list of the client
     *
     * @return the key share entry list of the client
     */
    public List<KeyShareEntry> getKeyShareEntryList() {
        return Collections.unmodifiableList(keyShareEntryList);
    }

    /**
     * Sets the key share entry list of the client
     *
     * @param keyShareEntryList the key share entry list of the client
     */
    public void setKeyShareEntryList(List<KeyShareEntry> keyShareEntryList) {
        this.keyShareEntryList = keyShareEntryList;
    }

    /**
     * Returns the client supported versions list from the protocol version
     * extension
     *
     * @return the client supported versions list from the protocol version
     * extension
     */
    public List<ProtocolVersion> getClientSupportedVersions() {
        return Collections.unmodifiableList(clientSupportedVersions);
    }

    /**
     * Sets the client supported versions list from the protocol version
     * extension
     *
     * @param clientSupportedVersions the client supported versions list from
     * the protocol version extension
     */
    public void setClientSupportedVersions(List<ProtocolVersion> clientSupportedVersions) {
        this.clientSupportedVersions = clientSupportedVersions;
    }

    /**
     * Returns the server supported ciphersuites
     *
     * @return the server supported ciphersuites
     */
    public List<CipherSuite> getServerSupportedCipherSuites() {
        return Collections.unmodifiableList(serverSupportedCipherSuites);
    }

    /**
     * Sets the server supported ciphersuites
     *
     * @param serverSupportedCipherSuites the server supported ciphersuites
     */
    public void setServerSupportedCipherSuites(List<CipherSuite> serverSupportedCipherSuites) {
        this.serverSupportedCipherSuites = serverSupportedCipherSuites;
    }

    /**
     * Returns the shared ecdhe secret
     *
     * @return the shared ecdhe secret
     */
    public byte[] getSharedEcdheSecret() {
        return sharedEcdheSecret;
    }

    /**
     * Sets the shared ecdhe secret
     *
     * @param sharedEcdheSecret the shared ecdhe secret
     */
    public void setSharedEcdheSecret(byte[] sharedEcdheSecret) {
        this.sharedEcdheSecret = sharedEcdheSecret;
    }

    /**
     * Returns the handshake secret
     *
     * @return the handshake secret
     */
    public byte[] getHandshakeSecret() {
        return handshakeSecret;
    }

    /**
     * Sets the handshake secret
     *
     * @param handshakeSecret the handshake secret
     */
    public void setHandshakeSecret(byte[] handshakeSecret) {
        this.handshakeSecret = handshakeSecret;
    }

    /**
     * Returns the client handshake traffic secret
     *
     * @return the client handshake traffic secret
     */
    public byte[] getClientHandshakeTrafficSecret() {
        return clientHandshakeTrafficSecret;
    }

    /**
     * Sets the client handshake traffic secret
     *
     * @param clientHandshakeTrafficSecret the client handshake traffic secret
     */
    public void setClientHandshakeTrafficSecret(byte[] clientHandshakeTrafficSecret) {
        this.clientHandshakeTrafficSecret = clientHandshakeTrafficSecret;
    }

    /**
     * Returns the server handshake traffic secret
     *
     * @return the server handshake traffic secret
     */
    public byte[] getServerHandshakeTrafficSecret() {
        return serverHandshakeTrafficSecret;
    }

    /**
     * Sets the server handshake traffic secret
     *
     * @param serverHandshakeTrafficSecret the server handshake traffic secret
     */
    public void setServerHandshakeTrafficSecret(byte[] serverHandshakeTrafficSecret) {
        this.serverHandshakeTrafficSecret = serverHandshakeTrafficSecret;
    }

    /**
     * Returns the server certificate
     *
     * @return the server certificate
     */
    public Certificate getCertificate() {
        return certificate;
    }

    /**
     * Sets the server certificate
     *
     * @param certificate the server certificate
     */
    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }

    /**
     * Returns the private key for the certificate
     *
     * @return the private key for the certificate
     */
    public PrivateKey getKey() {
        return key;
    }

    /**
     * Sets the private key for the certificate
     *
     * @param key the private key for the certificate
     */
    public void setKey(PrivateKey key) {
        this.key = key;
    }

    /**
     * TODO
     *
     * @return
     */
    public byte[] getClientFinishedKey() {
        return clientFinishedKey;
    }

    /**
     * TODO
     *
     * @param clientFinishedKey
     */
    public void setClientFinishedKey(byte[] clientFinishedKey) {
        this.clientFinishedKey = clientFinishedKey;
    }

    /**
     * TODO
     *
     * @return
     */
    public byte[] getServerFinishedKey() {
        return serverFinishedKey;
    }

    /**
     * TODO
     *
     * @param serverFinishedKey
     */
    public void setServerFinishedKey(byte[] serverFinishedKey) {
        this.serverFinishedKey = serverFinishedKey;
    }

    /**
     * Returns the client application traffic secret
     *
     * @return the client application traffic secret
     */
    public byte[] getClientApplicationTrafficSecret() {
        return clientApplicationTrafficSecret;
    }

    /**
     * Sets the client application traffic secret
     *
     * @param clientApplicationTrafficSecret the client application traffic
     * secret
     */
    public void setClientApplicationTrafficSecret(byte[] clientApplicationTrafficSecret) {
        this.clientApplicationTrafficSecret = clientApplicationTrafficSecret;
    }

    /**
     * Returns the server application traffic secret
     *
     * @return the server application traffic secret
     */
    public byte[] getServerApplicationTrafficSecret() {
        return serverApplicationTrafficSecret;
    }

    /**
     * Sets the server application traffic secret
     *
     * @param serverApplicationTrafficSecret the server application traffic
     * secret
     */
    public void setServerApplicationTrafficSecret(byte[] serverApplicationTrafficSecret) {
        this.serverApplicationTrafficSecret = serverApplicationTrafficSecret;
    }

    /**
     * Returns the master secret
     *
     * @return the master secret
     */
    public byte[] getMasterSecret() {
        return masterSecret;
    }

    /**
     * Sets the master secret
     *
     * @param masterSecret the master secret
     */
    public void setMasterSecret(byte[] masterSecret) {
        this.masterSecret = masterSecret;
    }
}
