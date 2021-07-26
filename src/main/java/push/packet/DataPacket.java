package push.packet;

public class DataPacket implements Packet {
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
