import api.gui.*;
import api.util.*;

import java.net.Socket;

/*
	This class builds upon my existing classes to fulfill the requirement for Project 1.
*/
public class CNT4504Project1Code
{	
	public static class CNT4504Project1ClientThread extends Networking.SimpleClientThread
	{
		public CNT4504Project1ClientThread(final ApplicationWindow window, final String remoteHost, final int remotePort)
		{
			super(window, remoteHost, remotePort);
		}

		public void run()
		{
			try
			{
				// TODO
			}
			catch (final Exception e)
			{
				Support.displayException(this.window, e, false);
			}
			finally
			{
				this.close();
			}
		}
	}

	public static class CNT4504Project1ServerThread extends Networking.SimpleServerThread
	{
		public CNT4504Project1ServerThread(final ApplicationWindow window, final int listeningPort)
		{
			super(window, listeningPort);
		}

		public void run()
		{
			while ((this.isListening()) && (this.listeningSocket != null))
			{
				try
				{
					Networking.SimpleChildServerThread newThread = new CNT4504Project1ChildServerThread(this.window, this, this.listeningSocket.accept());
					newThread.start();
					this.getClientList().add(newThread);
				}
				catch (final Exception e)
				{
					Support.displayException(this.window, e, false);
				}
			}

			this.close();
		}
	}

	public static class CNT4504Project1ChildServerThread extends Networking.SimpleChildServerThread
	{
		public CNT4504Project1ChildServerThread(final ApplicationWindow window, final Networking.SimpleServerThread parent, final Socket socket)
		{
			super(window, parent, socket);
		}

		public void run()
		{
			try
			{
				// TODO
			}
			catch (final Exception e)
			{
				Support.displayException(this.window, e, false);
			}
			finally
			{
				this.close();
			}
		}
	}

	public static class CNT4504Project1Protocol implements Networking.SimpleProtocol
	{
		public String processInput(final String input)
		{
			String output = null;

			// TODO

			return output; 
		}
	}
}