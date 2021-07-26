package push.server.socket;

public interface WrappedSocket {

    public void send(String tag, String order, String data);
    public Object recieve();
    public boolean isConnect();
}
