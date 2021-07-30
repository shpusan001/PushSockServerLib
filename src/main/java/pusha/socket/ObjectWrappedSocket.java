package pusha.socket;

import pusha.packet.StringPacket;
import pusha.packet.NullPacket;
import pusha.packet.Packet;

import java.io.*;
import java.net.Socket;

public class ObjectWrappedSocket implements WrappedSocket {
    private Socket socket;
    private Socket checkingBitSocket;
    private boolean isServerSide;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    private Thread connectingCheckThread;

    public String socketId = "NONE";

    public ObjectWrappedSocket(Socket socket, Socket checkingBitSocket, boolean isServerSide){

        this.socket = socket;
        this.isServerSide = isServerSide;
        this.checkingBitSocket = checkingBitSocket;

        try {
            objectOutputStream = new ObjectOutputStream(this.socket.getOutputStream());
            objectInputStream = new ObjectInputStream(this.socket.getInputStream());
        } catch (IOException e) {
        }
    }

    @Override
    public void send(Packet packet){
        try {
                objectOutputStream.writeObject(packet);
                objectOutputStream.reset();
        } catch (IOException e) {
        }
    }

    @Override
    public Socket getSocket(){
        return socket;
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
            if(data instanceof StringPacket){
                packet = (StringPacket) data;
            }else{
                packet = new NullPacket();
            }
        } catch (IOException | ClassNotFoundException e) {
        }

        return packet;
    }

    @Override
    public boolean isConnect() {
        try {
            if(objectOutputStream==null || objectInputStream ==null
            || socket.isClosed()){
                return false;
            }
                DataOutputStream dataOutputStream = new DataOutputStream(checkingBitSocket.getOutputStream());
                dataOutputStream.writeUTF("");
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
}
