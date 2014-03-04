/*
	Title: CNT 4504 Project 1 - Core
	Author: Matthew Boyette
	Date: 1/12/2014
	
	This code implements the core networking functions needed by the client and server applications.
*/
package CNT4504.Project1Code;

import api.gui.*;
import api.util.*;

import java.net.Socket;

/*
	This class builds upon my existing classes to fulfill the requirement for Project 1.
*/
public class CNT4504Project1Code
{
	public static class CNT4504Project1ChildServerThread extends Networking.SimpleChildServerThread
	{
		public CNT4504Project1ChildServerThread(final Networking.SimpleServerThread parent, final Socket socket, final ApplicationWindow window)
		{
			super(parent, socket, window);
		}
		
		@Override
		public void run()
		{
			try
			{
				// TODO
			}
			catch (final Exception e)
			{
				Support.displayException(this.getWindow(), e, false);
			}
			finally
			{
				this.close();
			}
		}
	}
	
	public static class CNT4504Project1ClientThread extends Networking.SimpleClientThread
	{
		public CNT4504Project1ClientThread(final String remoteHost, final int remotePort, final ApplicationWindow window)
		{
			super(remoteHost, remotePort, window);
		}
		
		@Override
		public void run()
		{
			try
			{
				// TODO
			}
			catch (final Exception e)
			{
				Support.displayException(this.getWindow(), e, false);
			}
			finally
			{
				this.close();
			}
		}
	}
	
	public static class CNT4504Project1Protocol implements Networking.SimpleProtocol
	{
		@Override
		public String processInput(final String input)
		{
			String output = null;
			
			// TODO
			
			return output;
		}
	}
	
	public static class CNT4504Project1ServerThread extends Networking.SimpleServerThread
	{
		public CNT4504Project1ServerThread(final int listeningPort, final ApplicationWindow window)
		{
			super(listeningPort, window);
		}
		
		@Override
		public void run()
		{
			while ((this.isListening()) && (this.getListeningSocket() != null))
			{
				try
				{
					Networking.SimpleChildServerThread newThread = new CNT4504Project1ChildServerThread(this, this.getListeningSocket().accept(), this.getWindow());
					newThread.start();
					this.getClientList().add(newThread);
				}
				catch (final Exception e)
				{
					Support.displayException(this.getWindow(), e, false);
				}
			}
			
			this.close();
		}
	}
}