package pusha.client.manager;

import pusha.client.thread.ClientProcessingThread;
import pusha.log.LogFormat;
import pusha.packet.Packet;
import pusha.socket.ObjectWrappedSocket;
import pusha.socket.WrappedSocket;

import java.io.IOException;
import java.net.Socket;

public class ClientManager {

    private WrappedSocket wrappedSocket;

    private Thread clientProcessingThread;

    public static ClientManager instance;

    public WrappedSocket getSocket() {
            return wrappedSocket;
    }

    public static void use(){
        instance = new ClientManager();
    }

    public void connect(String ip, int port, String id){
        try {
            Socket socket = new Socket(ip, port);
            Socket checkingBitSocket = new Socket(ip, port+1);
            wrappedSocket = new ObjectWrappedSocket(socket, checkingBitSocket, false);
            wrappedSocket.setSocketId(id);
            wrappedSocket.send("UUID", "UUID", wrappedSocket.getSocketId());
            new LogFormat("Client", "connected, id ={" + id + "}").log();
        } catch (IOException e) {
        }
    }

    public void process(){
        new LogFormat("Client", "Client data processing start").log();
        clientProcessingThread = new Thread(new ClientProcessingThread());
        clientProcessingThread.start();
    }

    public void send(Packet packet){
        if(wrappedSocket!=null){
            wrappedSocket.send(packet);
        }
    }
}
