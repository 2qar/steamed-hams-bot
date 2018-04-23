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
import java.util.Scanner;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import net.dv8tion.jda.core.MessageBuilder;


// TODO: Add an alternate function that DMs users steamed hams to the user instead of just pasting it into the text channel that the command is called into
public class App extends ListenerAdapter
{
    static ArrayList<String> steamedHams;
    
    public static void main(String[] args) throws Exception
    {
        JDA jda = new JDABuilder(AccountType.BOT).setToken(Ref.TOKEN).buildBlocking();
        jda.addEventListener(new App());
        
        File hamFile = new File("src/test/java/SteamedHams.txt");
        System.out.println("Steamed Hams script available? " + hamFile.exists());
        
        steamedHams = new ArrayList<String>();
        
        Scanner reader = new Scanner(hamFile);
        
        while(reader.hasNextLine())
            steamedHams.add(reader.nextLine());
        
        hamsItalicizer();
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
        //User userObj = event.getAuthor();
        MessageChannel channelObj = event.getChannel();
        Message messageObj = event.getMessage();
        
        if(messageObj.getContentRaw().startsWith(Ref.PREFIX + "steamedhams"))
        {
            sendSteamedHams(channelObj);
        }
    }
    
    private void sendSteamedHams(MessageChannel channel)
    {
        for(String line : steamedHams)
        {
            MessageBuilder builder = new MessageBuilder();
            builder.append("\n" + line);
            channel.sendMessage(builder.build()).queue();
        }
            
    }
    
    /*
    private void sendSteamedHams(MessageChannel channel)
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
        
        //for(String line : steamedHams)
            //channel.sendMessage(line).complete();
    }
    */    

    private void hamsFormatter()
    {
        
    }
    
}
