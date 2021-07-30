package pusha.packet;

public interface Packet {
    public String getOrder();
    public String getTag();
    public String getMessage();
    public Object getObject();
}
