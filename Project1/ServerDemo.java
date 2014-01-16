import api.util.Networking;

public class ServerDemo
{
	private static Networking.SimpleServerThread myThread = null;
	
	// Server application entry-point.
	public static void main(String[] args)
	{
		myThread = new Networking.KnockKnockJokeExample.KnockKnockServerThread(15000);
		myThread.start();
		
		while (myThread.isListening())
		{
			continue;
		}
		
		myThread = null;
	}
}