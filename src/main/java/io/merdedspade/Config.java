package io.merdedspade;
import java.io.*;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Config {
    public static String token;
    public static String prefix;
    final static Logger logger = LoggerFactory.getLogger(Merdian.class);
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

