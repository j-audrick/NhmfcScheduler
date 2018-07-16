package nhmfc.filenet.xml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="Email", strict=false)
public class Email
{
  @Element(name="to", required=true)
  private String to;
  
  public Email(@Element(name="to") String to)
  {
    this.to = to;
  }
  
  public String getTo()
  {
    return this.to;
  }
  
  public void setTo(String to)
  {
    this.to = to;
  }
}
