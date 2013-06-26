package exp.restlet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

/**
 * Example of JAX-RS annotated resource. Restlet applications that use JAX-RS annotations are configured differently.
 * See {@link EPadJaxRSServer}.
 */
@Path("apath")
public class MyServerJAXRSResource extends ServerResource
{
	public MyServerJAXRSResource()
	{
		setNegotiated(false); // Disable content negotiation
		// setExisting(false); // Does the resource exist? Will return a 404 if not found.
	}

	@Override
	protected void doInit() throws ResourceException
	{
		System.out.println("The root resource (JAX-RS) was initialized.");
	}

	@Override
	protected void doCatch(Throwable throwable)
	{
		System.out.println("An exception was thrown in the root (JAX-RS) resource.\n");
	}

	@Override
	protected void doRelease() throws ResourceException
	{
		System.out.println("The root resource (JAX-RS) was released.\n");
	}

	@GET
	public String represent()
	{
		return "This is the root resource (JAX-RS annotation)!\n";
	}
}
