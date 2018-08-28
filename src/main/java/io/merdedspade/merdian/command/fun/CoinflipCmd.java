package io.merdedspade.merdian.command.fun;

import com.jagrosh.jdautilities.command.*;
import net.dv8tion.jda.client.entities.Group;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.*;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.awt.Color;

public class CoinflipCmd extends Command {
    Random random = new Random();
    int botnum = random.nextInt(1);
    int numd; //user input number
    static ch.qos.logback.classic.Logger l =
            (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(CoinflipCmd.class);

    public CoinflipCmd() {
        this.name = "numflip";
        this.aliases = new String[]{"coin", "cf", "nf", "coinflip"};
        this.help = "Number Flip.";
        this.arguments = "<0, 1>";
    }

    protected MessageEmbed ebs() {

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Fun | Number Flip", null);
        eb.setColor(Color.green);
        if (numd == 1 && botnum == numd) {
            eb.setDescription("Your choice: " + numd + "." + " Bot choice: " + botnum + ". You win!");
        } else if (numd == 0 && botnum == numd) {
            eb.setDescription("Your choice: " + numd + "." + " Bot choice: " + botnum + ". You win!");
        } else {
            eb.setDescription("Your choice: " + numd + "." + " Bot choice: " + botnum + ". You lose!");
        }

        return eb.build();
    }

    @Override
    protected void execute(CommandEvent event) {
        try {
            numd = Integer.parseInt(event.getArgs());
        } catch (NumberFormatException e) {
            event.reply("Error! Please write number. Example: `[prefix]coinflip 0`");
        }
        if (numd != 1 || numd != 0) {
            event.reply("Error! Please write 0 or 1.");
        } else {
            event.reply(ebs());
            JDA jda = event.getJDA();                       //JDA
            long responseNumber = event.getResponseNumber();//The amount of discord events that JDA has received since the last reconnect.


            User author = event.getAuthor();                //The user that sent the message
            Message message = event.getMessage();           //The message that was received.
            MessageChannel channel = event.getChannel();    //This is the MessageChannel that the message was sent to.
            //  This could be a TextChannel, PrivateChannel, or Group!

            String msg = message.getContentDisplay();              //This returns a human readable version of the Message. Similar to
            // what you would see in the client.

            boolean bota = author.isBot();                    //This boolean is useful to determine if the User that
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

}
