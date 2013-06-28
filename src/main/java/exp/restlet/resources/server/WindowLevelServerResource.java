package exp.restlet.resources.server;

import org.restlet.data.Status;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exp.restlet.resources.WindowLevelResource;

/**
 * To test:
 * 
 * <pre>
 * curl -v -X POST "http://localhost:8111/level?cmd=create"
 * </pre>
 * 
 * @author martin
 */
public class WindowLevelServerResource extends ServerResource implements WindowLevelResource
{
	private final Logger logger = LoggerFactory.getLogger(WindowLevelServerResource.class);

	private static final String MALFORMED_REQUEST_MESSAGE = "Malformed request!";
	private static final String UNKNOWN_COMMAND_MESSAGE = "Bad request - unknown command: ";
	private static final String EMPTY_COMMAND_MESSAGE = "Bad request - no command";

	public WindowLevelServerResource()
	{
		setNegotiated(false); // Disable content negotiation
	}

	@Override
	protected void doCatch(Throwable throwable)
	{
		logger.warn("An exception was thrown in the server resource.\n");
	}

	@Override
	@Post
	public String command()
	{
		if (getQueryValue(QUERY_COMMAND_NAME) == null) {
			setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
			return MALFORMED_REQUEST_MESSAGE;
		} else {
			String commandName = getQueryValue(QUERY_COMMAND_NAME);

			if (WindowLevelCommand.CREATE.hasCommandName(commandName)) {
				return "Create\n";
			} else if (WindowLevelCommand.QUERY.hasCommandName(commandName)) {
				setStatus(Status.SUCCESS_OK);
				return "query: " + getQuery().getQueryString();
			} else {
				setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
				if (commandName == null)
					return EMPTY_COMMAND_MESSAGE;
				else
					return UNKNOWN_COMMAND_MESSAGE + commandName;
			}
		}
	}
}