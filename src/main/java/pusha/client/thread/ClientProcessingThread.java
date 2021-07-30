package pusha.client.thread;

import pusha.configuration.ClientConfiguration;
import pusha.client.manager.ClientManager;
import pusha.service.ClientPacketRecieveService;
import pusha.log.SoutLog;


public class ClientProcessingThread implements Runnable {

    ClientManager clientManager;

    public ClientProcessingThread(ClientManager clientManager){
        this.clientManager = clientManager;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            //connection check
                if (ClientManager.instance.getSocket() != null) {
                    if (!ClientManager.instance.getSocket().isConnect()) {
                        tryReconnect();
                    } else {
                    //recieve data processing
                    Object packet = clientManager.getSocket().recieve();
                    ClientPacketRecieveService.instance.process(clientManager.getSocket(), packet);
                }
            } else {
                tryReconnect();
            }
        }
    }

    // reconnect method
    private void tryReconnect() {
        try {
            new SoutLog("Client", "Server disconnected, try reconnect after 3s").log();
            Thread.sleep(3000);
            ClientManager.instance.connect(ClientConfiguration.instance.ip, ClientConfiguration.instance.port,
                    ClientConfiguration.instance.id);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}