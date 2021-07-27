package push.client.service;

import push.log.LogFormat;
import push.packet.Packet;
import push.server.manager.ServerManager;
import push.socket.WrappedSocket;

public class ClientObjectRecieveService {

    ServerManager serverManager = ServerManager.instance;

    public static ClientObjectRecieveService instance = new ClientObjectRecieveService();

    private ClientObjectRecieveService(){}

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
        switch(packet.getOrder()){
            case "NOTICE" : order_NOTICE(wrappedSocket, packet); break;
        }
    }

    private void order_NOTICE(WrappedSocket wrappedSocket, Packet packet){
        new LogFormat(packet.getTag(), "Server => {" + wrappedSocket.getSocketId() + "} : " + packet.getMessage()).log();
    }
}
