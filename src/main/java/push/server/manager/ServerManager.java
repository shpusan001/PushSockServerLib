package push.server.manager;

import push.server.log.LogFormat;
import push.server.repository.WrappedSocketRepository;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerManager {

    private ServerSocket serverSocket;
    private Thread listenThread;
    private Thread dataProcessingThread;

    public static ServerManager instance;
    public WrappedSocketRepository repository;

    private ServerManager(){}

    public static void use(){
        instance = new ServerManager();
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setRepository(WrappedSocketRepository repository){
        this.repository = repository;
    }

    public void bound(int port){
        try {
            serverSocket = new ServerSocket(port);
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

    public void DataProcessing(){
        new LogFormat("Server", "Server data processing start").log();
        dataProcessingThread = new Thread(new DataProcessingThread());
        dataProcessingThread.start();
    }
}
