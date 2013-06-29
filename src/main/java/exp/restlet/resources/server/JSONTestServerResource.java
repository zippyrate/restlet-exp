package exp.restlet.resources.server;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.data.Status;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exp.restlet.resources.JSONTestResource;

/**
 * To test:
 * 
 * <pre>
 * curl -X post -H "Content-Type: application/json" http://localhost:8111/json \\ 
 *   -d @/Users/martin/workspace/exp/restlet-exp/src/main/resources/test.json
 * </pre>
 * 
 * @author martin
 */
public class JSONTestServerResource extends ServerResource implements JSONTestResource
{
	private final Logger logger = LoggerFactory.getLogger(JSONTestServerResource.class);

	public JSONTestServerResource()
	{
		setNegotiated(false); // Disable content negotiation
	}

	@Override
	protected void doCatch(Throwable throwable)
	{
		logger.warn("An exception was thrown in the JSON resource.\n");
	}

	@Override
	@Post("json")
	public Representation acceptAndReturnJSON(Representation entity)
	{
		try {
			JsonRepresentation jsonDocument = new JsonRepresentation(entity);
			JSONObject rootObject = jsonDocument.getJsonObject();
			JSONArray jsonArray = rootObject.optJSONArray("anArray");
			String stringValue = rootObject.optString("stringVariable");

			logger.info("array: " + jsonArray.toString());
			logger.info("string value: " + stringValue);
			return new JsonRepresentation("{ \"Result\": \"Ok\" }");
		} catch (IOException e) {
			setStatus(Status.SERVER_ERROR_INTERNAL);
			return new JsonRepresentation("{ \"error\" : \"Bad JSON: " + e.getMessage() + "\"}");
		} catch (JSONException e) {
			setStatus(Status.SERVER_ERROR_INTERNAL);
			return new JsonRepresentation("{ \"error\" : \"Internal server error: " + e.getMessage() + "\"}");
		}
	}
}