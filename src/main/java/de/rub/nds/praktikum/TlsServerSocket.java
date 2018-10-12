package de.rub.nds.praktikum;

import de.rub.nds.praktikum.protocol.TlsProtocol;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.PrivateKey;
import java.security.Security;
import org.bouncycastle.crypto.tls.Certificate;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * A ServerSocket which uses a TLS 1.3 to transport the data
 *
 */
public class TlsServerSocket extends ServerSocket {

    private TlsProtocol protocol;

    private final Certificate cert;
    private final PrivateKey key;

    /**
     * Constructor
     *
     * @param port The port on which to listen for connection
     * @param cert The server certificate for the TLS connection
     * @param privateKey The private key for the server certificate
     * @throws IOException An IoException if something goes wrong with the
     * socket
     */
    public TlsServerSocket(int port, Certificate cert, PrivateKey privateKey) throws IOException {
        super(port);
        Security.addProvider(new BouncyCastleProvider());
        this.cert = cert;
        this.key = privateKey;
    }

    @Override
    public Socket accept() throws IOException {
        Socket s = super.accept();
        protocol = new TlsProtocol(s, cert, key);
        long starttime = System.currentTimeMillis();
        protocol.initSession();
        long stoptime = System.currentTimeMillis();
        System.out.println("Performed Handshake in: " + (stoptime - starttime) + " ms");
        return new TlsSocket(s, protocol);
    }
}
