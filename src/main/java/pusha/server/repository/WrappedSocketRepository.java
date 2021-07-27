package pusha.server.repository;

import pusha.socket.WrappedSocket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

public class WrappedSocketRepository {

    public List<WrappedSocket> wrappedSocketList = new CopyOnWriteArrayList<>();

    public Map<String, WrappedSocket> RegisteredSocketMap = new HashMap<>();
}
