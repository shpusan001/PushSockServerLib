package pusha.service;

import pusha.socket.WrappedSocket;

public interface RecieveService {

    void process(WrappedSocket wrappedSocket, Object object);
}
