package io.merdedspade.merdian;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Launch {
    protected static Merdian bot = new Merdian();
    final static Logger logger = LoggerFactory.getLogger(Launch.class);


    public static Const dev = new Const();

    public static void il(String msg) {
        logger.info(msg);
    }

    public static void el(String msg) {
        logger.error(msg);
    }

    public static void wl(String msg) {
        logger.warn(msg);
    }

    public static void main(String[] args) {
        il("Merdian is MerdianBot " + dev.ver + " " + dev.dev_status);
        if (dev.dev_status.equals("alpha") || dev.dev_status.equals("pre-alpha") || dev.dev_status.equals("beta") || dev.dev_status.equals("canary")) {
            wl("THIS VERSION IN " + dev.dev_status.toUpperCase() + ". CAN BE UNSTABLE.");
        }
        Config.start();
        il("Now starting bot!");
        bot.bot();

    }


}


