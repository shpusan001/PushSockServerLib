package pusha.server.thread;

import pusha.log.LogFormat;
import pusha.server.manager.ServerManager;
import pusha.socket.ObjectWrappedSocket;
import pusha.socket.WrappedSocket;

import java.io.IOException;
import java.net.Socket;

public class ListenThread implements Runnable{

    ServerManager serverManager = ServerManager.instance;

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()){
            try {
                Socket socket = serverManager.getServerSocket().accept();
                Socket bitCheckSocket = serverManager.getServerBitCheckSocket().accept();
                WrappedSocket wrappedSocket = new ObjectWrappedSocket(socket, bitCheckSocket, true);
                serverManager.repository.wrappedSocketList.add(wrappedSocket);
                new LogFormat("Server", "Client connect, Connected : "
                        + serverManager.repository.wrappedSocketList.size()).log();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
