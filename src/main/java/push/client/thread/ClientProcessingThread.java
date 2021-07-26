package push.client.thread;

import push.client.manager.ClientManager;
import push.log.LogFormat;


public class ClientProcessingThread implements Runnable {
    ClientManager clientManager = ClientManager.instance;

    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            //connection check
           if(!ClientManager.instance.getSocket().isConnect()){
               new LogFormat("Client", "Server disconnected, try reconnect after 3s").log();
               try {
                   Thread.sleep(3000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }else {
               //recieve data processing

           }
        }
    }
}
