package pusha.log;

import java.time.LocalDateTime;

public class LogFormat {

    final static boolean belog = true;

    String time;
    String tag;
    String log;

    public LogFormat(String tag, String log) {
        this.time = LocalDateTime.now().toString();
        this.tag = tag;
        this.log = log;
    }

    public void log(){
        if(belog) System.out.println("[" + time + "] " + "[" + tag + "] " + log);
    }

    @Override
    public String toString(){
        return ("[" + time + "] " + "[" + tag + "] " + log);
    }
}
