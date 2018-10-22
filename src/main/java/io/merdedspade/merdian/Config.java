package io.merdedspade.merdian;
import java.io.*;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Config {
    public static boolean dev_mode;
    public static boolean prod_mode;
    public static String mode;
    public static String token;
    public static String owner;
    public static String prefix;
    public static String debug;
    public static String shards_string;
    public static int shards;
    public static boolean isDebug;
    final static Logger logger = LoggerFactory.getLogger(Config.class);
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
            dev_mode = false;
            prod_mode = false;
            mode = property.getProperty("Mode");
            if (mode.equals("dev")) {
                dev_mode = true;
            }
            if (mode.equals("prod")) {
                prod_mode = true;
            }
            token = property.getProperty("Token");
            prefix = property.getProperty("Prefix");
            debug = property.getProperty("Debug");
            owner = property.getProperty("BotOwner");
            shards_string = property.getProperty("Shards");
            shards = Integer.parseInt(shards_string);
            il("Prefix: " + prefix);
            boolConverter();
            il("Debug: " + isDebug);
        } catch (IOException e) {
            el("Config file not found! Please download full archive with config! Stopping...");
            System.exit(1);
        }

    }

    public static boolean boolConverter() {
        if (debug.equals("true")) {
            isDebug = true;
        } else {
            isDebug = false;
        }
        return isDebug;
    }




    }

