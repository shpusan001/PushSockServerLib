package pusha.packet;

public class ObjectPacket implements Packet{
    String tag;
    String order;
    String message;
    Object object;

    @Override
    public String getOrder() {
        return order;
    }

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Object getObject() {
        return object;
    }
}
