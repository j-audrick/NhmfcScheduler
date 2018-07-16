package nhmfc.filenet.xmlcusto;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="dateAndTime", strict=false)
public class DateAndTime
{
  @Element(name="date", required=false)
  private String date;
  @Element(name="hStartTime", required=false)
  private String hStartTime;
  @Element(name="hEndTime", required=false)
  private String hEndTime;
  
  public DateAndTime() {}
  
  public DateAndTime(String date, String hStartTime, String hEndTime)
  {
    this.date = date;
    this.hStartTime = hStartTime;
    this.hEndTime = hEndTime;
  }
  
  public String getDate()
  {
    return this.date;
  }
  
  public void setDate(String date)
  {
    this.date = date;
  }
  
  public String gethStartTime()
  {
    return this.hStartTime;
  }
  
  public void sethStartTime(String hStartTime)
  {
    this.hStartTime = hStartTime;
  }
  
  public String gethEndTime()
  {
    return this.hEndTime;
  }
  
  public void sethEndTime(String hEndTime)
  {
    this.hEndTime = hEndTime;
  }
}
