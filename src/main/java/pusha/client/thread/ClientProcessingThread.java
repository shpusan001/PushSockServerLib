package pusha.client.thread;

import pusha.SockConfiguration;
import pusha.client.manager.ClientManager;
import pusha.client.service.ClientObjectRecieveService;
import pusha.log.LogFormat;
import pusha.packet.Packet;


public class ClientProcessingThread implements Runnable {

    ClientManager clientManager = ClientManager.instance;

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            //connection check
                if (ClientManager.instance.getSocket() != null) {
                    if (!ClientManager.instance.getSocket().isConnect()) {
                        tryReconnect();
                    } else {
                    //recieve data processing
                    Object data = clientManager.getSocket().recieve();
                    Packet packet = (Packet) data;
                    ClientObjectRecieveService.instance.process(clientManager.getSocket(), packet);
                }
            } else {
                tryReconnect();
            }
        }
    }

    // reconnect method
    private void tryReconnect() {
        try {
            new LogFormat("Client", "Server disconnected, try reconnect after 3s").log();
            Thread.sleep(3000);
            ClientManager.instance.connect(SockConfiguration.instance.ip, SockConfiguration.instance.port,
                    SockConfiguration.instance.id);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}