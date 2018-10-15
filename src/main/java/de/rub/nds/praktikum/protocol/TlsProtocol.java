package de.rub.nds.praktikum.protocol;

import de.rub.nds.praktikum.records.Record;
import java.io.IOException;
import java.net.Socket;
import java.security.PrivateKey;
import java.util.List;
import org.bouncycastle.crypto.tls.Certificate;

/**
 * The TLS protocol class is responsible for the orchestration of the tls
 * protocol, the handshake, the recordlayer etc.
 *
 */
public class TlsProtocol {

    private final long timeout;

    private final Socket socket;
    private final RecordLayer recordLayer;
    private final HandshakeLayer handshakeLayer;
    private final ApplicationLayer applicationLayer;
    private final AlertLayer alertLayer;
    private final List<TlsSubProtocol> layerList;
    private final SessionContext context;

    /**
     * Constructor
     *
     * @param socket the socket which should be used
     * @param cert the server certificate
     * @param key the private key for the server certificate
     * @throws IOException throws an io exception if something goes wrong with
     * the socket streams
     */
    public TlsProtocol(Socket socket, Certificate cert, PrivateKey key, long timeout) throws IOException {
        throw new UnsupportedOperationException("Add code here");
    }

    /**
     * Starts the handshake
     *
     * @throws IOException if something goes wrong with the socket streams
     */
    public void initSession() throws IOException {
        throw new UnsupportedOperationException("Add code here");
    }

    /**
     * Performs a step in the statemachine
     *
     * @throws IOException if something goes wrong with the socket streams
     */
    public void stepConnectionState() throws IOException {
        throw new UnsupportedOperationException("Add code here");
    }

    private void passDataToLayer(List<Record> recordList) {
        throw new UnsupportedOperationException("Add code here");
    }

    private void passSubGroupToLayer(List<Record> recordList) {
        throw new UnsupportedOperationException("Add code here");
    }

    /**
     * Sends the provided data[] as application data
     *
     * @param data the data to send
     * @throws IOException if something goes wrong with the streams
     */
    public void sendData(byte[] data) throws IOException {
        throw new UnsupportedOperationException("Add code here");
    }

    /**
     * Tries to read data from the stream
     *
     * @return the received data
     * @throws IOException if something goes wrong with the streams
     */
    public byte[] receiveData() throws IOException {
        throw new UnsupportedOperationException("Add code here");
    }

    /**
     * Returns the session context of the protcol
     *
     * @return the session context of the protocol
     */
    public SessionContext getContext() {
        return context;
    }
}
