package de.rub.nds.praktikum.messages.extensions;

import de.rub.nds.praktikum.constants.ExtensionType;
import de.rub.nds.praktikum.messages.Serializer;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a KeyShare extension. The keyshare extension is used in
 * tls 1.3 in exchange for client and server key exchange messages.
 *
 */
public class KeyShareExtension extends Extension {

    private final List<KeyShareEntry> entryList;

    /**
     * Constructor
     *
     * @param entryList A list of supported key shareenties.
     */
    public KeyShareExtension(List<KeyShareEntry> entryList) {
        super(ExtensionType.KEY_SHARE);
        this.entryList = entryList;
    }

    /**
     * Constructor
     *
     * @param entry A selected key share entry.
     */
    public KeyShareExtension(KeyShareEntry entry) {
        super(ExtensionType.KEY_SHARE);
        this.entryList = new LinkedList<>();
        entryList.add(entry);
    }

    /**
     * Returns a list of the key share entries
     *
     * @return An unmodifiable list of the key share entries
     */
    public List<KeyShareEntry> getEntryList() {
        return Collections.unmodifiableList(entryList);
    }

    /**
     * Return a key share extension serializer
     *
     * @return A key share extension serializer
     */
    @Override
    public Serializer getSerializer() {
        return new KeyShareExtensionSerializer(this);
    }
}
