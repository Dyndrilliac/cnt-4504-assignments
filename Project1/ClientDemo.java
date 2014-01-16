import api.util.Networking;

public class ClientDemo
{
	private static Networking.SimpleClientThread myThread = null;
	
	// Client application entry-point.
	public static void main(String[] args)
	{
		myThread = new Networking.KnockKnockJokeExample.KnockKnockClientThread("localhost", 15000);
		myThread.start();

		while (myThread.isConnected())
		{
			continue;
		}

		myThread = null;
	}
}