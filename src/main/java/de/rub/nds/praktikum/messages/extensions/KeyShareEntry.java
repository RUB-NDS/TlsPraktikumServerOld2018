package de.rub.nds.praktikum.messages.extensions;

import de.rub.nds.praktikum.constants.NamedGroup;

/**
 * This class represents a key share entry inside of a key share extension
 * message. Each key share entry consists of a named group to which the key
 * share belongs as well as a byte[] representation of the public key
 *
 */
public class KeyShareEntry {

    private final byte[] groupBytes;
    private final byte[] keyShare;

    /**
     * Constructor
     *
     * @param groupBytes The named group of the public key
     * @param keyShare The public key
     */
    public KeyShareEntry(byte[] groupBytes, byte[] keyShare) {
        this.groupBytes = groupBytes;
        this.keyShare = keyShare;
    }

    /**
     * Returns the named group of the key share. If we cannot translate the byte
     * null is returned
     *
     * @return the named group of the key share
     */
    public NamedGroup getGroup() {
        return NamedGroup.convert(groupBytes);
    }

    /**
     * Returns the byte[] representation of the public key
     *
     * @return the byte[] representation of the public key
     */
    public byte[] getKeyShare() {
        return keyShare;
    }

    /**
     * Returns the raw group bytes of the keyshare entry
     *
     * @return the raw group bytes of the keyshare entry
     */
    public byte[] getGroupBytes() {
        return groupBytes;
    }

}
