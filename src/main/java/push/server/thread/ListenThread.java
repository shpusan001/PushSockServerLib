package push.server.thread;

import push.log.LogFormat;
import push.server.manager.ServerManager;
import push.socket.ObjectWrappedSocket;
import push.socket.WrappedSocket;

import java.io.IOException;
import java.net.Socket;

public class ListenThread implements Runnable{

    ServerManager serverManager = ServerManager.instance;

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()){
            try {
                Socket socket = serverManager.getServerSocket().accept();
                WrappedSocket wrappedSocket = new ObjectWrappedSocket(socket);
                serverManager.repository.wrappedSocketList.add(wrappedSocket);
                new LogFormat("Server", "Client connect, Connected : "
                        + serverManager.repository.wrappedSocketList.size()).log();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
