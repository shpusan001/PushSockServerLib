package pusha.testdrive;

import pusha.configuration.ClientConfiguration;
import pusha.configuration.ServerConfiguration;
import pusha.client.manager.ClientManager;
import pusha.packet.StringPacket;
import pusha.server.manager.ServerManager;
import pusha.server.repository.MemorySocketRepository;

import java.util.UUID;

public class TestDrive {
    public static void main(String[] args) {

        /**
         * Sock Configuration Initialization
         * --default
         * ip : "127.0.0.1"
         * port : 7777
         * id : "" (must be initialized)
         */

        //Configuration's Id Initialize To UUID
        ClientConfiguration.instance.id = UUID.randomUUID().toString();

        String uuid = ClientConfiguration.instance.id;

        //Server Thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Server Setting
                ServerManager.use();
                ServerManager serverManager = ServerManager.instance;
                serverManager.setRepository(new MemorySocketRepository());
                serverManager.bound(ServerConfiguration.instance.port);
                serverManager.listen();
                serverManager.process();
                //

                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //Send To Client
                serverManager.sendTarget(uuid, new StringPacket("GOODTALK", "NOTICE", "hi"));
                serverManager.sendTarget(uuid, new StringPacket("GOODTALK", "NOTICE", "nice"));
                serverManager.sendTarget(uuid, new StringPacket("GOODTALK", "NOTICE", "good"));
                serverManager.sendTarget(uuid, new StringPacket("GOODTALK", "NOTICE", "exellent"));
                serverManager.sendTarget(uuid, new StringPacket("GOODTALK", "NOTICE", "pretty"));
                //

            }
        }).start();

        //Client Thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                //Client Setting
                ClientManager.use();
                ClientManager clientManager = ClientManager.instance;
                clientManager.connect(ClientConfiguration.instance.ip, ClientConfiguration.instance.port, uuid);
                clientManager.process();
                //

                //Send To Server
                clientManager.send(new StringPacket("TRASHTALK", "NOTICE", "bad"));
                clientManager.send(new StringPacket("TRASHTALK", "NOTICE", "ugly"));
                clientManager.send(new StringPacket("TRASHTALK", "NOTICE", "poop"));
                clientManager.send(new StringPacket("TRASHTALK", "NOTICE", "pee"));
                clientManager.send(new StringPacket("TRASHTALK", "NOTICE", "dummy"));
                //

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //Client Close
                clientManager.getSocket().close();

                /**
                 * If client sock closed,
                 * client sock automatically try to reconnect per 3s
                 */

                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //After 4s

                /**
                 * Client is resurrectioned
                 */

                //Send To Server
                clientManager.send(new StringPacket("TRASHTALK", "NOTICE", "sticky"));
            }
        }).start();


    }
}
