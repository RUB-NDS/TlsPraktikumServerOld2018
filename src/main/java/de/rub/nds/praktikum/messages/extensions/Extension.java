package de.rub.nds.praktikum.messages.extensions;

import de.rub.nds.praktikum.constants.ExtensionType;
import de.rub.nds.praktikum.messages.Serializer;

/**
 * Extension messages follow a certain pattern in tls. They all first contain a
 * type followed by a length field of the size of the remaining bytes
 *
 */
public abstract class Extension {

    private final ExtensionType type;

    /**
     * Constructor
     *
     * @param type The type of the extension
     */
    public Extension(ExtensionType type) {
        this.type = type;
    }

    /**
     * Returns the type of the extension
     *
     * @return the type of the extension
     */
    public ExtensionType getType() {
        return type;
    }

    /**
     * Returns a serializer which can be used to transform this Extension into
     * its byte representation
     *
     * @return A serializer which can be used to transform this Extension into
     * its byte representation
     */
    public abstract Serializer getSerializer();
}
