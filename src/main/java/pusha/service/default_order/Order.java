package pusha.service.default_order;

import pusha.socket.WrappedSocket;

public interface Order {
    void excute(WrappedSocket wrappedSocket, Object object);
}
