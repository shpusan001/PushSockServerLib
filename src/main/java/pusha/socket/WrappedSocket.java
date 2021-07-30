package pusha.socket;

import pusha.packet.Packet;

import java.net.Socket;

public interface WrappedSocket {

    public void send(Packet packet);
    public Object recieve();
    public boolean isConnect();
    public void close();

    public Socket getSocket();
    public void setSocketId(String socketId);
    public String getSocketId();
}
