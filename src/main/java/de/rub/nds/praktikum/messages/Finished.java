package de.rub.nds.praktikum.messages;

import de.rub.nds.praktikum.constants.HandshakeMessageType;

/**
 * This class represents a TLS finished message. The finished message is sent by
 * the client and the server alike and contains an hmac over the previous
 * transcript which is computed with a special finished key.
 *
 */
public class Finished extends HandshakeMessage {

    private final byte[] verifyData;

    /**
     * Constructor
     *
     * @param verifyData The computed verifyData (hmac of the transcript)
     */
    public Finished(byte[] verifyData) {
        super(HandshakeMessageType.FINISHED.getValue());
        this.verifyData = verifyData;
    }

    /**
     * Returns the verify data
     *
     * @return the verify data
     */
    public byte[] getVerifyData() {
        return verifyData;
    }
}
