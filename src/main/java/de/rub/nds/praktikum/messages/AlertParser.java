package de.rub.nds.praktikum.messages;

/**
 * A parser class for alert messages
 *
 */
public class AlertParser extends Parser<Alert> {

    /**
     * Constructor for the parser
     *
     * @param array The array that should be parsed
     */
    public AlertParser(byte[] array) {
        super(array);
    }

    @Override
    public Alert parse() {
        throw new UnsupportedOperationException("Add code here");
    }

}
