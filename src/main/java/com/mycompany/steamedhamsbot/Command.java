/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.steamedhamsbot;

/**
 *
 * @author Tucker
 */
public class Command 
{
    private String name = "";
    public String getName() { return name; }
    
    private String description = "";
    public String getDescription() { return description; }
    
    private boolean showOnHelp = true;
    public boolean getShowOnHelp() { return showOnHelp; }
    
    public Command()
    {
        // do nothing ahha
    }
    
    public Command(String name)
    {
        this.name = name;
    }
    
    public Command(String name, boolean showOnHelp)
    {
        this.name = name;
        this.showOnHelp = showOnHelp;
    }
    
    public Command(String name, String description)
    {
        this.name = name;
        this.description = description;
    }
    
    @Override
    public String toString()
    {
        return name;
    }
}
