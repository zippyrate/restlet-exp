package exp.restlet;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		Server server = new Server(Protocol.HTTP, 8111);

		server.setNext(new MyApplication());
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

		Router router = new Router(getContext());

		/*
		 * router.attach("http://localhost:8111/", tracer);
		 * 
		 * router.attach("http://localhost:8111/accounts/", tracer);
		 * 
		 * router.attach("http://localhost:8111/accounts/{accountId}", blocker);
		 */

		// This works when we are creating application via web.xml and mvn jetty:run
		router.attach("/", MyServerResource.class); // A new resource instance is created for each request.
		// router.attach("/", tracer);

		// This works if we directly create application in main above. New resource instance created for each request.
		router.attach("http://localhost:8111/", MyServerResource.class);
		// router.attach("http://localhost:8111/", tracer);

		return router;
	}
}