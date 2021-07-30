package pusha.service;

import pusha.log.SoutLog;
import pusha.packet.Packet;
import pusha.server.manager.ServerManager;
import pusha.service.default_order.Order;
import pusha.service.default_order.client.PacketNotice;
import pusha.socket.WrappedSocket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientPacketRecieveService implements RecieveService{

    private ServerManager serverManager = ServerManager.instance;

    private Map<String, Order> orderMap = new HashMap<>();

    public static ClientPacketRecieveService instance = new ClientPacketRecieveService();

    private ClientPacketRecieveService(){
        addOrder("NOTICE", new PacketNotice());
    }

    @Override
    public void process(WrappedSocket wrappedSocket, Object object){
        Packet packet = (Packet) object;
        Order order = orderMap.get(packet.getOrder());
        order.excute(wrappedSocket, packet);
    }

    @Override
    public boolean addOrder(String name, Order order) {
        if(!orderMap.containsKey(name)) {
            orderMap.put(name, order);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean removeOrder(String name) {
        if(orderMap.containsKey(name)){
            orderMap.remove(name);
            return true;
        }else{
            return false;
        }
    }
}
