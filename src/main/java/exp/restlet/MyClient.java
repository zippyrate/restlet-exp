package exp.restlet;

import org.restlet.resource.ClientResource;

public class MyClient 
{
  public static void main(String[] args) throws Exception 
  {
  	ClientResource ePadClientResource = new ClientResource("http://localhost:8111/");

  	ePadClientResource.get().write(System.out);
  	//ePadClientResource.options().write(System.out);
  }
}