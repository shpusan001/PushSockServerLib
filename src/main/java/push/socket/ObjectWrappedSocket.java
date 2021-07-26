package push.socket;

import push.packet.DataPacket;
import push.packet.NullPacket;
import push.packet.Packet;

import java.io.*;
import java.net.Socket;

public class ObjectWrappedSocket implements WrappedSocket {
    private Socket socket;
    private Socket checkingBitSocket;
    private boolean isServerSide;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    private boolean isKill = false;
    private Thread connectingCheckThread;

    public String socketId = "NONE";

    @Override
    public boolean getIsKill() {
        return isKill;
    }

    public ObjectWrappedSocket(Socket socket, Socket checkingBitSocket, boolean isServerSide){

        this.socket = socket;
        this.isServerSide = isServerSide;
        this.checkingBitSocket = checkingBitSocket;

        try {
                objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
                objectInputStream = new ObjectInputStream(this.socket.getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void send(String tag, String order, String data){
        try {
            Packet packet = new DataPacket(tag, order, data);
                objectOutputStream.writeObject(packet);
                objectOutputStream.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void send(Packet packet){
        try {
                objectOutputStream.writeObject(packet);
                objectOutputStream.reset();
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
            Object data = objectInputStream.readObject();
            if(data instanceof DataPacket){
                packet = (DataPacket) data;
            }else{
                packet = new NullPacket();
            }
        } catch (IOException | ClassNotFoundException e) {
            isKill = true;
        }

        return packet;
    }

    @Override
    public boolean isConnect() {
        try {
                DataOutputStream dataOutputStream = new DataOutputStream(checkingBitSocket.getOutputStream());
                dataOutputStream.writeUTF("");
                dataOutputStream.flush();
                return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public void close(){
        try {
            socket.close();
            checkingBitSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    class ConnectingCheckThread implements Runnable{

        @Override
        public void run() {
            try {
                DataInputStream dataInputStream = new DataInputStream(checkingBitSocket.getInputStream());
                String message = dataInputStream.readUTF();
            } catch (IOException e) {
                isKill=true;
            }
        }
    }

}
