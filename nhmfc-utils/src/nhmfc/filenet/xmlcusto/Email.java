package nhmfc.filenet.xmlcusto;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="Email", strict=false)
public class Email
{
  @Element(name="to", required=true)
  private String to;
  @Element(name="from", required=false)
  private String from;
  @Element(name="subject", required=false)
  private String subject;
  @Element(name="body", required=false)
  private String body;
  
  public Email() {}
  
  public Email(String to, String from, String subject, String body)
  {
    this.to = to;
    this.from = from;
    this.subject = subject;
    this.body = body;
  }
  
  public String getTo()
  {
    return this.to;
  }
  
  public void setTo(String to)
  {
    this.to = to;
  }
  
  public String getFrom()
  {
    return this.from;
  }
  
  public void setFrom(String from)
  {
    this.from = from;
  }
  
  public String getSubject()
  {
    return this.subject;
  }
  
  public void setSubject(String subject)
  {
    this.subject = subject;
  }
  
  public String getBody()
  {
    return this.body;
  }
  
  public void setBody(String body)
  {
    this.body = body;
  }
  
  public int hashCode()
  {
    int hash = 3;
    hash = 89 * hash + (this.to != null ? this.to.hashCode() : 0);
    hash = 89 * hash + (this.from != null ? this.from.hashCode() : 0);
    hash = 89 * hash + (this.subject != null ? this.subject.hashCode() : 0);
    hash = 89 * hash + (this.body != null ? this.body.hashCode() : 0);
    return hash;
  }
  
  public boolean equals(Object obj)
  {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Email other = (Email)obj;
    if (this.to == null ? other.to != null : !this.to.equals(other.to)) {
      return false;
    }
    if (this.from == null ? other.from != null : !this.from.equals(other.from)) {
      return false;
    }
    if (this.subject == null ? other.subject != null : !this.subject.equals(other.subject)) {
      return false;
    }
    if (this.body == null ? other.body != null : !this.body.equals(other.body)) {
      return false;
    }
    return true;
  }
}
