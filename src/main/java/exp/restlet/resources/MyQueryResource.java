package exp.restlet.resources;

import org.restlet.resource.Get;

public interface MyQueryResource
{
	@Get
	public void query();
}
