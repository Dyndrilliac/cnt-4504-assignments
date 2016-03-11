/*
 * Title: CNT 4504 Project 1 - Client Demo
 * Author: Matthew Boyette
 * Date: 1/12/2014
 * 
 * This code implements the client application.
 */

package Project1;

import api.gui.swing.ApplicationWindow;
import api.util.Support;

public final class ClientDemo
{
    public final static void main(final String[] args)
    {
        new ClientDemo();
    }
    
    private ClientThread[]    clients     = null;
    private boolean           isDebugging = false;
    private ApplicationWindow window      = null;
    
    public ClientDemo()
    {
        final int numClients = Support.getIntegerInputString(this.getWindow(), "How many clients?", "Load");
        final int remotePort = Support.getIntegerInputString(this.getWindow(), "Which remote port?", "Port");
        final String remoteHost = Support.getInputString(this.getWindow(), "Which remote host?", "Host");
        final String command = Support.getInputString(this.getWindow(), "What command to send?", "Command");
        
        ClientThread[] clients = new ClientThread[numClients];
        
        for (int i = 0; i < numClients; i++)
        {
            clients[i] = new ClientThread(this.getWindow(), remoteHost, remotePort, command);
        }
        
        this.setClients(clients);
    }
    
    public final ClientThread[] getClients()
    {
        return this.clients;
    }
    
    public final ApplicationWindow getWindow()
    {
        return this.window;
    }
    
    public final boolean isDebugging()
    {
        return this.isDebugging;
    }
    
    protected final void setClients(final ClientThread[] clients)
    {
        this.clients = clients;
    }
    
    protected final void setDebugging(final boolean isDebugging)
    {
        this.isDebugging = isDebugging;
    }
    
    protected final void setWindow(final ApplicationWindow window)
    {
        this.window = window;
    }
}