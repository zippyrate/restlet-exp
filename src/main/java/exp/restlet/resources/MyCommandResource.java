package exp.restlet.resources;

import org.restlet.resource.Post;

/**
 * Resource with a command passed as a query parameter. Not very RESTful.
 * 
 * @author martin
 */
public interface MyCommandResource
{
	public final String QUERY_COMMAND_NAME = "cmd";

	@Post
	public String command();

	enum WindowLevelCommand {
		CREATE("create"), QUERY("query");

		private final String commandName;

		WindowLevelCommand(String commandName)
		{
			this.commandName = commandName;
		}

		public String getCommandName()
		{
			return commandName;
		}

		public boolean hasCommandName(String otherName)
		{
			return (otherName == null) ? false : commandName.equalsIgnoreCase(otherName);
		}
	}
}