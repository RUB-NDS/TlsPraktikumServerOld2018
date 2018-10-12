package de.rub.nds.praktikum.messages;

import de.rub.nds.praktikum.constants.HandshakeMessageType;

/**
 * This class represents a TLS certificate verify message. This message is sent
 * by the server to prove the possesion of the private key. It contains a
 * signtatureAndHash algorithm byte[] as an identifier for the used algorithms
 * as well as a signture.
 *
 */
public class CertificateVerify extends HandshakeMessage {

    private final byte[] signatureAndHashAlgorithm;

    private final byte[] signature;

    /**
     * Constructor
     *
     * @param signatureAndHashAlgorithm The used signature and hash algorithm
     * identifier
     * @param signature the (with the provided sig/hash algo) computed signature
     * algorithm
     */
    public CertificateVerify(byte[] signatureAndHashAlgorithm, byte[] signature) {
        super(HandshakeMessageType.CERTIFICATE_VERIFY.getValue());
        this.signatureAndHashAlgorithm = signatureAndHashAlgorithm;
        this.signature = signature;
    }

    /**
     * Returns the byte[] value of the signature and hash algorithm
     *
     * @return the byte[] value of the signature and hash algorithm
     */
    public byte[] getSignatureAndHashAlgorithm() {
        return signatureAndHashAlgorithm;
    }

    /**
     * Returns the byte[] value of the signature
     *
     * @return the byte[] value of the signature
     */
    public byte[] getSignature() {
        return signature;
    }
}
