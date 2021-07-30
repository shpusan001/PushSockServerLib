package pusha.server.thread;

import pusha.log.SoutLog;
import pusha.server.manager.ServerManager;
import pusha.server.repository.SocketRepository;
import pusha.service.ServerPacketRecieveService;
import pusha.socket.WrappedSocket;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class ServerProcessingThread implements Runnable {
    ServerManager serverManager = ServerManager.instance;
    SocketRepository repository = ServerManager.instance.repository;

    Map<WrappedSocket, Thread> recieveThreadMap = new HashMap<>();

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            //connection check
            //recieve data processing
            Iterator<WrappedSocket> listIterator = repository.getIteratorOnList();
            while(listIterator.hasNext()){
                WrappedSocket wrappedSocket = listIterator.next();
                if (!wrappedSocket.isConnect()) {
                    /**
                     * Socket Disconnect Case
                     */

                    //Socket is closed
                    wrappedSocket.close();

                    //Socket in map is deleted
                    repository.removeOnMap(wrappedSocket.getSocketId());

                    //Socket in list is deleted
                    repository.removeOnList(wrappedSocket);

                    new SoutLog("Server", "Client disconnected, SockID : " + wrappedSocket.getSocketId()).log();
                } else {
                    /**
                     * Socket Connect Case
                     */

                    // Create Recieve Thread Map -- 1Thread per 1Recieve Waiting

                    if (!recieveThreadMap.containsKey(wrappedSocket)) {
                        recieveThreadMap.put(wrappedSocket, new Thread(new Recieve(wrappedSocket)));
                        recieveThreadMap.get(wrappedSocket).start();
                    }
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }

    class Recieve implements Runnable {

        WrappedSocket wrappedSocket;

        public Recieve(WrappedSocket wrappedSocket) {
            this.wrappedSocket = wrappedSocket;
        }

        @Override
        public void run() {
            //Recieve data for packet
            Object packet = wrappedSocket.recieve();

            //Packet Processing for order
            ServerPacketRecieveService.instance.process(wrappedSocket, packet);

            //Remove This Thread From recieveThreadMap
            recieveThreadMap.remove(wrappedSocket);
        }
    }
}


