package nhmfc.filenet.main;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.filenet.api.core.Connection;
import com.filenet.api.util.UserContext;

import nhmfc.filenet.xml.*;
import nhmfc.filenet.utils.*;
import nhmfc.filenet.email.*;

import filenet.vw.api.*;

public class DCMSLogs {
	public static void main(String args[]) throws Exception {
		
		VWSession vwSession = null;
		MailSenderWF escaEmailwf = new MailSenderWF();
		MailSenderWI escaEmailwi = new MailSenderWI();
		MailSenderWrn escaEmailwrn = new MailSenderWrn();
		vwSession = peSession();
		Connection ceCon = Utilities.getCEConnectionHTTP();
		if (vwSession != null) {
			List<String[]> holdaylist = Utilities.hoList(ceCon);
			Date start = new Date();
			System.out.println("--------------------------------------------------------------------------------");
			System.out.println("Running @ " + start);
			Map<Integer, Map<String, String>> logdata = Utilities.logFetch(vwSession);
			Date endd = new Date();
			System.out.println("Itr Elapsed for Log Fetch: " + ChronoUnit.MILLIS.between(start.toInstant(), endd.toInstant()) +" Millis");
			Map<Integer, Map<String, String>> data = Utilities.RosterFetch(vwSession);
			Iterator<Map.Entry<Integer, Map<String, String>>> entries = data.entrySet().iterator();
			System.out.println("Running . . . .");
			// one entries = 1 row
			while (entries.hasNext()) {
				Map.Entry<Integer, Map<String, String>> entry = entries.next();
				Map<String, ProcessEngineObject> wfdata = ReadXMLWF.getXMLWFdata();
				Iterator<Map.Entry<String, ProcessEngineObject>> wfentries = wfdata.entrySet().iterator();
				while (wfentries.hasNext()) {
					Map.Entry<String, ProcessEngineObject> wfentry = wfentries.next();
					ProcessEngineObject op = wfentry.getValue();
					for (ProcessEngineObject obj : op.getSteps()) {
						if (entry.getValue().get("StepName").toString().equals(obj.getName()) && !entry.getValue().get("F_Overdue").toString().equals("3")) {
							
							int Tat = 0;
							String tatunit = obj.getTurnAroundTimeUnit().toString();
							if (tatunit.equals("DAY")) {
								Tat = obj.getTurnAroundTimeAmount() * 32400;
							} else if (tatunit.equals("HOUR")) {
								Tat = obj.getTurnAroundTimeAmount() * 3600;
							} else if (tatunit.equals("MINUTE")) {
								Tat = obj.getTurnAroundTimeAmount() * 60;
							}

							int wfTat = 0;
							String wftatunit = op.getTurnAroundTimeUnit().toString();
							if (wftatunit.equals("DAY")) {
								wfTat = op.getTurnAroundTimeAmount() * 32400;
							} else if (wftatunit.equals("HOUR")) {
								wfTat = op.getTurnAroundTimeAmount() * 3600;
							} else if (wftatunit.equals("MINUTE")) {
								wfTat = op.getTurnAroundTimeAmount() * 60;
							}
							
							String ddate = "";
							double stepRen = Utilities.accumMinRen(entry.getValue().get("F_WobNum").toString(),entry.getValue().get("StepName").toString(),logdata,holdaylist);
							
							double tat = (Tat > (60 * stepRen) 
									? Tat - (60 * stepRen) 
									: 0.0);
							if(stepRen > 0) {
								ddate = Utilities.computeDueDateWHours(entry.getValue().get("PrevEndTime").toString(), tat, holdaylist);
							}else {
								ddate = Utilities.computeDueDateWHours(entry.getValue().get("PrevEndTime").toString(), Tat, holdaylist);
							}
							
							Date dateNow = new Date();
							DateFormat format = new SimpleDateFormat("E MMM d HH:mm:ss z yyyy");
							Date duedate = format.parse(ddate);
							String xStatus = "";
							if (dateNow.before(duedate)) {
								xStatus = "Ongoing On-time";
							} else {
								xStatus = "Ongoing Overdue";
								if(entry.getValue().get("IsEmailed").toString().equals("0")) {
									if (obj.getOverdueEmail().getTo().toString().equals("noemail.dcms@nhmfc.gov.ph")) {
										escaEmailwi.setParameters(Integer.parseInt(entry.getValue().get("F_Originator").toString())
												,entry.getValue().get("F_Subject").toString()
												,entry.getValue().get("F_WobNum").toString()
												,entry.getValue().get("StepName").toString()
												,(Utilities.isDatedef(entry.getValue().get("xDeadline").toString()) ? Utilities.regDateFormat(duedate.toString()) : Utilities.regDateFormat(entry.getValue().get("xDeadline").toString()))
												,Utilities.regDateFormat(entry.getValue().get("F_StartTime").toString())
												,"Work Item Escalation"
												,"This Work Item: ");
										Thread t = new Thread(escaEmailwi);
										t.start();
										System.out.println("*Emailed Step: MASA");
									} else if (obj.getOverdueEmail().getTo().toString().equals("bounduser.dcms@nhmfc.gov.ph")) {
										escaEmailwi.setParameters(Integer.parseInt(entry.getValue().get("F_BoundUser").toString())
												,entry.getValue().get("F_Subject").toString()
												,entry.getValue().get("F_WobNum").toString()
												,entry.getValue().get("StepName").toString()
												,(Utilities.isDatedef(entry.getValue().get("xDeadline").toString()) ? Utilities.regDateFormat(duedate.toString()) : Utilities.regDateFormat(entry.getValue().get("xDeadline").toString()))
												,Utilities.regDateFormat(entry.getValue().get("F_StartTime").toString())
												,"Work Item Escalation"
												,"This Work Item: ");
										Thread t = new Thread(escaEmailwi);
										t.start();
										System.out.println("*Emailed Step: Bound User");
									} else {
										escaEmailwi.setParameters(obj.getOverdueEmail().getTo()
												,entry.getValue().get("F_Subject").toString()
												,entry.getValue().get("F_WobNum").toString()
												,entry.getValue().get("StepName").toString()
												,(Utilities.isDatedef(entry.getValue().get("xDeadline").toString()) ? Utilities.regDateFormat(duedate.toString()) : Utilities.regDateFormat(entry.getValue().get("xDeadline").toString()))
												,Utilities.regDateFormat(entry.getValue().get("F_StartTime").toString())
												,"Work Item Escalation"
												,"This Work Item: ");
										Thread t = new Thread(escaEmailwi);
										t.start();
										System.out.println("*Emailed Step: " + obj.getName());
									}
									Utilities.updateIsEmailed(entry.getValue().get("F_WobNum").toString(),  vwSession);
								}
								if (Utilities.isbetweenEmailTime()){
									if (obj.getOverdueEmail().getTo().toString().equals("noemail.dcms@nhmfc.gov.ph")) {
										escaEmailwi.setParameters(Integer.parseInt(entry.getValue().get("F_Originator").toString())
												,entry.getValue().get("F_Subject").toString()
												,entry.getValue().get("F_WobNum").toString()
												,entry.getValue().get("StepName").toString()
												,(Utilities.isDatedef(entry.getValue().get("xDeadline").toString()) ? Utilities.regDateFormat(duedate.toString()) : Utilities.regDateFormat(entry.getValue().get("xDeadline").toString()))
												,Utilities.regDateFormat(entry.getValue().get("F_StartTime").toString())
												,"Work Item Escalation"
												,"This Work Item: ");
										Thread t = new Thread(escaEmailwi);
										t.start();
										System.out.println("*Emailed Step daily: MASA");
									} else if (obj.getOverdueEmail().getTo().toString().equals("bounduser.dcms@nhmfc.gov.ph")) {
										escaEmailwi.setParameters(Integer.parseInt(entry.getValue().get("F_BoundUser").toString())
												,entry.getValue().get("F_Subject").toString()
												,entry.getValue().get("F_WobNum").toString()
												,entry.getValue().get("StepName").toString()
												,(Utilities.isDatedef(entry.getValue().get("xDeadline").toString()) ? Utilities.regDateFormat(duedate.toString()) : Utilities.regDateFormat(entry.getValue().get("xDeadline").toString()))
												,Utilities.regDateFormat(entry.getValue().get("F_StartTime").toString())
												,"Work Item Escalation"
												,"This Work Item: ");
										Thread t = new Thread(escaEmailwi);
										t.start();
										System.out.println("*Emailed Step Daily: Bound User");
									} else {
										escaEmailwi.setParameters(obj.getOverdueEmail().getTo()
												,entry.getValue().get("F_Subject").toString()
												,entry.getValue().get("F_WobNum").toString()
												,entry.getValue().get("StepName").toString()
												,(Utilities.isDatedef(entry.getValue().get("xDeadline").toString()) ? Utilities.regDateFormat(duedate.toString()) : Utilities.regDateFormat(entry.getValue().get("xDeadline").toString()))
												,Utilities.regDateFormat(entry.getValue().get("F_StartTime").toString())
												,"Work Item Escalation"
												,"This Work Item: ");
										Thread t = new Thread(escaEmailwi);
										t.start();
										System.out.println("*Emailed Step Daily: " + obj.getName());
									}
								}
							}
							String wfStatus = "";
							String wfddate = Utilities.computeDueDateWHours(entry.getValue().get("F_StartTime").toString(), wfTat, holdaylist);
							Date wfduedate = format.parse(wfddate);
							if (dateNow.before(wfduedate)) {
								wfStatus = "Ongoing On-time";
							} else {
								wfStatus = "Ongoing Overdue";
								if(entry.getValue().get("IsEmailedWF").toString().equals("0")) {
									escaEmailwf.setParameters(op.getOverdueEmail().getTo()
											,entry.getValue().get("F_Subject").toString()
											,entry.getValue().get("F_WobNum").toString()
											,entry.getValue().get("F_Class").toString()
											,(Utilities.isDatedef(entry.getValue().get("xWorkflowDeadline").toString()) ? Utilities.regDateFormat(wfduedate.toString()) : Utilities.regDateFormat(entry.getValue().get("xWorkflowDeadline").toString()))
											,Utilities.regDateFormat(entry.getValue().get("F_StartTime").toString())
											,"Workflow Escalation"
											,"This Workflow: ");
									Thread t = new Thread(escaEmailwf);
									t.start();
									Utilities.updateIsEmailedwf(entry.getValue().get("F_WobNum").toString(),vwSession);
									System.out.println("**Emailed WF");
								}
								if (Utilities.isbetweenEmailTime()) {
									escaEmailwf.setParameters(op.getOverdueEmail().getTo()
											,entry.getValue().get("F_Subject").toString()
											,entry.getValue().get("F_WobNum").toString()
											,entry.getValue().get("F_Class").toString()
											,(Utilities.isDatedef(entry.getValue().get("xWorkflowDeadline").toString()) ? Utilities.regDateFormat(wfduedate.toString()) : Utilities.regDateFormat(entry.getValue().get("xWorkflowDeadline").toString()))
											,Utilities.regDateFormat(entry.getValue().get("F_StartTime").toString())
											,"Workflow Escalation"
											,"This Workflow: ");
									Thread t = new Thread(escaEmailwf);
									t.start();
									System.out.println("**Emailed WF daily");
								}
							}
							if(entry.getValue().get("IsEmailedWrn") == "") {
								double wrntat = 0;
								wrntat = Tat * 0.75;
								
								String due = Utilities.computeDueDateWHours(entry.getValue().get("PrevEndTime"), wrntat, holdaylist);
								Date ddue = format.parse(due);
								
								if(dateNow.before(ddue)) {
									Utilities.updateIsEmailed(entry.getValue().get("F_WobNum").toString(), "IsEmailedWrn",  vwSession);
									escaEmailwrn.setParameters(Integer.parseInt(entry.getValue().get("F_").toString())
											,entry.getValue().get("F_Subject").toString()
											,entry.getValue().get("F_WobNum").toString()
											,entry.getValue().get("StepName").toString()
											,(Utilities.isDatedef(entry.getValue().get("xDeadline").toString()) ? Utilities.regDateFormat(duedate.toString()) : Utilities.regDateFormat(entry.getValue().get("xDeadline").toString()))
											,Utilities.regDateFormat(entry.getValue().get("F_StartTime").toString())
											,"Work Item Deadline Warning"
											,"This Work Item: ");
									Thread t = new Thread(escaEmailwrn);
									t.start();
									System.out.println("*Emailed Step Warning: " + obj.getName());
								}
							}
							
							Utilities.updateStepMinutesRendered(entry.getValue().get("F_WobNum").toString(), entry.getValue().get("PrevEndTime").toString(), vwSession, stepRen, holdaylist);
							if(Utilities.isDatedef(entry.getValue().get("xDeadline").toString())) {
								Utilities.updateWorkItems(entry.getValue().get("F_WobNum").toString(), ddate, xStatus, vwSession);
								Utilities.updateWorkflowTat(entry.getValue().get("F_WobNum").toString(), wfddate, wfStatus, vwSession);
							}else {
								Utilities.updateWorkItems(entry.getValue().get("F_WobNum").toString(), xStatus, vwSession);
								Utilities.updateWorkflowTat(entry.getValue().get("F_WobNum").toString(), wfStatus, vwSession);
							}
						}
					}
				}
			}
			Date end = new Date();
			System.out.println("Run Complete @ " + new Date());
			if(ChronoUnit.SECONDS.between(start.toInstant(), end.toInstant()) > 60) {
				System.out.println("Total Elapsed:" + ChronoUnit.MINUTES.between(start.toInstant(), end.toInstant()) + "mins " + ChronoUnit.SECONDS.between(start.toInstant(), end.toInstant()) % 60 +"seconds and "
						+ ChronoUnit.MILLIS.between(start.toInstant(), end.toInstant()) % 1000 + "millis");
			}else {
				System.out.println("Total Elapsed:" + ChronoUnit.SECONDS.between(start.toInstant(), end.toInstant()) +"seconds and "
						+ ChronoUnit.MILLIS.between(start.toInstant(), end.toInstant()) % 1000 + "millis");
			}
			System.out.println("--------------------------------------------------------------------------------");
		}
		vwSession.logoff();
		//pop con content engine
		UserContext.get().popSubject();
	}
	public static VWSession peSession() {
		System.setProperty("java.security.auth.login.config", "C:\\Users\\QPS-AUDRICK\\Desktop\\JAR\\jaas.conf.WSI");
		// User Information
		String userName = "P8Admin";
		String password = "IBMFileNetP8";
		// Connection Point
		String connectionPoint = "pecp1";

		String strAppURI1 = "http://192.168.20.31:9080/wsi/FNCEWS40MTOM/";
		//System.out.println("[ENTER  PEManager getPESession()]");
		VWSession peSession = null;

		try {
			peSession = new VWSession();
			peSession.setBootstrapCEURI(strAppURI1);

			peSession.logon(userName, password, connectionPoint);

		} catch (VWException e) {
			e.printStackTrace();

		}
		return peSession;
	}
}
