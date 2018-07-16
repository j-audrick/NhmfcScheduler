package nhmfc.filenet.xmlcusto;

import java.util.List;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="HolidayObject", strict=false)
public class HolidayObject
{
  @Element(name="holidayType", required=false)
  private String holidayType;
  @Element(name="Name", required=false)
  private String name;
  @Element(name="description", required=false)
  private String description;
  @Element(name="dateAndTime", required=false)
  private DateAndTime dateAndTime;
  @ElementList(name="holidays", required=false, inline=false)
  private List<HolidayObject> holidays;
  
  public HolidayObject() {}
  
  public HolidayObject(String holidayType, String name, String description, DateAndTime dateAndTime, List<HolidayObject> holidays)
  {
    this.holidayType = holidayType;
    this.name = name;
    this.description = description;
    this.dateAndTime = dateAndTime;
    this.holidays = holidays;
  }
  
  public DateAndTime getDateAndTime()
  {
    return this.dateAndTime;
  }
  
  public void setDateAndTime(DateAndTime dateAndTime)
  {
    this.dateAndTime = dateAndTime;
  }
  
  public String getHolidayType()
  {
    return this.holidayType;
  }
  
  public void setHolidayType(String holidayType)
  {
    this.holidayType = holidayType;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getDescription()
  {
    return this.description;
  }
  
  public void setDescription(String description)
  {
    this.description = description;
  }
  
  public List<HolidayObject> getHolidays()
  {
    return this.holidays;
  }
  
  public void setHolidays(List<HolidayObject> holidays)
  {
    this.holidays = holidays;
  }
}
