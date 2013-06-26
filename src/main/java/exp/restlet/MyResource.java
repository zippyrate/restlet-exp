package exp.restlet;

import org.restlet.resource.Get;
import org.restlet.resource.Options;

public interface MyResource 
{
  @Get ("txt")
  public String represent();
  
  @Options ("txt")
  public String describe();
}