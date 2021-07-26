package push.socket;

import push.packet.DataPacket;
import push.packet.NullPacket;
import push.packet.Packet;

import java.io.*;
import java.net.Socket;

public class ObjectWrappedSocket implements WrappedSocket {
    private Socket socket;

    public String socketId = "NON";

    public ObjectWrappedSocket(Socket socket){
        this.socket = socket;
    }

    @Override
    public void send(String tag, String order, String data){
        try {
            DataPacket packet = new DataPacket(tag, order, data);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setSocketId(String socketId) {
        this.socketId = socketId;
    }

    @Override
    public String getSocketId() {
        return this.socketId;
    }

    @Override
    public Packet recieve() {
        Packet packet = new NullPacket();

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            packet = (Packet) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return packet;
    }

    @Override
    public boolean isConnect() {
        try {
            InputStream inputStream = socket.getInputStream();
            inputStream.read();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
