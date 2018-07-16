package nhmfc.filenet.xmlcusto;

import com.ibm.json.java.JSONObject;
import org.joda.time.DateTime;

public class CDate
{
  private Integer month;
  private Integer day;
  private Integer hour;
  private Integer minute;
  
  public CDate() {}
  
  public CDate(Integer month, Integer day, Integer hour, Integer minute)
  {
    this.month = month;
    this.day = day;
    this.hour = hour;
    this.minute = minute;
  }
  
  public CDate(JSONObject json)
  {
    this.month = Integer.valueOf(Integer.parseInt((String)json.get("month")));
    this.day = Integer.valueOf(Integer.parseInt((String)json.get("day")));
    this.hour = Integer.valueOf(Integer.parseInt((String)json.get("hour")));
    this.minute = Integer.valueOf(Integer.parseInt((String)json.get("minute")));
  }
  
  public Integer getMonth()
  {
    return this.month;
  }
  
  public void setMonth(Integer month)
  {
    this.month = month;
  }
  
  public Integer getDay()
  {
    return this.day;
  }
  
  public void setDay(Integer day)
  {
    this.day = day;
  }
  
  public Integer getHour()
  {
    return this.hour;
  }
  
  public void setHour(Integer hour)
  {
    this.hour = hour;
  }
  
  public Integer getMinute()
  {
    return this.minute;
  }
  
  public void setMinute(Integer minute)
  {
    this.minute = minute;
  }
  
  public DateTime asDateTime()
  {
    return new DateTime().withMonthOfYear(this.month.intValue()).withDayOfMonth(this.day.intValue()).withHourOfDay(this.hour.intValue()).withMinuteOfHour(this.minute.intValue()).withSecondOfMinute(0);
  }
}
