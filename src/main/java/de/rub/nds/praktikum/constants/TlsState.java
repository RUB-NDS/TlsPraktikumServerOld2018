package de.rub.nds.praktikum.constants;

public enum TlsState {

    /**
     * Starting the Handshake, waiting for a ClientHello message
     */
    START,
    /**
     * we already sent a finished message, waiting for client finished
     */
    WAIT_FINISHED,
    /**
     * finished messages have been exchanged, app data can now be exchanged
     */
    CONNECTED,
    /**
     * a valid parameter choice is possible, we select parameters and send them
     */
    NEGOTIATED,
    /**
     * We received a ch, we need to check if a valid parameter choice is
     * possible
     */
    RECVD_CH,
    /**
     * we entered an error state, we send an alert and close the connection
     */
    ERROR,
}
