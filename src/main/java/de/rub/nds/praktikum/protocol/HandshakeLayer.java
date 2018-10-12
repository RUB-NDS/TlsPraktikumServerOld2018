package de.rub.nds.praktikum.protocol;

import de.rub.nds.praktikum.constants.CipherSuite;
import de.rub.nds.praktikum.constants.ProtocolType;

/**
 * The handshake layer is responsible for the exchange of handshake messages
 * which are ultimately used to create the connection
 *
 */
public class HandshakeLayer extends TlsSubProtocol {

    private final SessionContext context;

    private final RecordLayer recordLayer;

    /**
     * Constructor
     *
     * @param context The SessionContext for which this handshake layer should
     * be constructed
     * @param recordLayer The record layer that should be used by this handshake
     * layer
     */
    public HandshakeLayer(SessionContext context, RecordLayer recordLayer) {
        super(ProtocolType.HANDSHAKE.getByteValue());
        this.context = context;
        this.recordLayer = recordLayer;
    }

    /**
     * Sends a ServerHello message
     */
    public void sendServerHello() {
        throw new UnsupportedOperationException("Add code here");
    }

    /**
     * Sends an encrypted extensions message
     */
    public void sendEncryptedExtensions() {
        throw new UnsupportedOperationException("Add code here");
    }

    /**
     * Sends a certificate message
     */
    public void sendCertificates() {
        throw new UnsupportedOperationException("Add code here");
    }

    /**
     * sends a certificate verify message
     */
    public void sendCertificateVerify() {
        throw new UnsupportedOperationException("Add code here");
    }

    private byte[] generateCertVerifySignature() {
        throw new UnsupportedOperationException("Add code here");
    }

    private byte[] generateToBeSigned() {
        throw new UnsupportedOperationException("Add code here");
    }

    /**
     * Sends a finished message
     */
    public void sendFinished() {
        throw new UnsupportedOperationException("Add code here");
    }

    @Override
    public void processByteStream(byte[] stream) {
        throw new UnsupportedOperationException("Add code here");
    }

    private CipherSuite selectCiphersuite() {
        throw new UnsupportedOperationException("Add code here");
    }

    private byte[] getKeyShare() {
        throw new UnsupportedOperationException("Add code here");
    }

    private byte[] computeSharedSecret() {
        throw new UnsupportedOperationException("Add code here");
    }

    private byte[] generateVerifyData() {
        throw new UnsupportedOperationException("Add code here");
    }
}
