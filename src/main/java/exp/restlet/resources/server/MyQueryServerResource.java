package exp.restlet.resources.server;

import org.restlet.Client;
import org.restlet.data.CharacterSet;
import org.restlet.data.Protocol;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exp.restlet.resources.MyQueryResource;

/**
 * Simple resource that accepts a query and prints it out.
 * <p>
 * Also sends request as client.
 * 
 * <pre>
 * curl -v -X GET "http://localhost:8111/query?f=f&g=5"
 * </pre>
 * 
 * @author martin
 */
public class MyQueryServerResource extends ServerResource implements MyQueryResource
{
	private final Logger logger = LoggerFactory.getLogger(MyQueryServerResource.class);

	@Override
	public void query()
	{
		String queryString = getQuery().getQueryString(CharacterSet.UTF_8);

		logger.info("queryString: " + queryString);

		Client client = new Client(getContext(), Protocol.HTTP);
		ClientResource clientResource = new ClientResource("http://restlet.org");
		clientResource.setNext(client);
		logger.info("" + clientResource.get());
	}
}
