
package exp.restlet;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.Status;
import org.restlet.routing.Filter;

/**
 * Example restlet filter for preventing requests from particular IP addresses.
 */
public class MyIPBlocker extends Filter 
{
  private final Set<String> blockedAddresses;
  
  public MyIPBlocker (Context context) 
  {
  	super(context);
  	this.blockedAddresses = new CopyOnWriteArraySet<String>();
  }
  
  @Override
  protected int beforeHandle(Request request, Response response) 
  {
  	int result = STOP;

  	if (getBlockedAddresses().contains(request.getClientInfo().getAddress())) {
      response.setStatus(Status.CLIENT_ERROR_FORBIDDEN, "Your IP address was blocked");
    } else {
    	result = CONTINUE;
    }
  	return result;
  }
  
  public Set<String> getBlockedAddresses() {
  	return blockedAddresses;	
  } 
}