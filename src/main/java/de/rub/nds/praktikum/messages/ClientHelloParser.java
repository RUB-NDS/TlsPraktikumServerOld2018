package de.rub.nds.praktikum.messages;

/**
 * A parser class which parses a provided byte[] into a client hello object
 *
 */
public class ClientHelloParser extends Parser<ClientHello> {

    private byte[] version;
    private byte[] random;
    private byte[] sessionId;
    private byte[] cipherSuites;
    private byte[] compressionMethods;
    private byte[] extensionBytes;

    public ClientHelloParser(byte[] array) {
        super(array);
    }

    @Override
    public ClientHello parse() {
        throw new UnsupportedOperationException("Add code here");
    }

}
