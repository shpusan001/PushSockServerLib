package push;

public class SockConfiguration {

    public static SockConfiguration instance = new SockConfiguration();

    private SockConfiguration(){}

    /**
     * SOCKCOFIGURATION
     * - ip : host
     * - port : host port
     * - id : unique identification number
     */

    public String ip = "127.0.0.1";
    public int port = 7777;
    public String id = "";
}
