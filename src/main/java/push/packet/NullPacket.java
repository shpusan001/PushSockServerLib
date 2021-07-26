package push.packet;

public class NullPacket implements Packet {
    @Override
    public String getTag() {
        return null;
    }

    @Override
    public String getOrder() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
