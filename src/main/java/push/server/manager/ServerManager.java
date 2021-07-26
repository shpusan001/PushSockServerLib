package push.server.manager;

import push.log.LogFormat;
import push.packet.Packet;
import push.server.repository.WrappedSocketRepository;
import push.server.thread.ListenThread;
import push.server.thread.ServerProcessingThread;
import push.socket.WrappedSocket;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerManager {

    private ServerSocket serverSocket;
    private ServerSocket serverBitCheckSocket;
    private Thread listenThread;
    private Thread serverProcessingThread;

    public static ServerManager instance;
    public WrappedSocketRepository repository;

    private ServerManager(){}

    public static void use(){
        instance = new ServerManager();
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public ServerSocket getServerBitCheckSocket() {
        return serverBitCheckSocket;
    }

    public void setRepository(WrappedSocketRepository repository){
        this.repository = repository;
    }

    public void bound(int port){
        try {
            serverSocket = new ServerSocket(port);
            serverBitCheckSocket = new ServerSocket(port+1);
            new LogFormat("Server", "Server bound, port: " + port).log();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen(){
        new LogFormat("Server", "Server listen").log();
        listenThread = new Thread(new ListenThread());
        listenThread.start();
    }

    public void process(){
        new LogFormat("Server", "Server data processing start").log();
        serverProcessingThread = new Thread(new ServerProcessingThread());
        serverProcessingThread.start();
    }

    public void sendTarget(String id, Packet packet){
        if(repository.RegisteredSocketMap.containsKey(id)){
            WrappedSocket wrappedSocket = repository.RegisteredSocketMap.get(id);
            wrappedSocket.send(packet);
        }
    }
}
