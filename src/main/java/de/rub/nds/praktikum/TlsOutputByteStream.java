package de.rub.nds.praktikum;

import de.rub.nds.praktikum.protocol.TlsProtocol;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * An Outputstream which uses a TlsProtocol to encrypt outgoing traffic
 *
 */
public class TlsOutputByteStream extends OutputStream {

    private final TlsProtocol protocol;
    private final ByteArrayOutputStream stream;

    /**
     * Constructor
     *
     * @param protocol The tlsProtocol which should be used for the
     * decapsulation
     */
    public TlsOutputByteStream(TlsProtocol protocol) {
        this.protocol = protocol;
        this.stream = new ByteArrayOutputStream();
    }

    @Override
    public void write(int b) throws IOException {
        stream.write(b);
    }

    @Override
    public void flush() throws IOException {
        protocol.sendData(stream.toByteArray());
    }
}
