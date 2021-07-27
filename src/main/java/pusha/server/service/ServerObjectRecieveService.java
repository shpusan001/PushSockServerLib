package pusha.server.service;

import pusha.packet.Packet;
import pusha.log.LogFormat;
import pusha.server.manager.ServerManager;
import pusha.socket.WrappedSocket;

public class ServerObjectRecieveService {

    ServerManager serverManager = ServerManager.instance;

    public static ServerObjectRecieveService instance = new ServerObjectRecieveService();

    private ServerObjectRecieveService(){}

    /**
     * Orders can be added with switch statements and functions
     *
     * --ex
     * If :: order to be added is BROADCAST
     * Then :: add a order to a switch statement
     *         create and add a function in this form
     *         function : order_BROADCAST(WrappedSocket wrappedSocket, Packet packet)
     */

    public void process(WrappedSocket wrappedSocket, Packet packet){
        switch (packet.getOrder()) {
            case "UUID" : order_UUID(wrappedSocket, packet); break;
            case "NOTICE" : order_NOTICE(wrappedSocket, packet); break;
        }
    }

    private void order_UUID(WrappedSocket wrappedSocket, Packet packet){
        wrappedSocket.setSocketId(packet.getMessage());
        serverManager.repository.RegisteredSocketMap.put(packet.getMessage(), wrappedSocket);
        new LogFormat(packet.getTag(), "{" + wrappedSocket.getSocketId() + "} is registered").log();
    }

    private void order_NOTICE(WrappedSocket wrappedSocket, Packet packet){
        new LogFormat(packet.getTag(), "{" + wrappedSocket.getSocketId() + "} => Server : " + packet.getMessage()).log();
    }
}
