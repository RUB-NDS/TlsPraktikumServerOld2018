package de.rub.nds.praktikum.messages;

/**
 * A serializer class which transfroms a certificate message object into its
 * byte representation
 *
 */
public class CertificateMessageSerializer extends Serializer<CertificateMessage> {

    private final CertificateMessage message;

    /**
     *
     * @param message
     */
    public CertificateMessageSerializer(CertificateMessage message) {
        this.message = message;
    }

    @Override
    protected byte[] serializeBytes() {
        throw new UnsupportedOperationException("Add code here");
    }

}
