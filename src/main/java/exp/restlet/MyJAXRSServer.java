package exp.restlet;

import org.restlet.Component;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.ext.jaxrs.JaxRsApplication;

/**
 * Approach to starting up a Restlet server that uses JAX-RS annotations. See {@link MyJAXRSApplication} and
 * {@link MyServerJAXRSResource}.
 */
public class MyJAXRSServer
{
	public static void main(String[] args) throws Exception
	{
		Component component = new Component();
		Server server = component.getServers().add(Protocol.HTTP, 8111);

		// Create JAX-RS runtime environment
		JaxRsApplication application = new JaxRsApplication(component.getContext());

		// Attach Application
		application.add(new MyJAXRSApplication());

		// Attach the application to the component and start it
		component.getDefaultHost().attach(application);
		component.start();

		System.out.println("JAX-RS server started on port " + server.getPort());
	}
}