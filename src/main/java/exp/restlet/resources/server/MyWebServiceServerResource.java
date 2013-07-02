package exp.restlet.resources.server;

import org.restlet.data.Status;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exp.restlet.resources.MyWebServiceResource;

/**
 * Resource representing an EPad Web Server.
 * <p>
 * To test:
 * 
 * <pre>
 * curl -v -X POST "http://localhost:8111/server/shutdown"
 * 
 * <pre>
 * 
 * @author martin
 */
public class MyWebServiceServerResource extends ServerResource implements MyWebServiceResource
{
	private final Logger logger = LoggerFactory.getLogger(MyWebServiceServerResource.class);

	private static final String SERVER_SHUTDOWN_MESSAGE = "Server shut down!";
	private static final String MALFORMED_REQUEST_MESSAGE = "Malformed request!";
	private static final String UNKNOWN_OPERATION_MESSAGE = "Bad request - unknown operation: ";

	public MyWebServiceServerResource()
	{
		setNegotiated(false); // Disable content negotiation
	}

	@Override
	protected void doInit() throws ResourceException
	{
		logger.info("The server resource was initialized.");
	}

	@Override
	protected void doCatch(Throwable throwable)
	{
		logger.warn("An exception was thrown in the server resource.\n");
	}

	@Override
	protected void doRelease() throws ResourceException
	{
		logger.info("The server resource was released.\n");
	}

	@Override
	@Post
	public String operation()
	{
		if (!getRequestAttributes().containsKey(TEMPLATE_OPERATION_NAME)) {
			setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return MALFORMED_REQUEST_MESSAGE;
		} else {
			String operationName = getAttribute(TEMPLATE_OPERATION_NAME);

			if (ServerOperation.STATUS.hasOperationName(operationName)) {
				return "Status";
			} else if (ServerOperation.SHUTDOWN.hasOperationName(operationName)) {
				setStatus(Status.SUCCESS_OK);
				return SERVER_SHUTDOWN_MESSAGE;
			} else {
				setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
				return UNKNOWN_OPERATION_MESSAGE + operationName;
			}
		}
	}
}