package nhmfc.filenet;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.*;
import java.time.temporal.ChronoUnit;

import filenet.vw.api.*;

public class DCMSLogs {
	static Integer 		itr = 0;
    public static void main(String args[]) throws ParseException
    {
        VWSession       vwSession = null;

        vwSession = new VWSession("P8Admin", "IBMFileNetP8", "pecp1");
        if (vwSession != null)
        {
            // create the sample class
        	//DCMSLogsFetch(vwSession);
        	computeDueDateWHours();
        }
        
    }
    //this method should have a MAP argument which will store the collections of Workflow StartTime
    public static void computeDueDateWHours() throws ParseException {
    	//Instantiation of Local variables
    	String startdate = "Fri Apr 13 18:00:00 SGT 2018"; //this is static this should be coming from the map
    	DateFormat format = new SimpleDateFormat("E MMM d HH:mm:ss z yyyy");
    	Date stdate = format.parse(startdate);
    	
    	Calendar cdate = Calendar.getInstance(); //instantiation of the Calendar - this is for the functional use
    	Calendar duedate = Calendar.getInstance();
    	cdate.setTime(stdate);
    	duedate.setTime(stdate);
    	// TAT is Working HOURS - this should be from the XML
    	// 9hrs = 1 day
    	double TAT = 7 * 3600; //conversion from hours to seconds 

    	
    	if(isWorkingHours(stdate) == false) {
    		duedate.set(Calendar.HOUR_OF_DAY, 0);
    		duedate.set(Calendar.MINUTE, 0);
    		duedate.set(Calendar.SECOND, 0);
    		duedate.set(Calendar.MILLISECOND, 0);
    		duedate.add(Calendar.DATE, 1);
    		duedate.add(Calendar.HOUR, 8);
    		while(isHoliday(duedate) || isWeekEnd(duedate)){
    			duedate.add(Calendar.DATE, 1);
    		}
    	} 
    	
    	System.out.println(duedate.getTime());
    	// if the due date falls on holiday or weekend - change to the earliest 
    	//acceptable date and set to 8AM (start of working hours) of that day
    	
    	if (TAT <= 32400) {
    		duedate.add(Calendar.SECOND, (int) TAT);
			System.out.println(duedate.getTime()); //end 
		}else {
			Calendar c = Calendar.getInstance(); //setting the duedate to 5pm with same date
    		c.setTime(stdate);
        	c.set(Calendar.HOUR_OF_DAY, 0);
        	c.set(Calendar.MINUTE, 0);
        	c.set(Calendar.SECOND, 0);
        	c.add(Calendar.HOUR, 17);
        	
        	int secondsBetween = (int) ChronoUnit.SECONDS.between(cdate.toInstant(),c.toInstant()); // seconds to minutes to hours

        	TAT = TAT - secondsBetween;
        	duedate.set(Calendar.HOUR_OF_DAY,0); //setting of the duedate to next day 8am start of working hours
        	duedate.set(Calendar.MINUTE,0);
        	duedate.set(Calendar.SECOND,0);
        	duedate.add(Calendar.DATE,1);
        	duedate.add(Calendar.HOUR_OF_DAY, 8);
        	
    		while(TAT > 32400) { //32400 is equal to 9 hrs equal to 1 day
    			 
    			if(isHoliday(duedate) || isWeekEnd(duedate)) {
    				duedate.add(Calendar.DATE,1);
    			}else {
    				TAT = TAT - 32400;
    				duedate.add(Calendar.DATE,1);
            		while(isHoliday(duedate) || isWeekEnd(duedate)) {
            			duedate.add(Calendar.DATE,1);
            		}
    			}
    			
    		}
    		duedate.add(Calendar.SECOND, (int) TAT);
    		System.out.println(duedate.getTime()); //end - return this
    		
		}
    }

	public static Map<Integer, Map<String, String>> DCMSLogsFetch(VWSession vwSession) {

		VWLog           vwLog = null;  // Note the distinction between "log" method (below)
		// and a "vwLog" VWLog object
		VWLogQuery      logQuery = null;
		VWLogElement    logElement = null;
		String[]        fieldNames = null;
		Object          value = null;
		
		
		Map<Integer, Map<String, String>> data = new HashMap<Integer,Map<String, String>>();
		
		vwLog = vwSession.fetchEventLog("DefaultEventLog");
		vwLog.setBufferSize(25);
		logQuery = vwLog.startQuery(null, null, null, 0, null, null);
		logElement = logQuery.next();
		
		if (logElement != null)
        {
        	// iterate through the log elements
        	
            do
            {
            	itr++;
            	fieldNames = logElement.getFieldNames();
            	//Instantiation of Map which will Contain the fields F_WobNum, FText, F_Subject, F_Comment,
            	//ProcessName, F_StartTime and Department
            	Map<String, String> map = new HashMap<String, String>();
            	for (int i = 0; i < fieldNames.length; i++)
                {
            		
                    if (fieldNames[i] != null) // retrieve the field value and populate map
                    {
                        value = logElement.getFieldValue(fieldNames[i]); //u can use cast or toString parse
                        	if(fieldNames[i].toString().equals("F_WobNum")) {
                        		//map.put(fieldNames[i].toString(), value.toString()); // Storing data into the Map
                        		System.out.println(fieldNames[i].toString() + ": " + value);
                            }else if (fieldNames[i].toString().equals("F_Subject")) {
                            	//map.put(fieldNames[i].toString(), value.toString());
                            	System.out.println(fieldNames[i].toString() + ": " + value);
                            }else if (fieldNames[i].toString().equals("F_Comment")) {
                            	//map.put(fieldNames[i].toString(), value.toString());
                            	System.out.println(fieldNames[i].toString() + ": " + value);
                            }else if (fieldNames[i].toString().equals("ProcessName")) {
                            	//map.put(fieldNames[i].toString(), value.toString());
                            	System.out.println(fieldNames[i].toString() + ": " + value);
                            }else if (fieldNames[i].toString().equals("F_StartTime")) {
                            	//map.put(fieldNames[i].toString(), value.toString());
                            	System.out.println(fieldNames[i].toString() + ": " + value);
                            }else if (fieldNames[i].toString().equals("F_WorkFlowNumber")) {
                            	//map.put(fieldNames[i].toString(), value.toString());
                            	System.out.println(fieldNames[i].toString() + ": " + value);
                            }else if (fieldNames[i].toString().equals("Department")) {
                            	//map.put(fieldNames[i].toString(), value.toString());
                            	System.out.println(fieldNames[i].toString() + ": " + value);
                            }else if (fieldNames[i].toString().equals("ProcessStartTime")) {
                            	//map.put(fieldNames[i].toString(), value.toString());
                            	System.out.println(fieldNames[i].toString() + ": " + value);
                            }else if (fieldNames[i].toString().equals("ProcessEndTime")) {
                            	//map.put(fieldNames[i].toString(), value.toString());
                            	System.out.println(fieldNames[i].toString() + ": " + value);
                            }else if (fieldNames[i].toString().equals("PrevStepName")) {
                            	//map.put(fieldNames[i].toString(), value.toString());
                            	System.out.println(fieldNames[i].toString() + ": " + value);
                            }else if (fieldNames[i].toString().equals("CurStepName")) {
                            	//map.put(fieldNames[i].toString(), value.toString());
                            	System.out.println(fieldNames[i].toString() + ": " + value);
                            }
                    }
                }
            	data.put(itr, map); //populate the 2nd map with the iteration number and the field value map
            	
            }
            while ((logElement = logQuery.next()) != null);
            
            //displaying the nested map contents
            
            /*for(int x = 1; itr >= x; x++) {
            	System.out.println("F_WobNum: " + data.get(x).get("F_WobNum"));
            	System.out.println("F_Subject: " + data.get(x).get("F_Subject"));
            	System.out.println("F_StartTime: " + data.get(x).get("F_StartTime"));
            	System.out.println("F_Comment: " + data.get(x).get("F_Comment"));
            }*/
            
            
            
        }
		return data;
	}
	
	
	//checkers all WORKING 
	public static boolean isHoliday(Calendar cdate) throws ParseException 
	{
		List<String> holidays = new ArrayList<String>();
    	holidays.add("01-01-2018");
    	holidays.add("09-04-2018");
    	holidays.add("01-05-2018");
    	holidays.add("12-06-2018");
    	holidays.add("30-11-2018");
    	holidays.add("25-12-2018");
    	holidays.add("30-12-2018");
    	//IF CDATE is equal to any oh the holidays within the list return true
    	
    	//Convert CDATE and List to a date format
    	DateFormat format = new SimpleDateFormat("E MMM d HH:mm:ss z yyyy");
    	DateFormat dfrmt = new SimpleDateFormat("dd-MM-yyyy");
    	Date date = format.parse(cdate.getTime().toString());
    	boolean ishol = false;
    	for (String temp : holidays) {
			Date hol = dfrmt.parse(temp);
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal1.setTime(hol);
			cal2.setTime(date);
			
			ishol = cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) && cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
			if(ishol) {
				ishol = true;
				break;
			}
		}
    	return ishol;
	}
    public static boolean isWeekEnd(Calendar cdate) throws ParseException{
    	int dayofWeek = cdate.get(Calendar.DAY_OF_WEEK);
		if (dayofWeek == 1 || dayofWeek == 7) {
			return true;
		}else {
			return false;
		}
    }
    public static boolean isWorkingHours(Date stdate) throws ParseException{
    	Calendar st = Calendar.getInstance();
    	st.setTime(stdate);
    	st.set(Calendar.HOUR_OF_DAY, 0);
    	st.set(Calendar.MINUTE, 59);
    	st.set(Calendar.SECOND, 59);
    	st.set(Calendar.MILLISECOND, 0);
    	st.add(Calendar.HOUR, 7);
    	Date startwork = st.getTime();
    	st.set(Calendar.HOUR, 17);
    	st.set(Calendar.MINUTE, 0);
    	st.set(Calendar.SECOND, 0);
    	st.set(Calendar.MILLISECOND, 0);
    	Date endwork = st.getTime();
    	
    	if (stdate.after(startwork) && stdate.before(endwork)) {
    		System.out.println(stdate);
    		System.out.println(startwork);
    		return true;
    	}else {
    		System.out.println(stdate);
    		System.out.println(startwork);
    		return false;
    	}
    } 
}
