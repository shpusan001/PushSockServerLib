package push.server;

import push.server.manager.ServerManager;
import push.server.repository.WrappedSocketRepository;

public class TestDrive {
    public static void main(String[] args) {
        ServerManager.use();
        ServerManager serverManager = ServerManager.instance;
        serverManager.setRepository(new WrappedSocketRepository());
        serverManager.bound(3333);
        serverManager.listen();
        serverManager.DataProcessing();
    }
}
