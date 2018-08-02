package nhmfc.filenet.utils;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.security.auth.Subject;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.CycleStrategy;
import org.simpleframework.xml.strategy.Strategy;

import com.filenet.api.core.Connection;
import com.filenet.api.core.Document;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.exception.EngineRuntimeException;
import com.filenet.api.util.UserContext;

import filenet.vw.api.VWFetchType;
import filenet.vw.api.VWLog;
import filenet.vw.api.VWLogElement;
import filenet.vw.api.VWLogQuery;
import filenet.vw.api.VWRoster;
import filenet.vw.api.VWRosterQuery;
import filenet.vw.api.VWSession;
import filenet.vw.api.VWWorkObject;
import filenet.vw.api.VWWorkObjectNumber;
import nhmfc.filenet.xml.Holiday;
import nhmfc.filenet.xml.HolidayCalendar;
import nhmfc.filenet.xml.HolidayObject;

public class Utilities {
	private static int BEGINWORKHOUR = 8;
	private static int ENDWORKHOUR = 17;
	
	//Content Engine Connection
	public static Connection getCEConnectionHTTP() {
		Connection connection = null;
		String UserName = "P8Admin";
		String Password = "IBMFileNetP8";
		String Url = "http://192.168.20.31:9080/wsi/FNCEWS40MTOM/";
		String Stanza = "FileNetP8WSI";
		connection = Factory.Connection.getConnection(Url);
		// System.out.println("Connection is established");
		Subject subject = UserContext.createSubject(connection, UserName, Password, Stanza);
		UserContext uc = UserContext.get();
		try {
			uc.pushSubject(subject);
		} catch (EngineRuntimeException engineRuntimeException) {
			System.out.println(
					"RuntimeException occured while getting the connection = " + engineRuntimeException.getMessage());

			engineRuntimeException.printStackTrace();
		}
		return connection;
	}
	
	//Main Computations | computation of Due Date -------------------------
	public static String computeDueDateWHours(String start, double Tat, List<String[]> holdaylist) throws Exception {
		// Instantiation of Local variables
		String deadline = "";
		String startdate = start; // this is static this should be coming from the map
		DateFormat format = new SimpleDateFormat("E MMM d HH:mm:ss z yyyy");
		Date stdate = format.parse(startdate);

		Calendar cdate = Calendar.getInstance(); // instantiation of the Calendar - this is for the functional use
		Calendar duedate = Calendar.getInstance();
		cdate.setTime(stdate);
		duedate.setTime(stdate);

		// TAT is Working HOURS - this should be from the XML
		// 9hrs = 1 day
		double TAT = Tat; // conversion from hours to seconds
		if (!isWorkingHours(stdate)) {
			duedate.set(Calendar.HOUR_OF_DAY, 0);
			duedate.set(Calendar.MINUTE, 0);
			duedate.set(Calendar.SECOND, 0);
			duedate.set(Calendar.MILLISECOND, 0);
			duedate.add(Calendar.DATE, 1);
			duedate.add(Calendar.HOUR, 8);
			while (isHoliday(duedate, holdaylist) == 32400 || isWeekEnd(duedate)) {
				duedate.add(Calendar.DATE, 1);
			}
		}
		
		if (isHoliday(duedate, holdaylist) < 32400) {
			if((TAT + isHoliday(duedate, holdaylist)) > 0) {
				TAT = TAT + isHoliday(duedate, holdaylist);
			}else {
				TAT = TAT + isHol(duedate, holdaylist);
			}
			
		}
		// if the due date falls on holiday or weekend - change to the earliest
		// acceptable date and set to 8AM (start of working hours) of that day

		if (TAT < 32400) {
			
			Date d = duedate.getTime();
			duedate.add(Calendar.SECOND, (int) TAT);
			Calendar dc = Calendar.getInstance();
			Calendar dx = Calendar.getInstance();
			dc.setTime(d);
			dx.setTime(d);
			dx.set(Calendar.HOUR_OF_DAY, 0);
			dx.set(Calendar.MINUTE, 0);
			dx.set(Calendar.SECOND, 0);
			dx.add(Calendar.HOUR, 17);

			// collect ung difference before add and after add -date1
			int firstdiff = (int) ChronoUnit.SECONDS.between(dc.toInstant(), duedate.toInstant());
			// collect ung difference before add and 5pm of that date -date2
			int seconddiff = (int) ChronoUnit.SECONDS.between(dc.toInstant(), dx.toInstant());

			if (firstdiff >= seconddiff) {
				dc.set(Calendar.HOUR_OF_DAY, 0);
				dc.set(Calendar.MINUTE, 0);
				dc.set(Calendar.SECOND, 0);
				dc.add(Calendar.DATE, 1);
				dc.add(Calendar.HOUR, 8);
				while (isHoliday(dc, holdaylist) == 32400 || isWeekEnd(dc)) {
					dc.add(Calendar.DATE, 1);
				}
				dc.add(Calendar.SECOND, firstdiff - seconddiff);
				duedate.setTime(dc.getTime());
			}	
			deadline = duedate.getTime().toString(); // end - return this
		} else {
			while (TAT >= 32400) { // 32400 is equal to 9 hrs equal to 1 day
				if (isHoliday(duedate, holdaylist) == 32400 || isWeekEnd(duedate)) {
					System.out.println(duedate.getTime().toString());
					while (isHoliday(duedate, holdaylist) == 32400 || isWeekEnd(duedate)) {
						duedate.add(Calendar.DATE, 1);
						System.out.println(duedate.getTime().toString());
					}
				} else {
					TAT = TAT - 32400;
					duedate.add(Calendar.DATE, 1);
					while (isHoliday(duedate, holdaylist) == 32400 || isWeekEnd(duedate)) {
						duedate.add(Calendar.DATE, 1);
					}
					if (isHoliday(duedate, holdaylist) < 32400) {
						if((TAT + isHoliday(duedate, holdaylist)) > 0) {
							TAT = TAT + isHol(duedate, holdaylist);
						}else {
							TAT = TAT + isHol(duedate, holdaylist);
						}
					}
				}
			}
			
			Date d = duedate.getTime();
			duedate.add(Calendar.SECOND, (int) TAT);
			Calendar dc = Calendar.getInstance();
			Calendar dx = Calendar.getInstance();
			dc.setTime(d);
			dx.setTime(d);
			dx.set(Calendar.HOUR_OF_DAY, 0);
			dx.set(Calendar.MINUTE, 0);
			dx.set(Calendar.SECOND, 0);
			dx.add(Calendar.HOUR, 17);

			// collect ung difference before add and after add -date1
			int firstdiff = (int) ChronoUnit.SECONDS.between(dc.toInstant(), duedate.toInstant());
			// collect ung difference before add and 5pm of that date -date2
			int seconddiff = (int) ChronoUnit.SECONDS.between(dc.toInstant(), dx.toInstant());

			if (firstdiff >= seconddiff) {
				dc.set(Calendar.HOUR_OF_DAY, 0);
				dc.set(Calendar.MINUTE, 0);
				dc.set(Calendar.SECOND, 0);
				dc.add(Calendar.DATE, 1);
				dc.add(Calendar.HOUR, 8);
				while (isHoliday(dc, holdaylist) == 32400 || isWeekEnd(dc)) {
					dc.add(Calendar.DATE, 1);
				}
				dc.add(Calendar.SECOND, firstdiff - seconddiff);
				duedate.setTime(dc.getTime());
			}
			deadline = duedate.getTime().toString(); // end - return this
		}
		return deadline;
	}

	// checkers for Computation of due date

	public static List<String[]> hoList(Connection conn) throws Exception {
		List<String[]> hList = new ArrayList<String[]>();

		Domain domain = Factory.Domain.fetchInstance(conn, null, null);
		ObjectStore objStore = Factory.ObjectStore.fetchInstance(domain, "DCMS", null);

		// Retrieve document by path
		Document d1 = Factory.Document.fetchInstance(objStore, "/Preferences/holidayCalendar", null); //noted ka 

		InputStream stream = d1.accessContentStream(0);

		Strategy strategy = new CycleStrategy("id", "ref");
		Serializer serializer = new Persister(strategy);
		HolidayCalendar heo = serializer.read(HolidayCalendar.class, stream);
		
		
		for (Holiday hol : heo.getHolidays()) {
			
			//FORMATTING FROM XML
			Integer sMInt = hol.getStartMonthInt() + 1; Integer eMInt = hol.getEndMonthInt() + 1;
			Integer sHInt = (hol.getStartHourInt() < 8 ? 8 : hol.getStartHourInt()); Integer eHInt = (hol.getEndHourInt() > 17 ? 17 : hol.getEndHourInt());
			
			
			String sMonth = (sMInt > 9 ? sMInt.toString(): "0" + sMInt);
			String sDay = (hol.getStartDayInt() > 9 ? hol.getStartDayInt().toString() : "0" + hol.getStartDayInt().toString());
			String sHour = (sHInt > 9 ? sHInt.toString() : "0" + sHInt.toString());
			String sMin = (hol.getStartMinuteInt() > 9 ? hol.getStartMinuteInt().toString(): "0" + hol.getStartMinuteInt().toString());
			
			String eMonth = (eMInt > 9 ? eMInt.toString(): "0" + eMInt);
			String eDay = (hol.getEndDayInt() > 9 ? hol.getEndDayInt().toString() : "0" + hol.getEndDayInt().toString());
			String eHour = (eHInt > 9 ? eHInt.toString() : "0" + eHInt.toString());
			String eMin = (hol.getEndMinuteInt() > 9 ? hol.getEndMinuteInt().toString(): "0" + hol.getEndMinuteInt().toString());
			
			if(hol.getStartHourInt() < 8) {
				sMin = "00";
			}
			
			if (hol.getEndHourInt() > 16) {
				eMin = "00";
			}
			hList.add(new String[] {
				sMonth + "-" + sDay + "-" + Year.now().getValue() + " " + 
				sHour + ":" + sMin + ":00",
				eMonth + "-" + eDay + "-" + Year.now().getValue() + " " + 
				eHour + ":"	+ eMin + ":00"	
				});
		}

		return hList;
	}

	public static int isHoliday(Calendar cdate, List<String[]> holdaylist) throws Exception {
		List<String[]> holidays = holdaylist;

		// Convert CDATE and List to a date format
		DateFormat format = new SimpleDateFormat("E MMM d HH:mm:ss z yyyy");
		DateFormat dfrmt = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		Date date = format.parse(cdate.getTime().toString());
		int totalMins = 0;
		boolean ishol = false;
		for (String[] temp : holidays) {
			Date sthol = dfrmt.parse(temp[0]);
			Date edhol = dfrmt.parse(temp[1]);
			Calendar stcalhol = Calendar.getInstance();
			Calendar edcalhol = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			stcalhol.setTime(sthol);
			edcalhol.setTime(edhol);
			cal2.setTime(date);
			
			Calendar eob = Calendar.getInstance();
			eob.setTime(edhol);
			eob.set(Calendar.HOUR_OF_DAY, 0);
			eob.set(Calendar.MINUTE, 0);
			eob.set(Calendar.SECOND, 0);
			eob.add(Calendar.HOUR, 17);

			ishol = (stcalhol.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
					&& stcalhol.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) &&
					(edcalhol.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
					&& edcalhol.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)); //checking if start and end date is same day and same with the cdate
			if (ishol) {
				if(isWithinRange(cal2,stcalhol,edcalhol)) {
					if(edcalhol.before(eob)) {
						Integer rmnwt = 0; Integer rmnht = 0;
						rmnwt = (int) ChronoUnit.SECONDS.between(edcalhol.toInstant(), eob.toInstant()) * -1;
						rmnht = (cdate.before(stcalhol)?(int) ChronoUnit.SECONDS.between(stcalhol.toInstant(), edcalhol.toInstant()):(int) ChronoUnit.SECONDS.between(cdate.toInstant(), edcalhol.toInstant()));
						totalMins = rmnwt + rmnht;
						break;
					}else {
						totalMins = (int) ChronoUnit.SECONDS.between(cal2.toInstant(), edcalhol.toInstant());
						break;
					}
					
				}else {
					totalMins = (int) ChronoUnit.SECONDS.between(stcalhol.toInstant(), edcalhol.toInstant());
				}
				break;
			}else if (isWithinRange(cal2,stcalhol,edcalhol) || ((stcalhol.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) && stcalhol.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) || (edcalhol.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) && edcalhol.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)))) {
				totalMins = 32400;
				break;
			}
		}
		return totalMins;
	}

	public static int isHol(Calendar cdate, List<String[]> holdaylist) throws Exception {
		List<String[]> holidays = holdaylist;

		// Convert CDATE and List to a date format
		DateFormat format = new SimpleDateFormat("E MMM d HH:mm:ss z yyyy");
		DateFormat dfrmt = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
		Date date = format.parse(cdate.getTime().toString());
		int totalMins = 0;
		boolean ishol = false;
		for (String[] temp : holidays) {
			Date sthol = dfrmt.parse(temp[0]);
			Date edhol = dfrmt.parse(temp[1]);
			Calendar stcalhol = Calendar.getInstance();
			Calendar edcalhol = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			stcalhol.setTime(sthol);
			edcalhol.setTime(edhol);
			cal2.setTime(date);
			
			Calendar eob = Calendar.getInstance();
			eob.setTime(edhol);
			eob.set(Calendar.HOUR_OF_DAY, 0);
			eob.set(Calendar.MINUTE, 0);
			eob.set(Calendar.SECOND, 0);
			eob.add(Calendar.HOUR, 17);

			ishol = (stcalhol.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
					&& stcalhol.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) &&
					(edcalhol.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
					&& edcalhol.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)); //checking if start and end date is same day and same with the cdate
			if (ishol) {
					totalMins = (int) ChronoUnit.SECONDS.between(stcalhol.toInstant(), edcalhol.toInstant());
					break;
				}
		}		
		return totalMins;
	}
	public static boolean isWeekEnd(Calendar cdate) throws ParseException {
		int dayofWeek = cdate.get(Calendar.DAY_OF_WEEK);
		if (dayofWeek == 1 || dayofWeek == 7) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean isWithinRange(Calendar date, Calendar stdate, Calendar eddate) {
		return date.after(stdate) && date.before(eddate);
	}

	public static boolean isWorkingHours(Date stdate) throws ParseException {
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
			return true;
		} else {
			return false;
		}
	}

	//Computation Utils End -----------------------------------------------
	
	//Data Fetch functions ------------------------------------------------
	public static Map<Integer, Map<String, String>> logFetch(VWSession vwSession) {
		VWLog vwLog = null; // Note the distinction between "log" method (below) and a "vwLog" VWLog object
		VWLogQuery logQuery = null;
		VWLogElement logElement = null;
		
		
		Map<Integer, Map<String, String>> data = new HashMap<Integer, Map<String, String>>();
		
		String queryFilter = "xMinutesRendered>:A AND F_EventType=:B";
		Object[] substitutionVars = { "0.0","1050" };
		int queryFlags = VWLog.QUERY_NO_OPTIONS;

		
		vwLog = vwSession.fetchEventLog("DefaultEventLog");
		vwLog.setBufferSize(250);
		logQuery = vwLog.startQuery(null, null, null, queryFlags, queryFilter, substitutionVars);
		int itr = 0;
		System.out.println("Log fetch count: " + logQuery.fetchCount());
		while (logQuery.hasNext()) {
			logElement = logQuery.next();
			itr++;
			Map<String, String> map = new HashMap<String, String>();
			map.put("F_WobNum", logElement.getFieldValue("F_WobNum").toString());
			map.put("StepName", logElement.getFieldValue("CurStepName").toString());
			map.put("xMinutesRendered", logElement.getFieldValue("xMinutesRendered").toString());
			map.put("PrevStartTime", logElement.getFieldValue("PrevStartTime").toString());
			map.put("PrevEndTime", logElement.getFieldValue("PrevEndTime").toString());
			data.put(itr, map);
		}
		System.out.println(itr);
		return data;
	}
	
	public static Map<Integer, Map<String, String>> RosterFetch(VWSession vwSession) {
		// Retrieve transfered work classes

		Map<Integer, Map<String, String>> data = new HashMap<Integer, Map<String, String>>();

		// Set Roster Name
		String rosterName = "DefaultRoster";
		// Retrieve Roster Object and Roster count
		VWRoster roster = vwSession.getRoster(rosterName);
		// System.out.println("Workflow Count: " + roster.fetchCount());
		// Set Query Parameters
		int queryFlags = VWRoster.QUERY_NO_OPTIONS;
		String queryFilter = "F_TrackerStatus=:A";
		String tStatus = "0";
		// VWWorkObjectNumber class takes care of the value format
		// used in place of F_WobNum and F_WorkFlowNumber
		Object[] substitutionVars = { tStatus };
		int fetchType = VWFetchType.FETCH_TYPE_WORKOBJECT;
		// Perform Query
		VWRosterQuery query = roster.createQuery(null, null, null, queryFlags, queryFilter, substitutionVars,
				fetchType);
		// Process Results
		int itr = 0;
		// System.out.println(query.fetchCount());
		System.out.println("Roster count:" + roster.fetchCount());

		while (query.hasNext()) {
			VWWorkObject vwWorkObjectItem = (VWWorkObject) query.next();
			if (vwWorkObjectItem.getFieldValue("F_QueueWPClassId").toString().equals("2")
					&& vwWorkObjectItem.getFieldValue("F_Locked").toString().equals("0")) {
				itr++;
				Map<String, String> map = new HashMap<String, String>();
				map.put("F_WobNum", vwWorkObjectItem.getFieldValue("F_WobNum").toString());
				map.put("F_Class", vwWorkObjectItem.getFieldValue("F_Class").toString());
				map.put("StepName", vwWorkObjectItem.getStepName());
				map.put("xDeadline", vwWorkObjectItem.getFieldValue("xDeadline").toString());
				map.put("xWorkflowDeadline", vwWorkObjectItem.getFieldValue("xWorkflowDeadline").toString());
				map.put("F_Subject", vwWorkObjectItem.getFieldValue("F_Subject").toString());
				map.put("F_StartTime", vwWorkObjectItem.getFieldValue("F_StartTime").toString());
				map.put("F_Overdue", vwWorkObjectItem.getFieldValue("F_Overdue").toString());
				map.put("F_Originator", vwWorkObjectItem.getFieldValue("F_Originator").toString());
				map.put("F_BoundUser",vwWorkObjectItem.getFieldValue("F_BoundUser").toString());
				map.put("PrevEndTime", vwWorkObjectItem.getFieldValue("PrevEndTime").toString());
				map.put("IsEmailed", vwWorkObjectItem.getFieldValue("IsEmailed").toString());
				map.put("IsEmailedWF", vwWorkObjectItem.getFieldValue("IsEmailedWF").toString());
/*				if(vwWorkObjectItem.getFieldValue("IsEmailedWrn") != null) {
					map.put("IsEmailedWrn", vwWorkObjectItem.getFieldValue("IsEmailedWrn").toString());
				}*/
				data.put(itr, map);
			}

		}
		System.out.println("Workitems available for update:" + itr);
		return data;
	}
	//Date Fetch functions ------------------------------------------------
	
	
	//Update Functions
	// for Updating running work items
	public static void updateWorkItems(String uqid, String duedate, String status, VWSession vwsession)
			throws ParseException {
		VWWorkObject vwwObj = null;
		int queryFlags = VWRoster.QUERY_NO_OPTIONS;
		String queryFilter = "F_WobNum=:A";
		// String wobNum = "WELCOME HOMES";
		Object[] substitutionVars = { new VWWorkObjectNumber(uqid) };
		// Change filter based on requirement String
		String rosterName = "DefaultRoster";
		VWRoster roster = vwsession.getRoster(rosterName);
		VWRosterQuery query = roster.createQuery(null, null, null, queryFlags, queryFilter, substitutionVars,
				VWFetchType.FETCH_TYPE_WORKOBJECT);
		// System.out.println("Total records for Work Item: " + query1.fetchCount());
		String startdate = duedate;
		DateFormat format = new SimpleDateFormat("E MMM d HH:mm:ss z yyyy");
		Date deadline = format.parse(startdate);
		while (query.hasNext()) {
			vwwObj = (VWWorkObject) query.next();
			vwwObj.doLock(true);
			vwwObj.setFieldValue("xStatus", status, true);
			vwwObj.setFieldValue("xDeadline", deadline, true);
			// vwwObj.setFieldValue("F_WFDeadline", 123, true);
			// System.out.println();
			vwwObj.doSave(true);
			// System.out.println("Updated Step Deadline and Status");
		}
	}

	// for Updating completed work items
	public static void updateWorkItems(String uqid, String status, VWSession vwsession) {
		VWWorkObject vwwObj = null;
		int queryFlags = VWRoster.QUERY_NO_OPTIONS;
		String queryFilter = "F_WobNum=:A";
		// String wobNum = "WELCOME HOMES";
		Object[] substitutionVars = { new VWWorkObjectNumber(uqid) };
		// Change filter based on requirement String
		String rosterName = "DefaultRoster";
		VWRoster roster = vwsession.getRoster(rosterName);
		VWRosterQuery query = roster.createQuery(null, null, null, queryFlags, queryFilter, substitutionVars,
				VWFetchType.FETCH_TYPE_WORKOBJECT);
		while (query.hasNext()) {
			vwwObj = (VWWorkObject) query.next();
			vwwObj.doLock(true);
			vwwObj.setFieldValue("xStatus", status, true);
			vwwObj.doSave(true);
			// System.out.println("Updated Step Status");
		}
	}

	public static void updateWorkflowTat(String uqid, String duedate, String status, VWSession vwsession)
			throws ParseException {
		VWWorkObject vwwObj = null;
		int queryFlags = VWRoster.QUERY_NO_OPTIONS;
		String queryFilter = "F_WobNum=:A";
		// String wobNum = "WELCOME HOMES";
		Object[] substitutionVars = { new VWWorkObjectNumber(uqid) };
		// Change filter based on requirement String
		String rosterName = "DefaultRoster";
		VWRoster roster = vwsession.getRoster(rosterName);
		VWRosterQuery query = roster.createQuery(null, null, null, queryFlags, queryFilter, substitutionVars,
				VWFetchType.FETCH_TYPE_WORKOBJECT);
		// System.out.println("Total records for Work Item: " + query1.fetchCount());
		String startdate = duedate;
		DateFormat format = new SimpleDateFormat("E MMM d HH:mm:ss z yyyy");
		Date deadline = format.parse(startdate);
		while (query.hasNext()) {
			vwwObj = (VWWorkObject) query.next();
			vwwObj.doLock(true);
			vwwObj.setFieldValue("xWorkflowStatus", status, true);
			vwwObj.setFieldValue("xWorkflowDeadline", deadline, true);
			// System.out.println();
			vwwObj.doSave(true);
			// System.out.println("Updated Workflow Deadline and Status");
		}
	}

	public static void updateWorkflowTat(String uqid, String status, VWSession vwsession) throws ParseException {
		VWWorkObject vwwObj = null;
		int queryFlags = VWRoster.QUERY_NO_OPTIONS;
		String queryFilter = "F_WobNum=:A";
		// String wobNum = "WELCOME HOMES";
		Object[] substitutionVars = { new VWWorkObjectNumber(uqid) };
		// Change filter based on requirement String
		String rosterName = "DefaultRoster";
		VWRoster roster = vwsession.getRoster(rosterName);
		VWRosterQuery query = roster.createQuery(null, null, null, queryFlags, queryFilter, substitutionVars,
				VWFetchType.FETCH_TYPE_WORKOBJECT);
		// System.out.println("Total records for Work Item: " + query1.fetchCount());
		while (query.hasNext()) {
			vwwObj = (VWWorkObject) query.next();
			vwwObj.doLock(true);
			vwwObj.setFieldValue("xWorkflowStatus", status, true);
			// System.out.println();
			vwwObj.doSave(true);
			// System.out.println("Updated Workflow Status");
		}
	}

	public static void updateStepMinutesRendered(String uqid, String prevenddate, VWSession vwsession, double minRend,
			List<String[]> holdaylist) throws Exception {
		VWWorkObject vwwObj = null;
		int queryFlags = VWRoster.QUERY_NO_OPTIONS;
		String queryFilter = "F_WobNum=:A";
		Object[] substitutionVars = { new VWWorkObjectNumber(uqid) };
		String rosterName = "DefaultRoster";
		VWRoster roster = vwsession.getRoster(rosterName);
		VWRosterQuery query = roster.createQuery(null, null, null, queryFlags, queryFilter, substitutionVars,
				VWFetchType.FETCH_TYPE_WORKOBJECT);

		String prvdate = prevenddate;
		DateFormat format = new SimpleDateFormat("E MMM d HH:mm:ss z yyyy");
		Date endtime = format.parse(prvdate);
		Calendar calenddate = Calendar.getInstance();
		calenddate.setTime(endtime);
		while (query.hasNext()) {
			vwwObj = (VWWorkObject) query.next();
			vwwObj.doLock(true);
			vwwObj.setFieldValue("xMinutesRendered", minRend + getWorkedMinutes(calenddate, holdaylist), true);
			vwwObj.doSave(true);
		}
	}

	public static void updateIsEmailed(String uqid, VWSession vwsession) {
		VWWorkObject vwwObj = null;
		int queryFlags = VWRoster.QUERY_NO_OPTIONS;
		String queryFilter = "F_WobNum=:A";
		Object[] substitutionVars = { new VWWorkObjectNumber(uqid) };
		String rosterName = "DefaultRoster";
		VWRoster roster = vwsession.getRoster(rosterName);
		VWRosterQuery query = roster.createQuery(null, null, null, queryFlags, queryFilter, substitutionVars,
				VWFetchType.FETCH_TYPE_WORKOBJECT);
		while (query.hasNext()) {
			vwwObj = (VWWorkObject) query.next();
			vwwObj.doLock(true);
			vwwObj.setFieldValue("IsEmailed", 1, true);
			vwwObj.doSave(true);
		}
	}

	public static void updateIsEmailedwf(String uqid, VWSession vwsession) {
		VWWorkObject vwwObj = null;
		int queryFlags = VWRoster.QUERY_NO_OPTIONS;
		String queryFilter = "F_WobNum=:A";
		Object[] substitutionVars = { new VWWorkObjectNumber(uqid) };
		String rosterName = "DefaultRoster";
		VWRoster roster = vwsession.getRoster(rosterName);
		VWRosterQuery query = roster.createQuery(null, null, null, queryFlags, queryFilter, substitutionVars,
				VWFetchType.FETCH_TYPE_WORKOBJECT);
		while (query.hasNext()) {
			vwwObj = (VWWorkObject) query.next();
			vwwObj.doLock(true);
			vwwObj.setFieldValue("IsEmailedWF", 1, true);
			vwwObj.doSave(true);
		}
	}

	public static void updateIsEmailed(String uqid, String fv, VWSession vwsession) {
		VWWorkObject vwwObj = null;
		int queryFlags = VWRoster.QUERY_NO_OPTIONS;
		String queryFilter = "F_WobNum=:A";
		Object[] substitutionVars = { new VWWorkObjectNumber(uqid) };
		String rosterName = "DefaultRoster";
		VWRoster roster = vwsession.getRoster(rosterName);
		VWRosterQuery query = roster.createQuery(null, null, null, queryFlags, queryFilter, substitutionVars,
				VWFetchType.FETCH_TYPE_WORKOBJECT);
		while (query.hasNext()) {
			vwwObj = (VWWorkObject) query.next();
			vwwObj.doLock(true);
			vwwObj.setFieldValue(fv, 1, true);
			vwwObj.doSave(true);
		}
	}

	
	//Utility helpers -----------------------
	public static double isStepReturned(String WobNum, String CurStepName, VWSession vwSession) {

		VWLog vwLog = null; // Note the distinction between "log" method (below) and a "vwLog" VWLog object
		VWLogQuery logQuery = null;
		VWLogElement logElement = null;
		String[] fieldNames = null;
		Object value = null;

		String queryFilter = "F_WobNum=:A AND CurStepName=:B AND F_EventType=:C";
		Object[] substitutionVars = { new VWWorkObjectNumber(WobNum), CurStepName, "1050" };
		int queryFlags = VWLog.QUERY_NO_OPTIONS;

		vwLog = vwSession.fetchEventLog("DefaultEventLog");
		logQuery = vwLog.startQuery(null, null, null, queryFlags, queryFilter, substitutionVars);
		double minRen = 0;

		while (logQuery.hasNext()) {
			logElement = logQuery.next();
			fieldNames = logElement.getFieldNames();
			for (int i = 0; i < fieldNames.length; i++) {
				if (fieldNames[i] != null) {
					value = logElement.getFieldValue(fieldNames[i]);
					if (fieldNames[i].toString().equals("xMinutesRendered")) {
						minRen = minRen + (double) value;
					}
				}
			}
		}
		return minRen;

	}

	public static boolean isbetweenEmailTime() throws ParseException {
		Date date = new Date();
		Calendar st = Calendar.getInstance();
		Calendar et = Calendar.getInstance();
		st.setTime(date);
		st.set(Calendar.HOUR_OF_DAY, 0);
		st.set(Calendar.MINUTE, 45);
		st.set(Calendar.SECOND, 0);
		st.set(Calendar.MILLISECOND, 0);
		st.add(Calendar.HOUR, 7);
		Date start = st.getTime();
		et.setTime(date);
		et.set(Calendar.HOUR_OF_DAY, 0);
		et.set(Calendar.MINUTE, 0);
		et.set(Calendar.SECOND, 0);
		et.set(Calendar.MILLISECOND, 0);
		et.add(Calendar.HOUR, 8);
		Date end = et.getTime();
		if (date.after(start) && date.before(end) && !isWeekEnd(st)) {
			return true;
		} else {
			return false;
		}
	}

	public static double minRenCal(String startdate, Connection conn) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("E MMM d HH:mm:ss z yyyy");
		Calendar start = Calendar.getInstance();
        Calendar cur = Calendar.getInstance();
        start.setTime(sdf.parse(startdate));
        cur.setTime(new Date());
        int workingDays = 0;
        double wDaysInMins = 0;
        double wDaysOd = 0;
		
		if(isWorkingHours(start.getTime())) {
			if(sameDay(start, cur)) {
				wDaysOd = ChronoUnit.SECONDS.between(start.toInstant(),cur.toInstant());
			}
		}else{
			Calendar ewh = Calendar.getInstance();
			ewh.set(Calendar.HOUR_OF_DAY, 0);
			ewh.set(Calendar.MINUTE, 0);
			ewh.set(Calendar.SECOND, 0);
			ewh.set(Calendar.MILLISECOND, 0);
			ewh.add(Calendar.HOUR, 17);
			if(start.before(ewh)) {
				ewh.set(Calendar.HOUR_OF_DAY, 0);
				ewh.set(Calendar.MINUTE, 0);
				ewh.set(Calendar.SECOND, 0);
				ewh.set(Calendar.MILLISECOND, 0);
				ewh.add(Calendar.HOUR, 8);
			}else {
				ewh.set(Calendar.HOUR_OF_DAY, 0);
				ewh.set(Calendar.MINUTE, 0);
				ewh.set(Calendar.SECOND, 0);
				ewh.set(Calendar.MILLISECOND, 0);
				ewh.add(Calendar.DATE, 1);
				ewh.add(Calendar.HOUR, 8);
			}
		}

        while(!start.after(cur))
        {
            int day = start.get(Calendar.DAY_OF_WEEK);
            day = day + 2;
            if (day > 7){
                day = day -7;
            }
            if ((day != Calendar.SATURDAY) && (day != Calendar.SUNDAY))
                workingDays++;
            start.add(Calendar.DATE, 1);
        }
        
        wDaysInMins = ((((workingDays * 9) * 60) > isHoliday(start, hoList(conn)))
					? ((workingDays * 9) * 60) - isHoliday(start, hoList(conn))
					: 0.0);
        
		return wDaysInMins + wDaysOd;
	}
	
	public static double accumMinRen(String WobNum, String CurStepName, Map<Integer, Map<String, String>> logdata, List<String[]> holdaylist) throws Exception {
		double minRen = 0;
		Iterator<Map.Entry<Integer, Map<String, String>>> entries = logdata.entrySet().iterator();
		while(entries.hasNext()) {
			Map.Entry<Integer, Map<String, String>> entry = entries.next();
			if(entry.getValue().get("StepName").toString().equals(CurStepName) && entry.getValue().get("F_WobNum").toString().equals(WobNum)) {
				minRen = minRen + getWorkedMinutes(entry.getValue().get("PrevStartTime").toString(),  entry.getValue().get("PrevEndTime").toString(),holdaylist);
			}
		}
		return minRen;
	}
	
	public static int getWorkedMinutes(Calendar stDate,List<String[]> holdaylist) throws Exception {
		Calendar start = (Calendar) stDate;
		Calendar end = Calendar.getInstance();
		end.setTime(new Date());

		if (start.get(Calendar.HOUR_OF_DAY) < BEGINWORKHOUR) {
			start.set(Calendar.HOUR_OF_DAY, BEGINWORKHOUR);
			start.set(Calendar.MINUTE, 0);
		}  else if(start.get(Calendar.HOUR_OF_DAY) > ENDWORKHOUR) {
			start.set(Calendar.HOUR_OF_DAY, ENDWORKHOUR);
			start.set(Calendar.MINUTE, 0);
		}

		if (end.get(Calendar.HOUR_OF_DAY) >= ENDWORKHOUR) {
			end.set(Calendar.HOUR_OF_DAY, ENDWORKHOUR);
			end.set(Calendar.MINUTE, 0);
		}

		int holMins = 0;
		int workedMins = 0;
		while (!sameDay(start, end)) {
			workedMins += workedMinsDay(start);
			holMins += isHoliday(start, holdaylist);
			start.add(Calendar.DAY_OF_MONTH, 1);

			start.set(Calendar.HOUR_OF_DAY, BEGINWORKHOUR);
			start.set(Calendar.MINUTE, 0);

		}
		workedMins += (end.get(Calendar.MINUTE) - start.get(Calendar.MINUTE))
				+ ((end.get(Calendar.HOUR_OF_DAY) - start.get(Calendar.HOUR_OF_DAY)) * 60);

		return ((workedMins < holMins) ? workedMins - holMins : 0);
	}
	
	public static int getWorkedMinutes(String stDate, String edDate, List<String[]> holdaylist) throws Exception {
		DateFormat orgFormat = new SimpleDateFormat("E MMM d HH:mm:ss z yyyy");

		
		Calendar start = Calendar.getInstance() ;
		Calendar end = Calendar.getInstance();
		start.setTime(orgFormat.parse(stDate));
		end.setTime(orgFormat.parse(edDate));
		

		if (start.get(Calendar.HOUR_OF_DAY) < BEGINWORKHOUR) {
			start.set(Calendar.HOUR_OF_DAY, BEGINWORKHOUR);
			start.set(Calendar.MINUTE, 0);
		}  else if(start.get(Calendar.HOUR_OF_DAY) > ENDWORKHOUR) {
			start.set(Calendar.HOUR_OF_DAY, ENDWORKHOUR);
			start.set(Calendar.MINUTE, 0);
		}

		if (end.get(Calendar.HOUR_OF_DAY) >= ENDWORKHOUR) {
			end.set(Calendar.HOUR_OF_DAY, ENDWORKHOUR);
			end.set(Calendar.MINUTE, 0);
		}

		int holMins = 0;
		int workedMins = 0;
		while (!sameDay(start, end)) {
			workedMins += workedMinsDay(start);
			holMins += isHoliday(start, holdaylist);
			start.add(Calendar.DAY_OF_MONTH, 1);

			start.set(Calendar.HOUR_OF_DAY, BEGINWORKHOUR);
			start.set(Calendar.MINUTE, 0);

		}
		workedMins += (end.get(Calendar.MINUTE) - start.get(Calendar.MINUTE))
				+ ((end.get(Calendar.HOUR_OF_DAY) - start.get(Calendar.HOUR_OF_DAY)) * 60);

		return ((workedMins < holMins) ? workedMins - holMins : 0);
	}

	private static int workedMinsDay(Calendar start) {
		if ((start.get(Calendar.DAY_OF_WEEK) == 1) || (start.get(Calendar.DAY_OF_WEEK) == 7))
			return 0;
		else
			return (60 - start.get(Calendar.MINUTE)) + ((ENDWORKHOUR - start.get(Calendar.HOUR_OF_DAY) - 1) * 60);
	}

	public static boolean sameDay(Calendar cal1, Calendar cal2) {
		boolean sameDay = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
		
		return sameDay;
	}
	
	public static String regDateFormat(String date) throws ParseException {
		
		DateFormat orgFormat = new SimpleDateFormat("E MMM d HH:mm:ss z yyyy");
		Date rdate = orgFormat.parse(date);
		DateFormat targTormat = new SimpleDateFormat("EE, MMMM d, yyyy h:mm a");
		
		
		return targTormat.format(rdate);
	}
	
	public static Boolean isDatedef(String date) throws ParseException {
		DateFormat orgFormat = new SimpleDateFormat("E MMM d HH:mm:ss z yyyy");
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(orgFormat.parse(date));
		
		if(cal1.get(Calendar.YEAR) < 2000) {
			return true;
		}else {
			return false;
		}
	}
}
