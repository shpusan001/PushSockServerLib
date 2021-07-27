package push;

import push.SockConfiguration;
import push.client.manager.ClientManager;
import push.packet.DataPacket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

public class ClientTestDrive {
    public static void main(String[] args) {
        SockConfiguration.instance.id = UUID.randomUUID().toString();
        String uuid = SockConfiguration.instance.id;

        ClientManager.use();
        ClientManager clientManager = ClientManager.instance;
        clientManager.connect(SockConfiguration.instance.ip, SockConfiguration.instance.port, uuid);
        clientManager.process();

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
            String tag = inputSplited[0];
            String order = inputSplited[1];
            String message = inputSplited[2];

            clientManager.send(new DataPacket(tag, order, message));
        }
    }
}
