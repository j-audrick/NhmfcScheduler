package nhmfc.filenet.xml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "HolidayDateTime", strict = false)
public class HolidayDateTime {
    @Element(name = "date", required = true)
    private String date;
    
    @Element(name = "hStartTime", required = true)
    private String hStartTime;
    
    @Element(name = "hEndTime", required = true)
    private String hEndTime;

    public HolidayDateTime(@Element (name = "date") String date, @Element (name = "hStartTime") String hStartTime, @Element (name = "hEndTime") String hEndTime) {
		this.date = date;
		this.hStartTime = hStartTime;
		this.hEndTime = hEndTime;
	}
    
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String gethStartTime() {
		return hStartTime;
	}

	public void sethStartTime(String hStartTime) {
		this.hStartTime = hStartTime;
	}

	public String gethEndTime() {
		return hEndTime;
	}

	public void sethEndTime(String hEndTime) {
		this.hEndTime = hEndTime;
	}
    
}
