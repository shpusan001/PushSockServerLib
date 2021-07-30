package pusha.packet;

public class ObjectPacket implements Packet{
    String tag;
    String order;
    Object object;

    public ObjectPacket(String order, String tag, Object object) {
        this.tag = tag;
        this.order = order;
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
    public Object getData() {
        return object;
    }
}
