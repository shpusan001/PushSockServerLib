package push.client.manager;

import push.client.thread.ClientProcessingThread;
import push.log.LogFormat;
import push.packet.Packet;
import push.socket.ObjectWrappedSocket;
import push.socket.WrappedSocket;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.net.Socket;
import java.util.Optional;

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
