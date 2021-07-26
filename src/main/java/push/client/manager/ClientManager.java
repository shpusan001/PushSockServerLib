package push.client.manager;

import push.client.thread.ClientProcessingThread;
import push.log.LogFormat;
import push.socket.ObjectWrappedSocket;
import push.socket.WrappedSocket;

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

    public void connect(String ip, int port){
        try {
            Socket socket = new Socket(ip, port);
            wrappedSocket = new ObjectWrappedSocket(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void process(){
        new LogFormat("Client", "Client data processing start").log();
        clientProcessingThread = new Thread(new ClientProcessingThread());
        clientProcessingThread.start();
    }
}
