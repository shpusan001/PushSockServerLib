import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pusha.packet.DataPacket;
import pusha.packet.NullPacket;
import pusha.packet.Packet;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ObjectStreamTest {

    @Test
    @DisplayName("object test")
    void ObjectStreamTest() throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(11111);
        Socket socketClient = new Socket("127.0.0.1", 11111);
        Socket socketServer = serverSocket.accept();

        ObjectOutputStream coos = new ObjectOutputStream(socketClient.getOutputStream());
        ObjectOutputStream soos = new ObjectOutputStream(socketServer.getOutputStream());

        ObjectInputStream cois = new ObjectInputStream(socketClient.getInputStream());
        ObjectInputStream sois = new ObjectInputStream(socketServer.getInputStream());

        coos.writeObject(new DataPacket("tag", "order", "message"));
        Packet tmp = (Packet) sois.readObject();
        System.out.println(tmp.getMessage());
        coos.writeObject(new NullPacket());
        Packet tmp2 = (Packet) sois.readObject();
        System.out.println(tmp2.getMessage());
        sois.readObject();
        System.out.println("suceess");
    }

    /*
    @Test
    void ObjectStreamTest2() throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket(33333);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("1thread start");
                    WrappedSocket socketClient = new ObjectWrappedSocket(new Socket("127.0.0.1", 33333), false);
                    socketClient.send(new DataPacket("hi", "hi", "hihi0"));
                    socketClient.send(new DataPacket("hi", "hi", "hihi1"));
                    socketClient.send(new DataPacket("hi", "hi", "hihi2"));
                    System.out.println("1thread end");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("2thread start");
                    WrappedSocket socketServer = new ObjectWrappedSocket(serverSocket.accept(), true);
                    Thread.sleep(1000);
                    System.out.println(((Packet) socketServer.recieve()).getMessage());
                    System.out.println(((Packet) socketServer.recieve()).getMessage());
                    System.out.println(((Packet) socketServer.recieve()).getMessage());
                    System.out.println("2thread end");
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Thread.sleep(10000);

    }
    */

}

