package push.log;

import java.time.LocalDateTime;

public class LogFormat {
    String time;
    String tag;
    String log;

    public LogFormat(String tag, String log) {
        this.time = LocalDateTime.now().toString();
        this.tag = tag;
        this.log = log;
    }

    public void log(){
        System.out.println("[" + time + "] " + "[" + tag + "] " + log);
    }

    @Override
    public String toString(){
        return ("[" + time + "] " + "[" + tag + "] " + log);
    }
}
