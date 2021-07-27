package push.client.thread;

import push.SockConfiguration;
import push.client.manager.ClientManager;
import push.client.service.ClientObjectRecieveService;
import push.log.LogFormat;
import push.packet.Packet;


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