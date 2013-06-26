package exp.restlet;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

/**
 * Example Restlet application configured to use JAX-RS. See {@link MyJAXRSApplication} for different server
 * initialization mechanism used for Restlet servers using JAX-RS annotations. See {@link MyServerJAXRSResource} for a
 * resource annotated using JAX-RS.
 */
public class MyJAXRSApplication extends Application
{

	@Override
	public Set<Class<?>> getClasses()
	{
		Set<Class<?>> rrcs = new HashSet<Class<?>>();
		rrcs.add(MyServerJAXRSResource.class);
		return rrcs;
	}
}