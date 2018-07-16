package nhmfc.filenet.xml;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDateTime;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Transient;

@Root(name="Holiday")
public class Holiday
{
  @Element(name="Name", required=true)
  private String name;
  @Element(name="Description", required=false)
  private String description;
  @Transient
  private Calendar startDate;
  @Element(name="StartMonthInt", required=false)
  private Integer startMonthInt;
  @Element(name="StartDayInt", required=false)
  private Integer startDayInt;
  @Element(name="StartHourInt", required=false)
  private Integer startHourInt;
  @Element(name="StartMinuteInt", required=false)
  private Integer startMinuteInt;
  @Transient
  private Calendar endDate;
  @Element(name="EndMonthInt", required=false)
  private Integer endMonthInt;
  @Element(name="EndDayInt", required=false)
  private Integer endDayInt;
  @Element(name="EndHourInt", required=false)
  private Integer endHourInt;
  @Element(name="EndMinuteInt", required=false)
  private Integer endMinuteInt;
  @Element(name="type", required=false)
  private HolidayType type;
  
  public Holiday()
  {
    this.startDate = Calendar.getInstance();
    this.startDate.setTimeInMillis(new Date().getTime());
    this.endDate = Calendar.getInstance();
    this.endDate.setTimeInMillis(new Date().getTime());
  }
  
  public Holiday(String name)
  {
    this(name, null);
  }
  
  public Holiday(String name, String description)
  {
    this(name, description, HolidayType.REGULAR);
  }
  
  public Holiday(String name, String description, HolidayType type)
  {
    this();
    this.name = name;
    this.description = description;
    this.type = type;
  }
  
  public Holiday(String name, Integer startMonthInt, Integer startDayInt, Integer startHourInt, Integer startMinuteInt, Integer endMonthInt, Integer endDayInt, Integer endHourInt, Integer endMinuteInt, HolidayType type)
  {
    this.name = name;
    this.description = name;
    this.startMonthInt = startMonthInt;
    this.startDayInt = startDayInt;
    this.startHourInt = startHourInt;
    this.startMinuteInt = startMinuteInt;
    this.endMonthInt = endMonthInt;
    this.endDayInt = endDayInt;
    this.endHourInt = endHourInt;
    this.endMinuteInt = endMinuteInt;
    this.type = type;
  }
  
  public Holiday(String name, String description, Integer startMonthInt, Integer startDayInt, Integer startHourInt, Integer startMinuteInt, Integer endMonthInt, Integer endDayInt, Integer endHourInt, Integer endMinuteInt, HolidayType type)
  {
    this();
    this.name = name;
    this.description = description;
    this.startMonthInt = startMonthInt;
    this.startDayInt = startDayInt;
    this.startHourInt = startHourInt;
    this.startMinuteInt = startMinuteInt;
    this.endMonthInt = endMonthInt;
    this.endDayInt = endDayInt;
    this.endHourInt = endHourInt;
    this.endMinuteInt = endMinuteInt;
    this.type = type;
  }
  
  public Integer getStartDayInt()
  {
    return this.startDayInt;
  }
  
  public void setStartDayInt(Integer startDayInt)
  {
    this.startDayInt = startDayInt;
  }
  
  public Integer getEndDayInt()
  {
    return this.endDayInt;
  }
  
  public void setEndDayInt(Integer endDayInt)
  {
    this.endDayInt = endDayInt;
  }
  
  public Integer getStartMonthInt()
  {
    return this.startMonthInt;
  }
  
  public void setStartMonthInt(Integer startMonthInt)
  {
    this.startMonthInt = startMonthInt;
  }
  
  public Integer getStartHourInt()
  {
    return this.startHourInt;
  }
  
  public void setStartHourInt(Integer startHourInt)
  {
    this.startHourInt = startHourInt;
  }
  
  public Integer getStartMinuteInt()
  {
    return this.startMinuteInt;
  }
  
  public void setStartMinuteInt(Integer startMinuteInt)
  {
    this.startMinuteInt = startMinuteInt;
  }
  
  public Integer getEndMonthInt()
  {
    return this.endMonthInt;
  }
  
  public void setEndMonthInt(Integer endMonthInt)
  {
    this.endMonthInt = endMonthInt;
  }
  
  public Integer getEndHourInt()
  {
    return this.endHourInt;
  }
  
  public void setEndHourInt(Integer endHourInt)
  {
    this.endHourInt = endHourInt;
  }
  
  public Integer getEndMinuteInt()
  {
    return this.endMinuteInt;
  }
  
  public void setEndMinuteInt(Integer endMinuteInt)
  {
    this.endMinuteInt = endMinuteInt;
  }
  
  public HolidayType getType()
  {
    return this.type;
  }
  
  public void setType(HolidayType type)
  {
    this.type = type;
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
  
  public Calendar getStartDate()
  {
    if (this.startDate == null)
    {
      this.startDate = Calendar.getInstance();
      this.startDate.setTimeInMillis(new Date().getTime());
    }
    this.startDate.set(2, this.startMonthInt.intValue());
    this.startDate.set(5, this.startDayInt.intValue());
    this.startDate.set(11, this.startHourInt.intValue());
    this.startDate.set(12, this.startMinuteInt.intValue());
    this.startDate.set(13, 0);
    
    return this.startDate;
  }
  
  public void setStartDate(Calendar startDate)
  {
    this.startDate = startDate;
  }
  
  public Calendar getEndDate()
  {
    if (this.endDate == null)
    {
      this.endDate = Calendar.getInstance();
      this.endDate.setTimeInMillis(new Date().getTime());
    }
    this.endDate.set(2, this.endMonthInt.intValue());
    this.endDate.set(5, this.endDayInt.intValue());
    this.endDate.set(11, this.endHourInt.intValue());
    this.endDate.set(12, this.endMinuteInt.intValue());
    this.endDate.set(13, 0);
    return this.endDate;
  }
  
  public void setEndDate(Calendar endDate)
  {
    this.endDate = endDate;
  }
  
  public Interval asInterval()
  {
    DateTime t1 = getStartDateAsDateTime();
    DateTime t2 = getEndDateAsDateTime();
    return new Interval(t1, t2);
  }
  
  public LocalDateTime getLocalDateTime()
  {
    return new LocalDateTime(getEndDateAsDateTime());
  }
  
  public DateTime getStartDateAsDateTime()
  {
    if (this.startDate == null) {
      this.startDate = Calendar.getInstance();
    }
    this.startDate.set(2, this.startMonthInt.intValue());
    this.startDate.set(5, this.startDayInt.intValue());
    this.startDate.set(11, this.startHourInt.intValue());
    this.startDate.set(12, this.startMinuteInt.intValue());
    this.startDate.set(13, 0);
    return new DateTime(this.startDate.getTimeInMillis());
  }
  
  public DateTime getEndDateAsDateTime()
  {
    this.endDate.set(2, this.endMonthInt.intValue());
    this.endDate.set(5, this.endDayInt.intValue());
    this.endDate.set(11, this.endHourInt.intValue());
    this.endDate.set(12, this.endMinuteInt.intValue());
    this.endDate.set(13, 0);
    return new DateTime(this.endDate.getTimeInMillis());
  }
  
  public int hashCode()
  {
    int hash = 7;
    hash = 37 * hash + Objects.hashCode(this.name);
    hash = 37 * hash + Objects.hashCode(this.description);
    hash = 37 * hash + Objects.hashCode(this.startMonthInt);
    hash = 37 * hash + Objects.hashCode(this.startDayInt);
    hash = 37 * hash + Objects.hashCode(this.startHourInt);
    hash = 37 * hash + Objects.hashCode(this.startMinuteInt);
    hash = 37 * hash + Objects.hashCode(this.endMonthInt);
    hash = 37 * hash + Objects.hashCode(this.endDayInt);
    hash = 37 * hash + Objects.hashCode(this.endHourInt);
    hash = 37 * hash + Objects.hashCode(this.endMinuteInt);
    hash = 37 * hash + Objects.hashCode(this.type);
    return hash;
  }
  
  public boolean equals(Object obj)
  {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Holiday other = (Holiday)obj;
    if (!Objects.equals(this.name, other.name)) {
      return false;
    }
    if (!Objects.equals(this.startDate, other.startDate)) {
      return false;
    }
    if (!Objects.equals(this.endDate, other.endDate)) {
      return false;
    }
    return true;
  }
  
  public String toString()
  {
    return "Holiday{name=" + this.name + ", description=" + this.description + ", startDate=" + this.startDate + ", endDate=" + this.endDate + ", type=" + this.type + '}';
  }
  
  public HolidayObject asHolidayObject()
  {
    DateAndTime dt = new DateAndTime();
    
    dt.setDate(this.startMonthInt + "-" + this.startDayInt);
    dt.sethStartTime(this.startHourInt + ":" + this.startMinuteInt + ":00");
    dt.sethEndTime(this.endHourInt + ":" + this.endMinuteInt + ":00");
    
    HolidayObject holObj = new HolidayObject();
    holObj.setDescription(this.description);
    holObj.setName(this.name);
    holObj.setDateAndTime(dt);
    holObj.setHolidayType(this.type.name());
    
    return holObj;
  }
}
