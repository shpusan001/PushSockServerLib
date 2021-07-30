package pusha.service;

import pusha.packet.Packet;
import pusha.log.SoutLog;
import pusha.server.manager.ServerManager;
import pusha.socket.WrappedSocket;

public class ServerObjectRecieveService implements RecieveService{

    ServerManager serverManager = ServerManager.instance;

    public static RecieveService instance = new ServerObjectRecieveService();

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

    @Override
    public void process(WrappedSocket wrappedSocket, Object object){
        Packet packet;
        if(object instanceof Packet) packet = (Packet) object;
        else {
            return;
        }

        switch (packet.getOrder()) {
            case "UUID" : order_UUID(wrappedSocket, packet); break;
            case "NOTICE" : order_NOTICE(wrappedSocket, packet); break;
        }
    }

    private void order_UUID(WrappedSocket wrappedSocket, Packet packet){
        wrappedSocket.setSocketId(packet.getMessage());
        serverManager.repository.addOnMap(packet.getMessage(), wrappedSocket);
        new SoutLog(packet.getTag(), "{" + wrappedSocket.getSocketId() + "} is registered").log();
    }

    private void order_NOTICE(WrappedSocket wrappedSocket, Packet packet){
        new SoutLog(packet.getTag(), "{" + wrappedSocket.getSocketId() + "} => Server : " + packet.getMessage()).log();
    }
}
