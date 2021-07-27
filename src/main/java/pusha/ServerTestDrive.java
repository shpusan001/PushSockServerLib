package pusha;

import pusha.packet.DataPacket;
import pusha.server.manager.ServerManager;
import pusha.server.repository.WrappedSocketRepository;

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

            /**
             * Write <ID> <TAG> <ORDER> <MESSAGE>
             * be send
             */

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
