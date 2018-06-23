/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.steamedhamsbot;

import javax.swing.*;
import java.awt.FlowLayout;
import net.dv8tion.jda.core.JDA;

/**
 *
 * @author Tucker
 */
public class AppWindow implements Runnable
{
    private final JDA bot;
    
    public AppWindow(JDA bot)
    {
        this.bot = bot;
    }
    
    @Override
    public void run()
    {
        JFrame window = new JFrame("test");
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        window.setLayout(new FlowLayout());
        window.add(new JLabel("ogdog"));
        window.add(new JButton("BUTTON"));
        window.add(new JTextField("ogdog"));
        
        window.pack();
        window.setVisible(true);
    }
}
