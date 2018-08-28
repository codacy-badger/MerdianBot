package io.merdedspade.merdian.command;

import java.awt.Color;

import net.dv8tion.jda.client.entities.Group;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.*;
import com.jagrosh.jdautilities.command.*;
import com.jagrosh.jdautilities.command.CommandEvent;
import org.slf4j.LoggerFactory;


public class HelpCmd extends Command {
    public HelpCmd() {
        this.name = "help";
        this.aliases = new String[]{"cmds", "commands", "h"};
        this.help = "Send help.";
    }

    static ch.qos.logback.classic.Logger l =
            (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(HelpCmd.class);

    protected MessageEmbed ebs() {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Core | Help", null);
        eb.setColor(Color.blue);
        eb.setDescription("Commands");
        //eb.addField("Shard info: ",  getShardString(), false);
        eb.addField("Core", "`info` - show bot info \n`ping` - check bot status \n`help` - bot help", false);
        eb.addField("Fun", "`numflip` *[number]* - number flip! \n", false);
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
