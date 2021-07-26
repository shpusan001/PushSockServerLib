package push.server.manager;

import push.packet.Packet;
import push.server.log.LogFormat;
import push.server.repository.WrappedSocketRepository;
import push.server.socket.WrappedSocket;


public class DataProcessingThread implements Runnable {
    ServerManager serverManager = ServerManager.instance;
    WrappedSocketRepository repository = ServerManager.instance.repository;
    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            //connection check
            for (WrappedSocket wrappedSocket : repository.wrappedSocketList) {
                if(!wrappedSocket.isConnect()){
                    repository.wrappedSocketList.remove(wrappedSocket);

                    if(repository.RegisteredSocketMap.containsKey(wrappedSocket.getSocketId())){
                        repository.RegisteredSocketMap.remove(wrappedSocket.getSocketId());
                    }

                    new LogFormat("Server", "Client disconnected, SockID : " + wrappedSocket.getSocketId()).log();
                }
            }

            //recieve data processing
            for (WrappedSocket wrappedSocket : repository.wrappedSocketList) {
                Object data = wrappedSocket.recieve();
                Packet packet = (Packet) data;
                RecieveDataProcessing.instance.process(wrappedSocket, packet);
            }
        }
    }
}
