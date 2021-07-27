package pusha.packet;

import java.io.Serializable;

public class DataPacket implements Serializable, Packet  {
    String Tag;
    String Order;
    String Message;

    public DataPacket(String tag, String order, String message) {
        Tag = tag;
        Order = order;
        Message = message;
    }

    @Override
    public String getTag() {
        return Tag;
    }

    @Override
    public String getOrder() {
        return Order;
    }

    @Override
    public String getMessage() {
        return Message;
    }
}
