package io.merdedspade.merdian.command;

import io.merdedspade.merdian.Const;
import net.dv8tion.jda.client.entities.Group;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import com.jagrosh.jdautilities.command.*;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class InfoCmd extends Command {
    protected Const info = new Const();
    static ch.qos.logback.classic.Logger l =
            (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(InfoCmd.class);

    public InfoCmd() {
        this.name = "info";
        this.aliases = new String[]{"status", "i"};
        this.help = "Send bot info.";
    }
 /*   public static String getShardString(JDA.ShardInfo shardInfo) {
        int shardId = shardInfo.getShardId();
        int shardCount = shardInfo.getShardTotal();
        String test = shardInfo.getShardString();
        return "Your Shard ID: " + shardId + "\n" + "Total shards: " + "\n"+ shardCount + "test: " + test;
    }*/

    public MessageEmbed ebs() {

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Core | Info.", null);
        eb.setColor(Color.pink);
        eb.setDescription("All Merdian Info");
        //eb.addField("Shard info: ",  getShardString(), false);
        eb.addField("Bot: ", "Version: " + info.ver, false);
        return eb.build();

    }

    protected void execute(CommandEvent event) {
        event.reply(ebs());

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


            l.info("New message on server! \n" + "\n Guild name: " + guild.getName() + "\n Channel: " + textChannel.getName() + "\n Author ID: " + author.getId() + "\n Message: " + msg);
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
