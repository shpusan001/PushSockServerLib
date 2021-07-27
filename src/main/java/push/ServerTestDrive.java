package push;

import push.SockConfiguration;
import push.packet.DataPacket;
import push.server.manager.ServerManager;
import push.server.repository.WrappedSocketRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServerTestDrive {
    public static void main(String[] args) {
        ServerManager.use();
        ServerManager serverManager = ServerManager.instance;
        serverManager.setRepository(new WrappedSocketRepository());
        serverManager.bound(SockConfiguration.instance.port);
        serverManager.listen();
        serverManager.process();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(true){
            System.out.println("Write <TAG> <ORDER> <MESSAGE>");
            String input;
            try {
                input = br.readLine();
            } catch (IOException e) {
                System.out.println("Wrong input");
                continue;
            }

            String[] inputSplited = input.split(" ");
            String id = inputSplited[0];
            String tag = inputSplited[1];
            String order = inputSplited[2];
            String message = inputSplited[3];

            serverManager.sendTarget(id, new DataPacket(tag, order, message));
        }
    }
}
