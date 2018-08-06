package io.merdedspade;
import java.io.*;
import java.util.Properties;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
public class Config {
    public static String token;
    public static String prefix;
    protected static final Logger logger = LogManager.getLogger("ConfigLogger");
    public static void il(String msg){
        logger.info(msg);
    }
    public static void el(String msg){
        logger.error(msg);
    }
    public static void wl(String msg){
        logger.warn(msg);
    }
    public static void start(){
        FileInputStream fis;
        Properties property = new Properties();
        il("Reading config file...");
        try {
            fis = new FileInputStream("config.properties"); //src/main/resources/config.properties
            property.load(fis);

            token = property.getProperty("Token");
            prefix = property.getProperty("Prefix");
            il("Prefix: " + prefix);

        } catch (IOException e) {
            el("Config file not found! Stopping...");
            System.exit(1);
        }

    }
    }

