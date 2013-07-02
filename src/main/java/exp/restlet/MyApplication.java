package exp.restlet;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exp.restlet.resources.server.JSONTestServerResource;
import exp.restlet.resources.server.MyCommandServerResource;
import exp.restlet.resources.server.MyQueryServerResource;
import exp.restlet.resources.server.MyWebServiceServerResource;

/**
 * Creation of simple Restlet service.
 * <p>
 * {@link #createInboundRoot()} is called when the application is invoked by the server. This method can be used to
 * create a router that defines attachment of resources to routes.
 * <p>
 * Application may be started using {@link #main(String[])} below or via Maven:
 * 
 * <pre>
 * mvn exec:exec -Djetty.port=8111
 * </pre>
 * 
 * If org.restlet.ext.jetty and an assemblage of Jetty JARS are in the class path, Jetty should be used as the server.
 * <p>
 * It may also be run in a servlet container configured via web.xml. See ./src/main/webapp/WEB-INF/web.xml for example
 * servlet container creation using Jetty. Can be executed via Maven as follows:
 * 
 * <pre>
 * mvn jetty:run
 * </pre>
 * 
 * Example client invocation:
 * 
 * <pre>
 * curl -v http://localhost:8111/ -X GET
 * </pre>
 */
public class MyApplication extends Application
{
	private static final Logger logger = LoggerFactory.getLogger(MyApplication.class);

	public static void main(String[] args) throws Exception
	{
		Component component = new Component();

		Server server = new Server(Protocol.HTTP, 8111, component);

		component.getDefaultHost().attach(new MyApplication());

		server.start();

		logger.info("Server started on port 8111!");
	}

	public MyApplication()
	{
		logger.info("******************Restlet Application Started **********************");
		setName("TestRestfulServer");
		setDescription("Test Restful Server");
		setOwner("Me");
		setAuthor("Me");
	}

	@Override
	public Restlet createInboundRoot()
	{
		MyTracer tracer = new MyTracer(getContext());
		MyIPBlocker blocker = new MyIPBlocker(getContext());
		// blocker.getBlockedAddresses().add("0.0.0.0");
		blocker.setNext(tracer);

		logger.info("****************** createInboundRoot **********************");
		logger.info("context: " + getContext());

		Router router = new Router(getContext());

		/*
		 * router.attach("/", tracer); router.attach("/accounts/", tracer); router.attach("/accounts/{accountId}", blocker);
		 */

		router.attach("/server/{operation}", MyWebServiceServerResource.class);
		router.attach("/level", MyCommandServerResource.class);
		router.attach("/json", JSONTestServerResource.class);
		router.attach("/query", MyQueryServerResource.class);

		return router;
	}
}