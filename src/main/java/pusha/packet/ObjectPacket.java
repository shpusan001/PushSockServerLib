package pusha.packet;

public class ObjectPacket implements Packet{
    String tag;
    String order;
    String message;
    Object object;

    public ObjectPacket(String tag, String order, String message, Object object) {
        this.tag = tag;
        this.order = order;
        this.message = message;
        this.object = object;
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
    public String getMessage() {
        return message;
    }

    @Override
    public Object getObject() {
        return object;
    }
}
