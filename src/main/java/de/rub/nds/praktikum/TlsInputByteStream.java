package de.rub.nds.praktikum;

import de.rub.nds.praktikum.protocol.TlsProtocol;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A Tls InputStream which uses a TlsProtocol to decrypt the incoming traffic
 *
 */
public class TlsInputByteStream extends InputStream {

    private final TlsProtocol protocol;
    private ByteArrayInputStream stream;
    private long lastReceived;

    /**
     * Constructor
     *
     * @param protocol The tlsProtocol which should be used for the
     * decapsulation
     */
    public TlsInputByteStream(TlsProtocol protocol) {
        this.protocol = protocol;
        this.stream = new ByteArrayInputStream(new byte[]{});
        lastReceived = System.currentTimeMillis();
    }

    @Override
    public int read() throws IOException {

        while (stream.available() == 0) {
            byte[] data = protocol.receiveData();
            stream = new ByteArrayInputStream(data);
            if (data.length > 0) {
                lastReceived = System.currentTimeMillis();
            }
        }
        if (System.currentTimeMillis() > lastReceived + 60000) {
            throw new IOException("Connection Timeout");
        }
        return stream.read();
    }

    @Override
    public int available() throws IOException {
        return stream.available();
    }

}
