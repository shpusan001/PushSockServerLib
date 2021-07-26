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
     * [Order]
     * #1 : NOTICE
     * @return
     */

    public void process(WrappedSocket wrappedSocket, Packet packet){
        switch(packet.getOrder()){
            case "NOTICE" : order_NOTICE(wrappedSocket, packet); break;
        }
    }

    private void order_NOTICE(WrappedSocket wrappedSocket, Packet packet){
        new LogFormat("Client", "Server to ..{" + wrappedSocket.getSocketId() + "} : " + packet.getMessage()).log();
    }
}
