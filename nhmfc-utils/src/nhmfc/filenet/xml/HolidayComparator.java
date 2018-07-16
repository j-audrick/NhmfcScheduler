package nhmfc.filenet.xml;

import java.util.Comparator;
import org.joda.time.DateTime;

public class HolidayComparator
  implements Comparator<Holiday>
{
  public int compare(Holiday o1, Holiday o2)
  {
    if ((o1 == null) || (o2 == null)) {
      throw new IllegalArgumentException("Null Dates");
    }
    if (o1.getStartDateAsDateTime().isBefore(o2.getStartDateAsDateTime())) {
      return -1;
    }
    if (o1.getStartDateAsDateTime().isAfter(o2.getStartDateAsDateTime())) {
      return 1;
    }
    if (o1.getStartDateAsDateTime().equals(o2.getStartDateAsDateTime())) {
      return 0;
    }
    return -1;
  }
}
