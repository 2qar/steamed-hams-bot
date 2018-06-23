package com.mycompany.steamedhamsbot;

import java.io.File;
//import java.io.FileNotFoundException;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.entities.Game;

import java.util.Scanner;
import java.util.ArrayList;
import javax.swing.SwingUtilities;
import net.dv8tion.jda.core.events.channel.priv.PrivateChannelCreateEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;

public class App extends ListenerAdapter
{
    static ArrayList<String> steamedHams;
    public static JDA jda;
    public static File[] bliniCat;
    
    public static void main(String[] args) throws Exception
    {
        jda = new JDABuilder(AccountType.BOT).setToken(Ref.TOKEN).buildBlocking();
        jda.addEventListener(new App());
        jda.getPresence().setGame(Game.of(Game.GameType.DEFAULT, "an unforgettable luncheon"));
        
        File hamFile = new File("src/test/java/SteamedHams.txt");
        
        steamedHams = new ArrayList<String>();
        
        Scanner reader = new Scanner(hamFile);
        
        while(reader.hasNextLine())
            steamedHams.add(reader.nextLine());
        
        hamsItalicizer();
        Commands.commandsInit();
        
        // get all of the pictures of the blini cat
        File bliniFolder = new File("src/main/java/com/mycompany/steamedhamsbot/blinipics");
        bliniCat = bliniFolder.listFiles();
        
        // open an app window
        AppWindow window = new AppWindow(jda);
        SwingUtilities.invokeLater(window);
        
        System.out.println("STEAMED HAMS IS ONLINE");
    }
    
    private static void hamsItalicizer()
    {
        for(int index = 0; index < steamedHams.size(); index++)
            if(steamedHams.get(index).startsWith("-"))
            {
                String formattedHam = "*" + steamedHams.get(index).substring(1) + "*";
                steamedHams.set(index, formattedHam);
            }
    }
    
    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        User userObj = event.getAuthor();
        MessageChannel channelObj = event.getChannel();
        Message messageObj = event.getMessage();
        String message = messageObj.getContentRaw();
        boolean notBot = !userObj.isBot();
        
        //if(userObj.getId().equals("205541764206034944"))
            //channelObj.sendMessage("robby smells").queue();
        
        // if a message has the command prefix, figure out which command to run
        if(message.startsWith(Ref.PREFIX) && notBot)
            Commands.runCommand(event);
        
        // if a message mentions steamed hams, add a steamed ham in the reactions
        if(contains(message, new String[] { "steamed hams", "steamedhams" })
                && notBot)
            messageObj.addReaction("🍔").queue();
    }
    
    @Override
    public void onPrivateChannelCreate(PrivateChannelCreateEvent event)
    {
        System.out.println(event + " : event recieved");
        //Commands.helpMessage(event);
        event.getChannel().close().complete();
    }
    
    
    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) 
    {
        if(!event.getAuthor().isBot())
            Commands.helpMessage(event);
    }
    
    private boolean contains(String message, String[] strings)
    {
        for(String string : strings)
            if(message.toLowerCase().contains(string))
                return true;
        return false;
    }

    private void hamsFormatter()
    {
        
    }
    
}
