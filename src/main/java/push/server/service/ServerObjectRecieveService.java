package push.server.service;

import push.packet.Packet;
import push.log.LogFormat;
import push.server.manager.ServerManager;
import push.socket.WrappedSocket;

public class ServerObjectRecieveService {

    ServerManager serverManager = ServerManager.instance;

    public static ServerObjectRecieveService instance = new ServerObjectRecieveService();

    private ServerObjectRecieveService(){}

    /**
     * [Order]
     * #1 : NOTICE
     * @return
     */

    public void process(WrappedSocket wrappedSocket, Packet packet){
        switch(packet.getOrder()){
            case "UUID" : order_UUID(wrappedSocket, packet); break;
        }
    }

    private void order_UUID(WrappedSocket wrappedSocket, Packet packet){
        wrappedSocket.setSocketId(packet.getMessage());
        serverManager.repository.RegisteredSocketMap.put(wrappedSocket.getSocketId(), wrappedSocket);
        new LogFormat("Server", "{" + wrappedSocket.getSocketId() + "} is registered");
    }
}
