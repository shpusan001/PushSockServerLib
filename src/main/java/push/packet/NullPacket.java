package push.packet;

import java.io.Serializable;

public class NullPacket implements Serializable, Packet {
    @Override
    public String getTag() {
        return "NULL";
    }

    @Override
    public String getOrder() {
        return "NULL";
    }

    @Override
    public String getMessage() {
        return "NULL";
    }
}
