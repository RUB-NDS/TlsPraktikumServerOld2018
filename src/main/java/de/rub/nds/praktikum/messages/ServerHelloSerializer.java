package de.rub.nds.praktikum.messages;

/**
 * A serializer class which transfroms a server hello message object into its
 * byte representation
 *
 */
public class ServerHelloSerializer extends Serializer<ServerHello> {

    private final ServerHello hello;

    /**
     * Constructor
     *
     * @param hello The message to serialize
     */
    public ServerHelloSerializer(ServerHello hello) {
        this.hello = hello;
    }

    @Override
    protected byte[] serializeBytes() {
        throw new UnsupportedOperationException("Add code here");
    }

}
