package io.merdedspade.merdian;

import ch.qos.logback.classic.Level;
import net.dv8tion.jda.client.entities.Group;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.*;
import net.dv8tion.jda.core.events.ReadyEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.slf4j.LoggerFactory;
import com.jagrosh.jdautilities.command.*;
import io.merdedspade.merdian.command.*;
import javax.security.auth.login.LoginException;

import static io.merdedspade.merdian.Launch.bot;


/*
Bot core here. Plz read README.md
 */



public class Merdian extends ListenerAdapter{
    public final com.jagrosh.jdautilities.command.Command.Category OWNER = new com.jagrosh.jdautilities.command.Command.Category("Owner");
    static ch.qos.logback.classic.Logger l =
            (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Merdian.class);
    static Config config = new Config();
    static Const consta = new Const();
    //Using debug


    public void bot() /*throws Exception*/ {
        if(config.isDebug == true){
            l.setLevel(Level.TRACE);
        } else{
            l.setLevel(Level.INFO);
        }

        try {
            l.info("Building CommandClient..");
            CommandClientBuilder builder = new CommandClientBuilder();
            // Set the bot's Owner ID±±
            builder.setOwnerId(config.owner);
            builder.setPrefix(config.prefix);
            if (config.dev_mode) {
                builder.setGame(Game.streaming("Dev mode", "https://twitch.tv/abroskin08"));
            }
            if (config.prod_mode) {
                builder.setGame(Game.streaming(consta.ver + " | " + config.prefix + "help", "https://twitch.tv/abroskin08"));
            } else {
                builder.setGame(Game.playing("Loading..."));
                builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
            }
            builder.addCommands(
                    new io.merdedspade.merdian.command.PingCmd(),
                    new io.merdedspade.merdian.command.ShutdownCmd(bot),
                    new io.merdedspade.merdian.command.InfoCmd(),
                    new io.merdedspade.merdian.command.HelpCmd(),
                    new io.merdedspade.merdian.command.fun.CoinflipCmd()
            );
            HelpCmd Help = new HelpCmd();
            builder.useHelpBuilder(true);
            builder.setHelpWord("dphelp");
            //builder.addCommands(new io.merdedspade.merdian.command.PingCmd(), new SecondCmd());
            CommandClient client = builder.build();
            l.info("Loading shards...");
            JDABuilder shardBuilder = new JDABuilder(AccountType.BOT).setToken(config.token).setGame(Game.listening("Loading..."));
            shardBuilder.setStatus(OnlineStatus.IDLE);
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
            shardBuilder.addEventListener(client);
            for (int i = 0; i < config.shards; i++)
            {
                l.info("Loading shard: " + i);
                shardBuilder.useSharding(i, config.shards)
                        .buildAsync();
                l.info("Done: " + i);
            }

            l.info("Done loading shards!");
            if (config.dev_mode) {
                builder.setGame(Game.streaming("Dev mode", "https://twitch.tv/abroskin08"));
            }
            if (config.prod_mode) {
                builder.setGame(Game.streaming(consta.ver + " | " + config.prefix + "help", "https://twitch.tv/abroskin08"));
            } else {
                builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
                builder.setGame(Game.watching("Loading error. Please, contact admin."));
                l.error("Please, set correct mode.");
                builder.setGame(Game.watching("Loading error. Please, contact admin."));
                Thread.sleep(999999);
                System.exit(1);
            }
        } catch (LoginException e) {
            //If auth error.
            e.printStackTrace();
            l.error("Wrong token!");
        } catch (InterruptedException e) {
            l.error("Bot sleep.");
        }


    }

    private JDA jda;

    public void shutdown() {
        jda.shutdown();
    }
    @Override
    public void onReady(ReadyEvent event){
        l.info("Bot ready!");


    }


    public void sendConsole(MessageReceivedEvent event) {

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


    }

}
