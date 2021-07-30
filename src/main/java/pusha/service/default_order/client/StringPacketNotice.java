package pusha.service.default_order.client;

import pusha.log.SoutLog;
import pusha.packet.Packet;
import pusha.service.default_order.Order;
import pusha.socket.WrappedSocket;

public class StringPacketNotice implements Order {
    @Override
    public void excute(WrappedSocket wrappedSocket, Object object) {
        Packet packet = (Packet) object;
        new SoutLog(packet.getTag(), "Server => {" + wrappedSocket.getSocketId() + "} : " + packet.getData()).log();
    }
}
