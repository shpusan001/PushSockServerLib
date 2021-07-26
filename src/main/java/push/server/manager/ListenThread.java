package push.server.manager;

import push.server.log.LogFormat;
import push.server.socket.ObjectWrappedSocket;
import push.server.socket.WrappedSocket;

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
                new LogFormat("ServerListen", "Client connect, Connected : "
                        + serverManager.repository.wrappedSocketList.size()).log();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
