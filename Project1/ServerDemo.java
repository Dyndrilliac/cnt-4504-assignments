/*
 * Title: CNT 4504 Project 1 - Server Demo
 * Author: Matthew Boyette
 * Date: 1/12/2014
 * 
 * This code implements the server application.
 */

package Project1;

import api.gui.swing.ApplicationWindow;
import api.util.Support;

public final class ServerDemo
{
    public final static void main(final String[] args)
    {
        new ServerDemo();
    }
    
    private boolean           isDebugging = false;
    private ServerThread      server      = null;
    private ApplicationWindow window      = null;
    
    public ServerDemo()
    {
        final int listeningPort = Support.getIntegerInputString(null, "Which port to use for listening?", "Port");
        this.setServer(new ServerThread(this.getWindow(), listeningPort));
    }
    
    public final ServerThread getServer()
    {
        return this.server;
    }
    
    public final ApplicationWindow getWindow()
    {
        return this.window;
    }
    
    public final boolean isDebugging()
    {
        return this.isDebugging;
    }
    
    protected final void setDebugging(final boolean isDebugging)
    {
        this.isDebugging = isDebugging;
    }
    
    protected final void setServer(final ServerThread server)
    {
        this.server = server;
    }
    
    protected final void setWindow(final ApplicationWindow window)
    {
        this.window = window;
    }
}