package io.merdedspade;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import net.dv8tion.jda.*;
import net.dv8tion.jda.core.*;

public class Main {
    //TODO: make normal debug
    protected static final Logger logger = LogManager.getLogger("MainLogger");

    public static Config config = new Config();
    public static Info inf = new Info();

    public static void il(String msg){
        logger.info(msg);
    }
    public static void el(String msg){
        logger.error(msg);
    }
    public static void wl(String msg){
        logger.warn(msg);
    }
    public static void main(String[] args) {
        il("Merdian is MerdianBot " + inf.ver + inf.dev_status);
        if (inf.dev_status.equals("pre-alpha") || inf.dev_status.equals("beta") || inf.dev_status.equals("canary") || inf.dev_status.equals("alpha")){
            wl("THIS VERSION IN " + inf.dev_status.toUpperCase() + ". CAN BE UNSTABLE.");
        }
        config.start();


        
    }

}
