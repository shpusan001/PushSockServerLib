package pusha.testdrive;

import pusha.configuration.ClientConfiguration;
import pusha.client.manager.ClientManager;
import pusha.packet.StringPacket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class MutiConnectClientTestDrive {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("start");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Host Ip:");
        try {
            ClientConfiguration.instance.ip = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<ClientManager> clist = new LinkedList<>();
        int count =0;
        for (int i = 0; i < 10000; i++) {
            clist.add(new ClientManager());
        }
        for (ClientManager c : clist) {
            c.connect(ClientConfiguration.instance.ip, ClientConfiguration.instance.port, UUID.randomUUID().toString());
            count++;
            System.out.println(count);
        }

        Thread.sleep(3000);

        for (ClientManager c : clist) {
            c.processThis();
        }

        while (true){
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
            String data = inputSplited[3];

            /**
             * Write <ID> <ORDER> <TAG> <MESSAGE>
             * be send
             */

            if(ClientManager.clientMap.containsKey(id)) {
                ClientManager.clientMap.get(id).send(new StringPacket(order, tag, data));
            }
        }
    }
}
