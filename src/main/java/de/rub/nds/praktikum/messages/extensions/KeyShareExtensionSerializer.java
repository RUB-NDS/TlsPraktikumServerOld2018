package de.rub.nds.praktikum.messages.extensions;

import de.rub.nds.praktikum.messages.Serializer;

/**
 * A serializer class which transfroms a KeyShareExtension into its byte
 * representation
 *
 */
public class KeyShareExtensionSerializer extends Serializer<KeyShareExtension> {

    private final KeyShareExtension extension;

    /**
     * Constructor
     *
     * @param extension the extension that should be serialized
     */
    public KeyShareExtensionSerializer(KeyShareExtension extension) {
        this.extension = extension;
    }

    @Override
    protected byte[] serializeBytes() {
        throw new UnsupportedOperationException("Add code here");
    }

}
