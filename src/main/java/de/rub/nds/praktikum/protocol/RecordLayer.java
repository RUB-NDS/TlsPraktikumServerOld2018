package de.rub.nds.praktikum.protocol;

import de.rub.nds.praktikum.constants.ProtocolType;
import de.rub.nds.praktikum.records.Record;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import javax.crypto.Cipher;

/**
 * The record layer is the lowest layer of tls protocol stack and is responsible
 * for the encapusaltion of the data from higher level protocols. Once the
 * ServerHello message is sent records are encrypted under the current handshake
 * context
 *
 */
public class RecordLayer {

    /**
     * tag length in byte
     */
    private static final int GCM_TAG_LENGTH = 16;

    private static final int GCM_IV_LENGTH = 12;

    private final OutputStream outputStream;

    private final InputStream inputStream;

    private final long timeout;

    private Cipher encryptionCipher = null;

    private Cipher decryptionCipher = null;

    private long readSequencenumber = 0;

    private long writeSequencenumber = 0;

    private boolean encryptionIsActive = false;

    private final SessionContext context;

    /**
     * Constructor
     *
     * @param outputStream output stream to which to write when sending data
     * @param inputStream input stream from which to read from to receive data
     * @param context the session context for which to create the record layer
     * for
     * @param timeout read timeout
     */
    public RecordLayer(OutputStream outputStream, InputStream inputStream, SessionContext context, long timeout) {
        this.timeout = timeout;
        this.outputStream = outputStream;
        this.inputStream = inputStream;
        this.context = context;
    }

    /**
     * Sends the provided data in record of the appropriate type. The record is
     * encrypted under the current connection state
     *
     * @param data the plaintext data that should be sent
     * @param type the type of the protocol which wants to send this data
     * @throws IOException if something goes wrong during transmission
     */
    public void sendData(byte[] data, ProtocolType type) throws IOException {
        throw new UnsupportedOperationException("Add code here");
    }

    /**
     * Tries to receive records from the input stream. If not data is available
     * an empty record list is returned.
     *
     * @return @throws IOException if an error occured while reading from the
     * stream
     */
    public List<Record> receiveData() throws IOException {
        throw new UnsupportedOperationException("Add code here");
    }

    private byte[] fetchData() throws IOException {

        System.out.println("Fetch called");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        long minTimeMillies = System.currentTimeMillis() + timeout;
        do {
            //waiting while no data available
        } while (minTimeMillies + timeout > System.currentTimeMillis() && inputStream.available() == 0);
        while (inputStream.available() != 0) {
            int read = inputStream.read();
            stream.write(read);
        }
        return stream.toByteArray();
    }

    /**
     * Activates the encryption with the parameters from the session context.
     *
     */
    public void activateEncryption() {
        encryptionIsActive = true;
    }

    protected void encrypt(Record record) {
        throw new UnsupportedOperationException("Add code here");
    }

    protected void decrypt(Record record) {
        throw new UnsupportedOperationException("Add code here");
    }

    /**
     *
     */
    public void resetSequencnumbers() {
        throw new UnsupportedOperationException("Add code here");
    }
}
