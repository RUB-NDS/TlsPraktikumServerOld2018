package de.rub.nds.praktikum.messages;

/**
 * A serializer class which transfroms a certificate verify message object into
 * its byte representation
 *
 */
public class CertificateVerifySerializer extends Serializer<CertificateVerify> {

    private final CertificateVerify certVerify;

    /**
     * Constructor
     *
     * @param certVerify The certificate verify message to serialize
     */
    public CertificateVerifySerializer(CertificateVerify certVerify) {
        this.certVerify = certVerify;
    }

    @Override
    protected byte[] serializeBytes() {
        throw new UnsupportedOperationException("Add code here");
    }

}
