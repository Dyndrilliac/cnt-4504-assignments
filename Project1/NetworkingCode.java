/*
 * Title: CNT 4504 Project 1 - Core Networking
 * Author: Matthew Boyette
 * Date: 1/12/2014
 * 
 * This code implements the core networking functions needed by the client and server applications.
 */

package Project1;

import java.net.Socket;

import api.gui.swing.ApplicationWindow;
import api.util.networking.Sockets;

/*
 * These classes build upon my existing simple classes to fulfill the requirements for Project 1.
 */
public class NetworkingCode
{
    public static class SimpleClientThread extends Sockets.SimpleAbstractClientThread
    {
        private String  command      = null;
        private boolean isCompleted  = false;
        private boolean isFailed     = false;
        private long    timeFinished = 0;
        private long    timeStarted  = 0;
        private long    totalTime    = 0;
        
        public final String getCommand()
        {
            return this.command;
        }
        
        public final long getTimeFinished()
        {
            return this.timeFinished;
        }
        
        public final long getTimeStarted()
        {
            return this.timeStarted;
        }
        
        public final long getTotalTime()
        {
            return this.totalTime;
        }
        
        public final boolean isCompleted()
        {
            return this.isCompleted;
        }
        
        public final boolean isFailed()
        {
            return this.isFailed;
        }
        
        protected final void setCommand(final String command)
        {
            this.command = command;
        }
        
        protected final void setCompleted(final boolean isCompleted)
        {
            this.isCompleted = isCompleted;
        }
        
        protected final void setFailed(final boolean isFailed)
        {
            this.isFailed = isFailed;
        }
        
        protected final void setTimeFinished(final long timeFinished)
        {
            this.timeFinished = timeFinished;
        }
        
        protected final void setTimeStarted(final long timeStarted)
        {
            this.timeStarted = timeStarted;
        }
        
        protected final void setTotalTime(final long totalTime)
        {
            this.totalTime = totalTime;
        }
    }
    
    public final static class SimpleServer extends Sockets.SimpleAbstractServer
    {
        public SimpleServer(final int listeningPort)
        {
            super(listeningPort);
        }
        
        @Override
        protected void addThread(final Socket socket)
        {
            
        }
        
        @Override
        public void handle(final String identifier, final String input)
        {
            
        }
        
        @Override
        public void print(final Object... args)
        {
            
        }
    }
    
    public final static class SimpleServerThread extends Sockets.SimpleAbstractServerThread
    {
        public SimpleServerThread(final ApplicationWindow window, final SimpleServer server, final Socket socket)
        {
            super(window, server, socket);
        }
    }
}