package de.rub.nds.praktikum.messages;

/**
 * A serializer class which transfroms an alert object into its byte
 * representation
 *
 */
public class AlertSerializer extends Serializer<Alert> {

    private final Alert alert;

    /**
     * Constructor
     *
     * @param alert the alert that should be serialized
     */
    public AlertSerializer(Alert alert) {
        this.alert = alert;
    }

    @Override
    protected byte[] serializeBytes() {
        throw new UnsupportedOperationException("Add code here");
    }

}
