package exp.restlet;

import java.io.IOException;

import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Options;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Example Restlet server resource. The {@link MyResource} interface can be used on both the client and server side.
 * <p>
 * Demonstrated both direct resource method definitions and annotated definitions.
 * <p>
 * Creation and deletion life cycle of resources can be observed.
 */
public class MyServerResource extends ServerResource implements MyResource
{
	private final Logger logger = LoggerFactory.getLogger(MyApplication.class);

	public MyServerResource()
	{
		setNegotiated(false); // Disable content negotiation
		// setExisting(false); // Does the resource exist? Will return a 404 if not found.
	}

	@Override
	protected void doInit() throws ResourceException
	{
		logger.info("The resource was initialized.");
	}

	@Override
	protected void doCatch(Throwable throwable)
	{
		logger.warn("An exception was thrown in the resource.\n");
	}

	@Override
	protected void doRelease() throws ResourceException
	{
		logger.info("The resource was released.\n");
	}

	@Override
	@Get("txt")
	public String represent()
	{
		return "This is the resource (Restlet @Get annotation)!\n";
	}

	@Override
	@Options("txt")
	public String describe()
	{
		return "This is the resource (Restlet @Options annotation)!\n";
	}

	@Override
	// The annotated GET does not seem to override this.
	protected Representation get() throws ResourceException
	{
		logger.info("The GET method of resource was invoked (annotationless).");

		String path = getReference().getPath();
		logger.info("Path: " + path);

		String qv = getQueryValue("fred");
		logger.info("Fred value: " + qv);

		return new StringRepresentation("This is the resource.\n");
	}

	@Override
	protected Representation delete() throws ResourceException
	{
		logger.info("The DELETE method of root resource was invoked.");
		return new StringRepresentation("Delete the root resource.\n");
	}

	@Override
	protected Representation post(Representation representation) throws ResourceException
	{
		return new StringRepresentation("You posted: " + representation + "\n");
	}

	@Override
	protected Representation put(Representation representation) throws ResourceException
	{
		try {
			return new StringRepresentation("You put: " + representation.getText() + "\n");
		} catch (IOException e) {
			String error = "IO exception inspecting resource: " + e.getMessage();
			logger.error(error);
			return new StringRepresentation(error);
		}
	}

	@Override
	protected Representation options() throws ResourceException
	{
		logger.info("The OPTIONS method of root resource was invoked.\n");
		return new StringRepresentation("You have no options!\n");
	}
}
