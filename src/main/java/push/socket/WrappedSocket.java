package push.socket;

import push.packet.Packet;

public interface WrappedSocket {

    public void send(String tag, String order, String data);
    public void send(Packet packet);
    public Object recieve();
    public boolean isConnect();
    public void close();

    public void setSocketId(String socketId);
    public String getSocketId();
}
