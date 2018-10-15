package de.rub.nds.praktikum.protocol;

import de.rub.nds.praktikum.constants.AlertDescription;
import de.rub.nds.praktikum.constants.AlertLevel;
import de.rub.nds.praktikum.constants.ProtocolType;
import java.io.IOException;

/**
 * The alert layer is responsible for the exchange of error messages between the
 * client and the server.
 *
 */
public class AlertLayer extends TlsSubProtocol {

    private final SessionContext context;

    private final RecordLayer recordLayer;

    /**
     * Constructor
     *
     * @param context The SessionContext for which this alert layer should be
     * constructed
     * @param recordLayer The record layer that should be used by this alert
     * layer
     */
    public AlertLayer(SessionContext context, RecordLayer recordLayer) {
        super(ProtocolType.ALERT.getByteValue());
        this.context = context;
        this.recordLayer = recordLayer;

    }

    /**
     * Sends an alert message with the provided parameters
     *
     * @param alertLevel level of the alert
     * @param alertDescription description of the alert
     * @throws IOException If something goes wrong during transmission
     */
    public void sendAlert(AlertLevel alertLevel, AlertDescription alertDescription) throws IOException {
        throw new UnsupportedOperationException("Add code here");
    }

    /**
     * Parses the received alert messages. If a fatal alert is received a
     * TlsException is thrown
     *
     * @param stream
     */
    @Override
    public void processByteStream(byte[] stream) {
        throw new UnsupportedOperationException("Add code here");
    }
}
