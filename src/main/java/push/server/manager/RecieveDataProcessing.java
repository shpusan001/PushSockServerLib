package push.server.manager;

import push.packet.Packet;
import push.server.log.LogFormat;
import push.server.socket.WrappedSocket;

public class RecieveDataProcessing {

    ServerManager serverManager = ServerManager.instance;

    public static RecieveDataProcessing instance = new RecieveDataProcessing();

    private RecieveDataProcessing(){}

    /**
     * [Order]
     * #1 : NOTICE
     * @return
     */

    public void process(WrappedSocket wrappedSocket, Packet packet){
        switch(packet.getOrder()){
            case "UUID" : order_Notice(wrappedSocket, packet); break;
        }
    }

    private void order_Notice(WrappedSocket wrappedSocket, Packet packet){
        wrappedSocket.setSocketId(packet.getMessage());
        serverManager.repository.RegisteredSocketMap.put(wrappedSocket.getSocketId(), wrappedSocket);
        new LogFormat("Server", "{" + wrappedSocket.getSocketId() + "} is registered");
    }
}
