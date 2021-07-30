package pusha.testdrive;

import pusha.configuration.ServerConfiguration;
import pusha.packet.StringPacket;
import pusha.server.manager.ServerManager;
import pusha.server.repository.MemorySocketRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServerTestDrive {
    public static void main(String[] args) {
        ServerManager.use();
        ServerManager serverManager = ServerManager.instance;
        serverManager.setRepository(new MemorySocketRepository());
        serverManager.bound(ServerConfiguration.instance.port);
        serverManager.listen();
        serverManager.process();

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        while(true){

            /**
             * Write <ID> <ORDER> <TAG> <MESSAGE>
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
            String order = inputSplited[1];
            String tag = inputSplited[2];
            String message = inputSplited[3];

            serverManager.sendTarget(id, new StringPacket(tag, order, message));
        }
    }
}
