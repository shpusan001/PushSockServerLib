package push.server.networkdata;

public class DataPacket implements Packet{
    String Tag;
    String Order;
    String Message;

    public DataPacket(String tag, String order, String message) {
        Tag = tag;
        Order = order;
        Message = message;
    }
}
