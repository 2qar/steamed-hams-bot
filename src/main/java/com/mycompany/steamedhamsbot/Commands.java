package com.mycompany.steamedhamsbot;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import java.util.ArrayList;
import java.util.Random;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.channel.priv.PrivateChannelCreateEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;

/**
 *
 * @author Tucker
 */
public class Commands 
{
    private static ArrayList<Command> commands = new ArrayList<Command>();
    private static ArrayList<String> commandNames = new ArrayList<String>();
    
    public static void commandsInit()
    {
        addCommand("steamedhams", "Paste the entirity of steamed hams into the chat "
                + "because fuck my life man why did i waste my time making this piece of shit");
        addCommand("fattext \"insert text here\"", "Give your text some JUICE. Use with quotes.");
        addCommand("help", "uhhhhhhhhhhhhhhhhhhhhhhh");
        //addCommand("shutdown", false);
    }
    
    private static void addCommand(String name, String description)
    {
        Command command = new Command(name, description);
        commands.add(command);
    }
    
    private static void addCommand(String name, boolean show)
    {
        Command command = new Command(name, show);
        commands.add(command);
    }
    
    public static void runCommand(MessageReceivedEvent event)
    {
        String msg = event.getMessage().getContentRaw();
        MessageChannel channel = event.getChannel();
        
        String command = msg.split(" ")[0].substring(2);
        
        // holy shit this is the coolest thing ever
        // being able to call methods by just their string name saves me a
        // shit ton of lines of code
        try
        {
            Method commandMethod = 
                Commands.class.getDeclaredMethod(command, MessageReceivedEvent.class);
            commandMethod.invoke(Commands.class, event);
        }
        catch(Exception e)
        {
            System.out.println("ERROR: " + e);
            if(e instanceof NoSuchMethodException)
                channel.sendMessage("'" + command + "' is not a command. "
                        + "DM me \"help\" for a list of commands.").queue();
        }
    }
    
    private static void blini(MessageReceivedEvent event)
    {
        MessageChannel channel = event.getChannel();
        
        Random rand = new Random();
        int pic = rand.nextInt(App.bliniCat.length);
        
        channel.sendFile(App.bliniCat[pic]).queue();
    }
    
    private static void steamedhams(MessageReceivedEvent event)
    {
        MessageChannel channel = event.getChannel();
        
        for(String line : App.steamedHams)
        {
            MessageBuilder builder = new MessageBuilder();
            builder.append("\n" + line);
            channel.sendMessage(builder.build()).queue();
        }
    }
    
    // TODO: Finish this method; it'll allow the bot to send the script w/o spam
    /*
    private static void steamedhams(MessageChannel channel)
    {
        MessageBuilder builder = new MessageBuilder();
        builder.append("```\n");
        
        for(int index = 0; index < steamedHams.size(); index++)
        {
            String line = steamedHams.get(index);
            int lineLength = line.length();
            
            // if the current message is gonna go over the character limit,
            // send it and start a new one
            if(builder.length() + lineLength >= 1950)
            {
                builder.append("```");
                Message hamSlice = builder.build();
                channel.sendMessage(hamSlice).queue();
                builder = new MessageBuilder();
                builder.append("```\n" + line);
            }
            else
                builder.append(line + "\n");
        }
        
    }
    */
    
    private static void test(MessageReceivedEvent event)
    {
        String test = "This is a test.";
        //channel.sendMessage("T").queue();
    }
    
    private static void fattext(MessageReceivedEvent event)
    {
        String msg = event.getMessage().getContentRaw();
        MessageChannel channel = event.getChannel();
        
        String formattedMsg = msg.substring(msg.indexOf("\"") + 1, msg.lastIndexOf("\""));
        
        String fatMessage = makeTextFat(formattedMsg.toLowerCase());
            
        channel.sendMessage(fatMessage).queue();
    }
    
    private static String makeTextFat(String text)
    {
        String fatMessage = "";
        
        for(int index = 0; index < text.length(); index++)
        {
            if(!(text.charAt(index) == ' '))
                fatMessage += ":regional_indicator_" + text.charAt(index) + ":";
            
            // add two spaces between words and 1 between letters
            if(index != text.length() - 1)
                if(text.charAt(index + 1) == ' ')
                    fatMessage += "  ";
                else
                    fatMessage += " ";
        }
        
        return fatMessage;
    }
    
    /*
    private static void help(MessageReceivedEvent event)
    {
        event.getAuthor().openPrivateChannel().complete();
        event.getChannel().sendMessage("Help message sent.").queue();
    }
    */

    public static void helpMessage(PrivateMessageReceivedEvent event)
    {
        MessageBuilder builder = new MessageBuilder();
        builder.append("```markdown\n");
        builder.append("#Commands\n\n");
        
        for(int index = 0; index < commands.size(); index++)
        {
            Command command = commands.get(index);
            builder.append(Ref.PREFIX + command.getName() + "\n");
            builder.append("#" + command.getDescription());
            if(index != commands.size() - 1)
                builder.append("\n\n");
            else
                builder.append("\n");
        }
        
        builder.append("```");
        event.getChannel().sendMessage(builder.build()).queue();
        event.getChannel().close().submit();
    }
    
    private static void shutdown(MessageReceivedEvent event)
    {
        if(event.getAuthor().getId().equals("203284058673774592"))
        {
            App.jda.shutdown();
            System.exit(0);
        }
    }
    
    private static void stop(MessageReceivedEvent event)
    {
        
    }
    
}
