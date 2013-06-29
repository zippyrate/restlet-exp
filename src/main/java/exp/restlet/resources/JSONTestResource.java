package exp.restlet.resources;

import org.restlet.representation.Representation;
import org.restlet.resource.Post;

public interface JSONTestResource
{
	@Post("json")
	public Representation acceptAndReturnJSON(Representation entity);
}
