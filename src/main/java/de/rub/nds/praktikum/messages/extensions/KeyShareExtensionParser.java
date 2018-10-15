package de.rub.nds.praktikum.messages.extensions;

import de.rub.nds.praktikum.messages.Parser;

/**
 * A parser class which parses a provided byte[] into a key share extension
 * object
 *
 */
public class KeyShareExtensionParser extends Parser<KeyShareExtension> {

    /**
     * Constructor
     *
     * @param array the byte[] that should be parsed
     */
    public KeyShareExtensionParser(byte[] array) {
        super(array);
    }

    @Override
    public KeyShareExtension parse() {
        throw new UnsupportedOperationException("Add code here");
    }
}
