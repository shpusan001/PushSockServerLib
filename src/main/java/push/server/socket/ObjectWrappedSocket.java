package push.server.socket;

import push.server.networkdata.DataPacket;
import push.server.networkdata.NullPacket;
import push.server.networkdata.Packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ObjectWrappedSocket implements WrappedSocket {
    private Socket socket;

    public String uuid;

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
    public Packet recieve() {
        Packet packet = new NullPacket();

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            packet = (DataPacket) objectInputStream.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return packet;
    }

    @Override
    public boolean isConnect() {
        try {
            socket.getInputStream();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
