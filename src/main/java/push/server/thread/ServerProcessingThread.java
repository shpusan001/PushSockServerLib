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
                        /**
                         * Socket Disconnect Case
                         */

                        //Socket in list is deleted
                        repository.wrappedSocketList.remove(wrappedSocket);

                        //Socket in map is deleted
                        if (repository.RegisteredSocketMap.containsKey(wrappedSocket.getSocketId())) {
                            repository.RegisteredSocketMap.remove(wrappedSocket.getSocketId());
                        }

                        //Socket is closed
                        wrappedSocket.close();

                        new LogFormat("Server", "Client disconnected, SockID : " + wrappedSocket.getSocketId()).log();
                    }else{
                        /**
                         * Socket Connect Case
                         */

                        //Recieve data for packet
                        Packet packet = (Packet) wrappedSocket.recieve();

                        //if Null => NullPacket
                        if (packet == null) packet = new NullPacket();

                        //Packet Processing for order
                        ServerObjectRecieveService.instance.process(wrappedSocket, packet);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
