package pusha.configuration;

public class ClientConfiguration {
    public static ClientConfiguration instance = new ClientConfiguration();

    /**
     * CLIENT CONFIGURATION
     * - ip : host
     * - port : host port
     * - id : unique identification number
     */

    public String ip = "127.0.0.1";
    public int port = 7777;
    public String id = "";
}
