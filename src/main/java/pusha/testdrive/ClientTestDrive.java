package pusha.testdrive;

import pusha.Configuration.ClientConfiguration;
import pusha.client.manager.ClientManager;
import pusha.packet.DataPacket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

public class ClientTestDrive {
    public static void main(String[] args) {
        ClientConfiguration.instance.id = UUID.randomUUID().toString();
        String uuid = ClientConfiguration.instance.id;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Host Ip:");
        try {
            ClientConfiguration.instance.ip = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ClientManager.use();
        ClientManager clientManager = ClientManager.instance;
        clientManager.connect(ClientConfiguration.instance.ip, ClientConfiguration.instance.port, uuid);
        clientManager.process();


        while(true){

            /**
             * Write <TAG> <ORDER> <MESSAGE>
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
            String tag = inputSplited[0];
            String order = inputSplited[1];
            String message = inputSplited[2];

            clientManager.send(new DataPacket(tag, order, message));
        }
    }
}
