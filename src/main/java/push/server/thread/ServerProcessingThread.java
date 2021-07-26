package push.server.thread;

import push.log.LogFormat;
import push.packet.NullPacket;
import push.packet.Packet;
import push.server.manager.ServerManager;
import push.server.repository.WrappedSocketRepository;
import push.server.service.ServerObjectRecieveService;
import push.socket.WrappedSocket;


public class ServerProcessingThread implements Runnable {
    ServerManager serverManager = ServerManager.instance;
    WrappedSocketRepository repository = ServerManager.instance.repository;
    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            //connection check
            //recieve data processing
            for (WrappedSocket wrappedSocket : repository.wrappedSocketList) {
                try {
                    if (!wrappedSocket.isConnect()) {
                        repository.wrappedSocketList.remove(wrappedSocket);

                        if (repository.RegisteredSocketMap.containsKey(wrappedSocket.getSocketId())) {
                            repository.RegisteredSocketMap.remove(wrappedSocket.getSocketId());
                        }

                        wrappedSocket.close();

                        new LogFormat("Server", "Client disconnected, SockID : " + wrappedSocket.getSocketId()).log();
                    }else{
                        if(wrappedSocket.isConnect()) {
                            Packet packet = (Packet) wrappedSocket.recieve();
                            if (packet == null) packet = new NullPacket();
                            ServerObjectRecieveService.instance.process(wrappedSocket, packet);
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
