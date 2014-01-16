import api.util.Networking;

import java.io.IOException;
import java.net.Socket;

/*
	This class builds upon my existing classes to fulfill the requirement for Project 1.
*/
public class CNT4504Project1Code
{
	public static class CNT4504Project1Application implements Runnable
	{
		public void run()
		{
			// TODO
		}
	}

	public static class CNT4504Project1ClientThread extends Networking.SimpleClientThread
	{
		public CNT4504Project1ClientThread(final String remoteHost, final int remotePort)
		{
			super(remoteHost, remotePort);
		}

		public void run()
		{
			/*try
				{
					// TODO
				}
				catch (final SocketException e)
				{
					System.err.println("Socket exception encountered while connected to server: " + e.toString());
					e.printStackTrace();
				}
				catch (final IOException e)
				{
					System.err.println("I/O exception encountered while connected to server: " + e.toString());
					e.printStackTrace();
				}
				finally
				{
					this.close();
				}*/
		}
	}

	public static class CNT4504Project1ServerThread extends Networking.SimpleServerThread
	{
		public CNT4504Project1ServerThread(final int listeningPort)
		{
			super(listeningPort);
		}

		public void run()
		{
			while ((this.isListening()) && (this.listeningSocket != null))
			{
				try
				{
					Networking.SimpleChildServerThread newThread = new CNT4504Project1ChildServerThread(this, this.listeningSocket.accept());
					newThread.start();
					this.getClientList().add(newThread);
				}
				catch (final IOException e)
				{
					System.err.println("I/O exception encountered while listening for clients: " + e.toString());
					e.printStackTrace();
				}
			}

			this.close();
		}
	}

	public static class CNT4504Project1ChildServerThread extends Networking.SimpleChildServerThread
	{
		public CNT4504Project1ChildServerThread(final Networking.SimpleServerThread parent, final Socket socket)
		{
			super(parent, socket);
		}

		public void run()
		{
			/*try
				{
					// TODO
				}
				catch (final SocketException e)
				{
					System.err.println("Socket exception encountered while connected to client: " + e.toString());
					e.printStackTrace();
				}
				catch (final IOException e)
				{
					System.err.println("I/O exception encountered while connected to client: " + e.toString());
					e.printStackTrace();
				}
				finally
				{
					this.close();
				}*/
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