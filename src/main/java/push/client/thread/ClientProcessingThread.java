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
        while(!Thread.currentThread().isInterrupted()) {
            //connection check
            try {
                if(!ClientManager.instance.getSocket().isConnect()){
                    new LogFormat("Client", "Server disconnected, try reconnect after 3s").log();
                    try {
                        Thread.sleep(3000);
                        ClientManager.instance.connect(SockConfiguration.instance.ip, SockConfiguration.instance.port,
                                SockConfiguration.instance.id);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else {
                    //recieve data processing
                    Object data = clientManager.getSocket().recieve();
                    Packet packet = (Packet) data;
                    ClientObjectRecieveService.instance.process(clientManager.getSocket(), packet);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
