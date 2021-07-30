package pusha.server.manager;

import pusha.log.SoutLog;
import pusha.packet.Packet;
import pusha.server.repository.SocketRepository;
import pusha.server.thread.ListenThread;
import pusha.server.thread.ServerProcessingThread;
import pusha.socket.WrappedSocket;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerManager {

    private ServerSocket serverSocket;
    private ServerSocket serverBitCheckSocket;
    private Thread listenThread;
    private Thread serverProcessingThread;

    public static ServerManager instance;
    public SocketRepository repository;
    public int connectedCount = 0;

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

    public void setRepository(SocketRepository repository){
        this.repository = repository;
    }

    public void bound(int port){
        try {
            serverSocket = new ServerSocket(port);
            serverBitCheckSocket = new ServerSocket(port+1);
            new SoutLog("Server", "Server bound, port: " + port).log();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen(){
        new SoutLog("Server", "Server listen").log();
        listenThread = new Thread(new ListenThread());
        listenThread.start();
    }

    public void process(){
        new SoutLog("Server", "Server data processing start").log();
        serverProcessingThread = new Thread(new ServerProcessingThread());
        serverProcessingThread.start();
    }

    //Send The Packet To The Desired Target
    public void sendTarget(String id, Packet packet){
            WrappedSocket wrappedSocket = repository.getOnMap(id);
            if(wrappedSocket!=null) wrappedSocket.send(packet);
    }
}
