package io.merdedspade;

import io.merdedspade.*;
import ch.qos.logback.classic.Level;
import net.dv8tion.jda.bot.sharding.DefaultShardManager;
import net.dv8tion.jda.bot.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.bot.sharding.ShardManager;
import net.dv8tion.jda.client.entities.Group;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.exceptions.PermissionException;
import net.dv8tion.jda.core.exceptions.RateLimitedException;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.util.List;
import java.util.Random;


/*
Bot core here. Plz read README.md
 */








public class Merdian extends ListenerAdapter{

    static ch.qos.logback.classic.Logger l =
            (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Merdian.class);
    static Config config = new Config();
    //Using debug


    public static void bot() /*throws Exception*/{
        if(config.isDebug == true){
            l.setLevel(Level.TRACE);
        } else{
            l.setLevel(Level.INFO);
        }

        try {
            l.info("Loading shards...");
            JDABuilder shardBuilder = new JDABuilder(AccountType.BOT).setToken(config.token).setGame(Game.playing("Merdian | Indev"));
          /*  JDA jda = new JDA(AccountType.BOT)
                    .setToken(config.token)           //Bot token.
                    .addEventListener(new Merdian())
                    .setGame(Game.playing("Merdian | dev"))
                    .useSharding( 0, 10);
                    //.buildBlocking(); // Blocking guarantees that JDA will be completely loaded.


            DefaultShardManagerBuilder builder = new DefaultShardManagerBuilder();
            builder.setToken(config.token);
            //builder.addEventListener(new Merdian());
            builder.build();*/
            shardBuilder.addEventListener(new Merdian());
            for (int i = 0; i < 2; i++)
            {
                l.info("Loading shard ID: " + i);
                shardBuilder.useSharding(i, 2)
                        .buildAsync();
                l.info("Done: " + i);
            }

            l.info("Done loading shards!");
        } catch (LoginException e) {
            //If auth error.
            e.printStackTrace();
            l.error("Wrong token!");
        }





    }
    @Override
    public void onReady(ReadyEvent event){
        //SoonTM
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
        String user_id = author.getId();
        if (event.isFromType(ChannelType.TEXT))         //If this message was sent to a Guild TextChannel
        {


            Guild guild = event.getGuild();             //The Guild that this message was sent in. (note, in the API, Guilds are Servers)
            TextChannel textChannel = event.getTextChannel(); //The TextChannel that this message was sent to.
            Member member = event.getMember();          //This Member that sent the message. Contains Guild specific information about the User!

          /*  String name;
            if (message.isWebhookMessage()) {
                name = event.getMember();
            } else {
                name = member.getEffectiveName();
            }
	    */

            l.info("New message on server! \n" + "\n Guild name: " +  guild.getName() + "\n Channel: " + textChannel.getName() + "\n Author ID: " + author.getId() + "\n Message: " + msg);
        } else if (event.isFromType(ChannelType.PRIVATE)) //If this message was sent to a PrivateChannel
        {

            PrivateChannel privateChannel = event.getPrivateChannel();

            l.info("New message in DM \n" + "\n Author ID: " + author.getId() + "\n Message: " + msg);
        } else if (event.isFromType(ChannelType.GROUP)) {

            Group group = event.getGroup();
            String groupName = group.getName() != null ? group.getName() : "";

            l.info("New message in group \n" + "\n Group name: " + groupName + "\n Author ID: " + author.getId() + "\n Message: " + msg);
        }


        if (msg.equals(config.prefix + "ping")) {

            channel.sendMessage("Pong! Bot working!").queue();
        } else if (msg.equals(config.prefix + "shutdown") && author.equals(config.owner)) {
            channel.sendMessage("Bye! ").queue();
            System.exit(0);
        } 
    }
}
