package push.server.repository;

import push.server.socket.WrappedSocket;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class WrappedSocketRepository {

    public List<WrappedSocket> wrappedSocketList = new LinkedList<>(); //일단 서버에 접속한 소켓

    //서버에 접속한 소켓중, uuid를 얻고 uuid로 접근가능한 소켓 <uuid, 소켓>
    public Map<String, WrappedSocket> RegisteredSocketMap = new HashMap<>();
}
