package push.server.thread;

import push.log.LogFormat;
import push.packet.NullPacket;
import push.packet.Packet;
import push.server.manager.ServerManager;
import push.server.repository.WrappedSocketRepository;
import push.server.service.ServerObjectRecieveService;
import push.socket.WrappedSocket;

import java.util.HashMap;
import java.util.Map;


public class ServerProcessingThread implements Runnable {
    ServerManager serverManager = ServerManager.instance;
    WrappedSocketRepository repository = ServerManager.instance.repository;

    Map<WrappedSocket, Thread> recieveThreadMap = new HashMap<>();

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            //connection check
            //recieve data processing
            for (WrappedSocket wrappedSocket : repository.wrappedSocketList) {
                if (!wrappedSocket.isConnect()) {
                    /**
                     * Socket Disconnect Case
                     */

                    //Socket is closed
                    wrappedSocket.close();

                    //Socket in map is deleted
                    if (repository.RegisteredSocketMap.containsKey(wrappedSocket.getSocketId())) {
                        repository.RegisteredSocketMap.remove(wrappedSocket.getSocketId());
                    }

                    //Socket in list is deleted
                    repository.wrappedSocketList.remove(wrappedSocket);

                    new LogFormat("Server", "Client disconnected, SockID : " + wrappedSocket.getSocketId()).log();
                }else{
                    /**
                     * Socket Connect Case
                     */

                    // Create Recieve Thread Map -- 1Thread per 1Recieve Waiting
                    if(!recieveThreadMap.containsKey(wrappedSocket)){
                        recieveThreadMap.put(wrappedSocket, new Thread(new Recieve(wrappedSocket)));
                        recieveThreadMap.get(wrappedSocket).start();
                    }
                }
            }
            try { Thread.sleep(1100); } catch (InterruptedException e) { }
        }
    }

    class Recieve implements Runnable {

        WrappedSocket wrappedSocket;

        public Recieve(WrappedSocket wrappedSocket){
            this.wrappedSocket = wrappedSocket;
        }

        @Override
        public void run() {

            //Recieve data for packet
            Packet packet = (Packet) wrappedSocket.recieve();

            //if Null => NullPacket
            if (packet == null) packet = new NullPacket();

            //Packet Processing for order
            ServerObjectRecieveService.instance.process(wrappedSocket, packet);

            //Remove This Thread From recieveThreadMap
            recieveThreadMap.remove(wrappedSocket);
        }
    }
}
