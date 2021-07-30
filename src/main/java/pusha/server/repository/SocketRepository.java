package pusha.server.repository;

import pusha.socket.WrappedSocket;

import java.util.Iterator;

public interface SocketRepository {
    int sizeOnList();
    boolean addOnList(WrappedSocket wrappedSocket);
    Iterator<WrappedSocket> getIteratorOnList();
    boolean removeOnList(WrappedSocket wrappedSocket);

    boolean addOnMap(String key, WrappedSocket wrappedSocket);
    WrappedSocket getOnMap(String key);
    boolean removeOnMap(String key);
}
