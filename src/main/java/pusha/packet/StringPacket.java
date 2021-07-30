package pusha.packet;

import java.io.Serializable;

public class StringPacket implements Serializable, Packet  {
    String order;
    String tag;
    String message;

    public StringPacket(String order, String tag, String message) {
        this.tag = tag;
        this.order = order;
        this.message = message;
    }

    @Override
    public String getOrder() {
        return order;
    }

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public String getData() {
        return message;
    }
}
