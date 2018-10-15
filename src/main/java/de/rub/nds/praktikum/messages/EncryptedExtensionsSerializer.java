package de.rub.nds.praktikum.messages;

/**
 * A serializer class which transfroms a encrypted extension message object into
 * its byte representation
 *
 */
public class EncryptedExtensionsSerializer extends Serializer<EncryptedExtensions> {

    private final EncryptedExtensions encryptedExtensions;

    /**
     * Constructor
     *
     * @param encryptedExtensions the encrypted extensions message that should
     * be serialized
     */
    public EncryptedExtensionsSerializer(EncryptedExtensions encryptedExtensions) {
        this.encryptedExtensions = encryptedExtensions;
    }

    @Override
    protected byte[] serializeBytes() {
        throw new UnsupportedOperationException("Add code here");
    }

}
