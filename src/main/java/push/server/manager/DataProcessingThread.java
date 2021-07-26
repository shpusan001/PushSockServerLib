package push.server.manager;

import push.server.log.LogFormat;
import push.server.socket.ObjectWrappedSocket;
import push.server.socket.WrappedSocket;

import java.io.IOException;
import java.net.Socket;

public class DataProcessingThread implements Runnable {
    ServerManager serverManager = ServerManager.instance;

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {

        }
    }
}
