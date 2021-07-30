package pusha.server.repository;

import pusha.socket.WrappedSocket;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class WrappedSocketRepository implements SocketRepository{

    public List<WrappedSocket> wrappedSocketList = new CopyOnWriteArrayList<>();

    public Map<String, WrappedSocket> RegisteredSocketMap = new HashMap<>();


    @Override
    public int sizeOnList() {
        return wrappedSocketList.size();
    }

    @Override
    public boolean addOnList(WrappedSocket wrappedSocket) {

        if(!wrappedSocketList.contains(wrappedSocket)){
            wrappedSocketList.add(wrappedSocket);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Iterator<WrappedSocket> getIteratorOnList() {
        return wrappedSocketList.iterator();
    }

    @Override
    public boolean removeOnList(WrappedSocket wrappedSocket) {

        if(wrappedSocketList.contains(wrappedSocket)){
            wrappedSocketList.remove(wrappedSocket);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean addOnMap(String key, WrappedSocket wrappedSocket) {

        if(!RegisteredSocketMap.containsKey(key)){
            RegisteredSocketMap.put(key, wrappedSocket);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public WrappedSocket getOnMap(String key) {
        return RegisteredSocketMap.get(key);
    }

    @Override
    public boolean removeOnMap(String key) {

        if(RegisteredSocketMap.containsKey(key)){
            RegisteredSocketMap.remove(key);
            return true;
        }else {
            return false;
        }
    }
}
