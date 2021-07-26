package push.server;

import push.client.manager.ClientManager;
import push.packet.DataPacket;
import push.server.manager.ServerManager;
import push.server.repository.WrappedSocketRepository;

import java.util.UUID;

public class TestDrive {
    public static void main(String[] args) {
        String uuid = UUID.randomUUID().toString();
        new Thread(new Runnable() {
            @Override
            public void run() {
                ServerManager.use();
                ServerManager serverManager = ServerManager.instance;
                serverManager.setRepository(new WrappedSocketRepository());
                serverManager.bound(3333);
                serverManager.listen();
                serverManager.process();
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                serverManager.sendTarget(uuid, new DataPacket("1", "NOTICE", "1"));
                serverManager.sendTarget(uuid, new DataPacket("1", "NOTICE", "와우"));
                serverManager.sendTarget(uuid, new DataPacket("1", "NOTICE", "반가워용"));
                serverManager.sendTarget(uuid, new DataPacket("1", "NOTICE", "호호호"));
                serverManager.sendTarget(uuid, new DataPacket("1", "NOTICE", "호호호"));

            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                ClientManager.use();
                ClientManager clientManager = ClientManager.instance;
                clientManager.connect("127.0.0.1", 3333, uuid);
                clientManager.process();

                clientManager.getSocket().send(new DataPacket("1", "NOTICE", "히히히"));
                clientManager.getSocket().send(new DataPacket("1", "NOTICE", "히히히"));
                clientManager.getSocket().send(new DataPacket("1", "NOTICE", "히히히"));
                clientManager.getSocket().send(new DataPacket("1", "NOTICE", "히히히"));
                clientManager.getSocket().send(new DataPacket("1", "NOTICE", "히히히"));
                clientManager.getSocket().send(new DataPacket("1", "NOTICE", "히히히"));
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                clientManager.getSocket().close();
            }
        }).start();


    }
}
