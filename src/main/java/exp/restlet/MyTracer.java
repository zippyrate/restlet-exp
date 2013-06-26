package exp.restlet;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;

/**
 * Example restlet to dump invocation parameters.
 */
public class MyTracer extends Restlet 
{
  public MyTracer (Context context) {
      super(context);
  }
  
	@Override
	public void handle(Request request, Response response) 
	{
		String entity = "Method       : " + request.getMethod()
				+ "\nResource URI : " + request.getResourceRef() 
				+ "\nIP address   : " + request.getClientInfo().getAddress() 
				+ "\nAgent name   : " + request.getClientInfo().getAgentName() 
				+ "\nAgent version: " + request.getClientInfo().getAgentVersion() + "\n";
		System.out.println("Request: " + request);
		System.out.println("app roles: " + this.getApplication().getRoles());
		System.out.println("name: " + this.getName());
		response.setEntity(entity, MediaType.TEXT_PLAIN);
	}	
}