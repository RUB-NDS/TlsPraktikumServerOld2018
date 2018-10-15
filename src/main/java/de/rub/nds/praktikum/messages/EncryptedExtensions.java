package de.rub.nds.praktikum.messages;

import de.rub.nds.praktikum.constants.HandshakeMessageType;

/**
 * This class represents a TLS encrypted extensions message. This message is
 * sent by the server to send extensions encrypted. We do not really need this
 * extension for this course, therefore this message only contains an emtpy
 * extension list
 *
 */
public class EncryptedExtensions extends HandshakeMessage {

    /**
     * Constructor
     */
    public EncryptedExtensions() {
        super(HandshakeMessageType.ENCRYPTED_EXTENSIONS.getValue());
    }

}
