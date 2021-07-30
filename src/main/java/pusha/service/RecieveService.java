package pusha.service;

import pusha.service.default_order.Order;
import pusha.socket.WrappedSocket;

public interface RecieveService {

    void process(WrappedSocket wrappedSocket, Object object);

    boolean addOrder(String name, Order order);
    boolean removeOrder(String name);
}
