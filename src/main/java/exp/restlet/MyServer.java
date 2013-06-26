package exp.restlet;

import org.restlet.Server;
import org.restlet.data.Protocol;

/**
 * Very simple, minimal way of creating a Restlet application.
 * <p>
 * Simple test: curl -v http://localhost:8111/ -X GET
 * <p>
 * See {@link MyApplication} for more elaborate starting procedure.
 */
public class MyServer
{
	public static void main(String[] args) throws Exception
	{
		final int port = 8111;
		Server myServer = new Server(Protocol.HTTP, port);
		myServer.start();
		System.out.println("MyServer started on port " + port);
	}
}
