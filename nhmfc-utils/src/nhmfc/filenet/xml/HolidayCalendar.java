package nhmfc.filenet.xml;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDateTime;
import org.joda.time.Seconds;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Transient;

@Root(name="HolidayCalendar", strict=false)
public class HolidayCalendar
{
  @Element(name="minHourOfDay", required=true)
  private Integer minHourOfDay;
  @Element(name="maxHourOfDay", required=true)
  private Integer maxHourOfDay;
  @Element(name="minFlexiHourOfDay", required=true)
  private Integer minFlexiHourOfDay;
  @Element(name="maxFlexiHourOfDay", required=true)
  private Integer maxFlexiHourOfDay;
  @ElementList(name="Holidays", inline=false, required=false)
  private List<Holiday> holidays;
  @Transient
  private Map<String, Holiday> holidaysPerName;
  @Transient
  private Map<Integer, List<Holiday>> holidaysPerMonth;
  @Transient
  private Map<String, List<Holiday>> holidaysPerDay;
  @Transient
  private final DateTimeFormatter formatter = DateTimeFormat.forPattern("MMdd");
  @Transient
  private String durFormat;
  
  public HolidayCalendar() {}
  
  public HolidayCalendar(Integer minHourOfDay, Integer maxHourOfDay, Integer minFlexiHourOfDay, Integer maxFlexiHourOfDay, List<Holiday> holidays)
  {
    this(holidays);
    this.minHourOfDay = minHourOfDay;
    this.maxHourOfDay = maxHourOfDay;
    this.minFlexiHourOfDay = minFlexiHourOfDay;
    this.maxFlexiHourOfDay = maxFlexiHourOfDay;
  }
  
  public HolidayCalendar(List<Holiday> holidays)
  {
    this.holidays = holidays;
    //populateMaps();
  }
  
  public Set<LocalDateTime> getAsSet()
  {
    Set<LocalDateTime> set = new HashSet();
    for (Holiday hol : this.holidays) {
      set.add(hol.getLocalDateTime());
    }
    return set;
  }
  
  public List<Holiday> getHolidays()
  {
    return this.holidays;
  }
  
  public void addHoliday(Holiday holiday)
  {
    this.holidays.add(holiday);
  }
  
  /*public void populateMaps()
  {
    this.holidaysPerName = new HashMap();
    this.holidaysPerMonth = new HashMap();
    this.holidaysPerDay = new HashMap();
    for (Holiday holiday : this.holidays)
    {
      this.holidaysPerName.put(holiday.getName(), holiday);
      Integer endMonth = Integer.valueOf(holiday.getEndDate().get(2));
      Integer startMonth = Integer.valueOf(holiday.getStartDate().get(2));
      
      int counter = endMonth.intValue() - startMonth.intValue();
      if (counter < 0) {
        counter += 12;
      }
      List<Holiday> monthlyHolidays;
      Integer localInteger1;
      for (Integer i = startMonth; counter >= 0; localInteger1 = i = Integer.valueOf(i.intValue() + 1))
      {
        if (this.holidaysPerMonth.containsKey(i))
        {
          ((List)this.holidaysPerMonth.get(i)).add(holiday);
        }
        else
        {
          monthlyHolidays = new ArrayList(Arrays.asList(new Holiday[] { holiday }));
          this.holidaysPerMonth.put(i, monthlyHolidays);
        }
        if (i.intValue() + 1 == 12) {
          i = Integer.valueOf(-1);
        }
        counter--;monthlyHolidays = i;
      }
      String key = holiday.getStartDateAsDateTime().toString(this.formatter);
      if (this.holidaysPerDay.containsKey(key))
      {
        ((List)this.holidaysPerDay.get(key)).add(holiday);
      }
      else
      {
        List<Holiday> perDayHolidays = new ArrayList(Arrays.asList(new Holiday[] { holiday }));
        this.holidaysPerDay.put(key, perDayHolidays);
      }
    }
  }
  */
  public Integer getMinHourOfDay()
  {
    return this.minHourOfDay;
  }
  
  public void setMinHourOfDay(Integer minHourOfDay)
  {
    this.minHourOfDay = minHourOfDay;
  }
  
  public Integer getMaxHourOfDay()
  {
    return this.maxHourOfDay;
  }
  
  public void setMaxHourOfDay(Integer maxHourOfDay)
  {
    this.maxHourOfDay = maxHourOfDay;
  }
  
  public Integer getMinFlexiHourOfDay()
  {
    return this.minFlexiHourOfDay;
  }
  
  public void setMinFlexiHourOfDay(Integer minFlexiHourOfDay)
  {
    this.minFlexiHourOfDay = minFlexiHourOfDay;
  }
  
  public Integer getMaxFlexiHourOfDay()
  {
    return this.maxFlexiHourOfDay;
  }
  
  public void setMaxFlexiHourOfDay(Integer maxFlexiHourOfDay)
  {
    this.maxFlexiHourOfDay = maxFlexiHourOfDay;
  }
  
  public Map<String, Holiday> getHolidaysPerName()
  {
    return this.holidaysPerName;
  }
  
  public void setHolidaysPerName(Map<String, Holiday> holidaysPerName)
  {
    this.holidaysPerName = holidaysPerName;
  }
  
  public Map<Integer, List<Holiday>> getHolidaysPerMonth()
  {
    return this.holidaysPerMonth;
  }
  
  public void setHolidaysPerMonth(Map<Integer, List<Holiday>> holidaysPerMonth)
  {
    this.holidaysPerMonth = holidaysPerMonth;
  }
  
  public Map<String, List<Holiday>> getHolidaysPerDay()
  {
    return this.holidaysPerDay;
  }
  
  public void setHolidaysPerDay(Map<String, List<Holiday>> holidaysPerDay)
  {
    this.holidaysPerDay = holidaysPerDay;
  }
  
  public boolean isHoliday(DateTime date)
  {
    Holiday hol = getHoliday(date);
    if (hol == null) {
      return false;
    }
    Interval interval = new Interval(hol.getStartDateAsDateTime(), hol.getEndDateAsDateTime());
    return interval.contains(date);
  }
  
  public Holiday getHoliday(DateTime date)
  {
    String key = date.toString(this.formatter);
    if (this.holidaysPerDay.containsKey(key))
    {
      List<Holiday> hols = (List)this.holidaysPerDay.get(key);
      for (Holiday hol : hols)
      {
        Interval interval = new Interval(hol.getStartDateAsDateTime(), hol.getEndDateAsDateTime());
        if (interval.contains(date)) {
          return hol;
        }
      }
    }
    return null;
  }
  
  public boolean isHoliday(DateTime start, DateTime end)
  {
    if (start.getDayOfYear() != end.getDayOfYear()) {
      throw new IllegalArgumentException("Invalid input. Start Date day should be equal to End Date day");
    }
    List<Holiday> hols = (List)this.holidaysPerDay.get(start.toString(this.formatter));
    
    Integer startActual = Integer.valueOf(start.getHourOfDay());
    Integer endActual = Integer.valueOf(end.getHourOfDay());
    
    Interval actual = new Interval(start, end);
    if (this.holidays == null) {
      return false;
    }
    for (Holiday holiday : this.holidays)
    {
      Interval interval = new Interval(holiday.getStartDateAsDateTime(), holiday.getEndDateAsDateTime());
      Seconds actualSecs = Seconds.secondsIn(actual);
      Seconds holSecs = Seconds.secondsIn(interval);
      if (actualSecs.getSeconds() <= holSecs.getSeconds()) {}
    }
    return false;
  }
  
  public Long getUsableTime(DateTime start, DateTime end)
  {
    Long usableTime = Long.valueOf(0L);
    if ((start == null) || (end == null))
    {
      System.err.println("Illegal parameters. null dates");
      return Long.valueOf(0L);
    }
    if (end.isBefore(start))
    {
      System.err.println("Illegal parameters. end is before start");
      return Long.valueOf(0L);
    }
    DateTime tempStart = new DateTime(start.getMillis());
    DateTime tempEnd = new DateTime(end.getMillis());
    if (tempStart.getDayOfMonth() == tempEnd.getDayOfMonth())
    {
      if ((tempStart.getDayOfWeek() == 6) || (tempStart.getDayOfWeek() == 7)) {
        return Long.valueOf(0L);
      }
      if (tempEnd.getHourOfDay() > this.maxHourOfDay.intValue()) {
        tempEnd = tempEnd.withHourOfDay(this.maxHourOfDay.intValue()).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
      }
      if (tempStart.getHourOfDay() < this.minHourOfDay.intValue()) {
        tempStart = tempStart.withHourOfDay(this.minHourOfDay.intValue()).withMinuteOfHour(tempStart.getMinuteOfHour()).withSecondOfMinute(tempStart.getSecondOfMinute()).withMillisOfSecond(tempStart.getMillisOfSecond());
      } else if (tempStart.getHourOfDay() >= this.maxHourOfDay.intValue()) {
        tempStart = tempStart.withHourOfDay(this.maxHourOfDay.intValue()).withMinuteOfHour(tempStart.getMinuteOfHour()).withSecondOfMinute(tempStart.getSecondOfMinute()).withMillisOfSecond(tempStart.getMillisOfSecond());
      }
      if (this.holidaysPerDay.containsKey(tempStart.toString(this.formatter)))
      {
        List<Holiday> hols = (List)this.holidaysPerDay.get(tempStart.toString(this.formatter));
        Collections.sort(hols, new HolidayComparator());
        Holiday earliest = (Holiday)hols.get(0);
        Holiday latest = (Holiday)hols.get(hols.size() - 1);
        if (tempStart.isAfter(earliest.getStartDateAsDateTime())) {
          return Long.valueOf(0L);
        }
        if ((tempStart.isBefore(earliest.getStartDateAsDateTime())) && (tempEnd.isAfter(earliest.getStartDateAsDateTime()))) {
          tempEnd = earliest.getStartDateAsDateTime().withMillisOfSecond(0);
        } else if ((!tempStart.isBefore(earliest.getStartDateAsDateTime())) || (!tempEnd.isBefore(earliest.getStartDateAsDateTime()))) {}
      }
      usableTime = Long.valueOf(tempEnd.getMillis() - tempStart.getMillis());
      if (usableTime.longValue() < 1L) {
        usableTime = Long.valueOf(end.getMillis() - start.getMillis());
      }
    }
    else
    {
      while (tempStart.getDayOfMonth() <= tempEnd.getDayOfMonth())
      {
        if (tempStart.getDayOfMonth() != tempEnd.getDayOfMonth()) {
          usableTime = Long.valueOf(usableTime.longValue() + getUsableTime(tempStart, tempStart.withHourOfDay(this.maxHourOfDay.intValue()).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0)).longValue());
        } else if (tempStart.getDayOfMonth() == tempEnd.getDayOfMonth()) {
          usableTime = Long.valueOf(usableTime.longValue() + getUsableTime(tempStart, tempEnd).longValue());
        }
        tempStart = tempStart.plusDays(1).withHourOfDay(this.minHourOfDay.intValue()).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
      }
    }
    return usableTime;
  }
  
  public static String formatter(Long milliseconds, String durFormat)
  {
    Long nVal = milliseconds;
    int days = 0;
    int hours = 0;
    int minutes = 0;
    int seconds = 0;
    
    days = (int)(nVal.longValue() / 32400000L);
    if (days != 0) {
      nVal = Long.valueOf(nVal.longValue() - days * 32400000);
    }
    hours = (int)(nVal.longValue() / 3600000L);
    if (hours != 0) {
      nVal = Long.valueOf(nVal.longValue() - hours * 3600000);
    }
    minutes = (int)(nVal.longValue() / 60000L);
    if (minutes != 0) {
      nVal = Long.valueOf(nVal.longValue() - minutes * 60000);
    }
    seconds = (int)(nVal.longValue() / 1000L);
    if (durFormat == null) {
      durFormat = "%02d days:%02d hours:%02d minutes:%02d seconds";
    }
    return String.format(durFormat, new Object[] { Integer.valueOf(days), Integer.valueOf(hours), Integer.valueOf(minutes), Integer.valueOf(seconds) });
  }
  
  public HolidayObject asHolidayObject()
  {
    HolidayObject hObject = new HolidayObject();
    hObject.setHolidayType(HolidayType.REGULAR.name());
    List<HolidayObject> hols = new ArrayList();
    hObject.setHolidays(hols);
    for (Holiday holiday : this.holidays) {
      hols.add(holiday.asHolidayObject());
    }
    return hObject;
  }
}
