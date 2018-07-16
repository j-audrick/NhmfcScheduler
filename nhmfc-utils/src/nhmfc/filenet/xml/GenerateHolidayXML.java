package nhmfc.filenet.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.CycleStrategy;
import org.simpleframework.xml.strategy.Strategy;

public class GenerateHolidayXML {

	public static void main(String[] args) throws FileNotFoundException, Exception {
	
	/*	HolidayObject heo = new HolidayObject("Philippine Holidays");

		heo.addHoliday(addHoliday("New Year", "Regular Holiday", "01-01", "08:00:00", "17:00:00", "New Year Day"));
		heo.addHoliday(addHoliday("Araw ng Kagitingan", "Regular Holiday", "04-09", "08:00:00", "17:00:00", "Araw ng Kagitingan"));
		heo.addHoliday(addHoliday("Labor Day", "Regular Holiday", "05-01", "08:00:00", "17:00:00", "Labor Day"));
		heo.addHoliday(addHoliday("Independence Day", "Regular Holiday", "06-12", "08:00:00", "17:00:00", "Independence Day"));
		heo.addHoliday(addHoliday("Araw ng mga Bayani", "Regular Holiday", "08-27", "08:00:00", "17:00:00", "Araw ng mga Bayani"));
		heo.addHoliday(addHoliday("Bonifacio Day", "Regular Holiday", "11-30", "08:00:00", "17:00:00", "Bonifacio Day"));
		heo.addHoliday(addHoliday("Christmas Day", "Regular Holiday", "12-25", "08:00:00", "17:00:00", "Christmas Day"));

		heo.addHoliday(addHoliday("EDSA Revolution Anniversary", "Special Non-Working", "02-25", "08:00:00", "17:00:00", "EDSA Revolution Anniversary"));
		heo.addHoliday(addHoliday("Ninoy Aquino Day", "Special Non-Working", "08-21", "08:00:00", "17:00:00", "Ninoy Aquino Day"));
		heo.addHoliday(addHoliday("All Saints Day", "Special Non-Working", "11-01", "08:00:00", "17:00:00", "All Saints Day"));
		heo.addHoliday(addHoliday("Feast of the Immaculate Conception of the Blessed Virgin Mary", "Special Non-Working", "08-21", "08:00:00", "17:00:00", 
				"Feast of the Immaculate Conception of the Blessed Virgin Mary"));
		heo.addHoliday(addHoliday("New Year Eve", "Special Non-Working", "12-31", "08:00:00", "17:00:00", "New Year Eve"));
		heo.addHoliday(addHoliday("Christmas Eve", "Special Non-Working", "12-24", "08:00:00", "17:00:00", "Christmas Eve"));
		
		Strategy strategy = new CycleStrategy("id", "ref");
		Persister persister = new Persister(strategy);
		persister.write(heo, new FileOutputStream(new File("Holidays.xml")));*/

	}

	/*public static HolidayObject addHoliday(String name, String holidayType, String date, String hStartTime, String hEndTime,
			String description) throws FileNotFoundException, Exception {

		// Holidays
		HolidayDateTime dateTime = new HolidayDateTime(date, hStartTime, hEndTime);
		dateTime.setDate(date);
		dateTime.sethStartTime(hStartTime);
		dateTime.sethEndTime(hEndTime);
		dateTime = new HolidayDateTime(date, hStartTime, hEndTime);

		HolidayObject h1 = new HolidayObject(name, holidayType, dateTime, description);
		h1.setName(name);
		h1.setType(holidayType);
		h1.setDateAndTime(dateTime);

		return h1;

	}*/

}
