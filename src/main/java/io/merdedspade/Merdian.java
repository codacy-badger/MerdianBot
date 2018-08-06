package io.merdedspade;

import io.merdedspade.*;
import net.dv8tion.jda.client.entities.Group;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.PermissionException;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import javax.security.auth.login.LoginException;
import java.util.List;
import java.util.Random;


/*
Bot core here. Plz read README.md
 */
public class Merdian extends ListenerAdapter {
    protected static final Logger logger = LogManager.getLogger("BotLogger");
    static Config config = new Config();

    //Im lazy -_-
    public static void il(String msg) {
        logger.info(msg);
    }

    public static void el(String msg) {
        logger.error(msg);
    }


    public static void wl(String msg) {
        logger.warn(msg);
    }

    public static void bot() {
        try {
            JDA jda = new JDABuilder(AccountType.BOT)
                    .setToken(config.token)           //Bot token.
                    .addEventListener(new Merdian())
                    .setGame(Game.playing("Merdian | dev"))
                    .buildBlocking(); // Blocking guarantees that JDA will be completely loaded.
        } catch (LoginException e) {
            //If anything auth error.
            e.printStackTrace();
            el("Wrong token!");
        } catch (InterruptedException e) {
            e.printStackTrace();
            el("Error. ^");
        }
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {

        JDA jda = event.getJDA();                       //JDA
        long responseNumber = event.getResponseNumber();//The amount of discord events that JDA has received since the last reconnect.


        User author = event.getAuthor();                //The user that sent the message
        Message message = event.getMessage();           //The message that was received.
        MessageChannel channel = event.getChannel();    //This is the MessageChannel that the message was sent to.
        //  This could be a TextChannel, PrivateChannel, or Group!

        String msg = message.getContentDisplay();              //This returns a human readable version of the Message. Similar to
        // what you would see in the client.

        boolean bot = author.isBot();                    //This boolean is useful to determine if the User that
        // sent the Message is a BOT or not!

        if (event.isFromType(ChannelType.TEXT))         //If this message was sent to a Guild TextChannel
        {


            Guild guild = event.getGuild();             //The Guild that this message was sent in. (note, in the API, Guilds are Servers)
            TextChannel textChannel = event.getTextChannel(); //The TextChannel that this message was sent to.
            Member member = event.getMember();          //This Member that sent the message. Contains Guild specific information about the User!

            String name;
            if (message.isWebhookMessage()) {
                name = author.getName();
            }
            else {
                name = member.getEffectiveName();
            }

            logger.info("(%s)[%s]<%s>: %s\n", guild.getName(), textChannel.getName(), name, msg);
        } else if (event.isFromType(ChannelType.PRIVATE)) //If this message was sent to a PrivateChannel
        {

            PrivateChannel privateChannel = event.getPrivateChannel();

            logger.info("[DM]<%s>: %s\n", author.getName(), msg);
        } else if (event.isFromType(ChannelType.GROUP))
        {

            Group group = event.getGroup();
            String groupName = group.getName() != null ? group.getName() : "";

            System.out.printf("[GROUP: %s]<%s>: %s\n", groupName, author.getName(), msg);
        }


        if (msg.equals(config.prefix + "ping")) {

            channel.sendMessage("Pong! Bot working!").queue();
        }
    }
}