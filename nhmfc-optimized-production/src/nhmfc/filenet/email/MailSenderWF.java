package nhmfc.filenet.email;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSenderWF implements Runnable {

	private String to = "";
	private String subject = "";
	
	// Link
	private String wobNum = "";

	// Subject
	private String processName = "";
	private String launchDate = "";
	private String deadline = "";
	private String body = "";
	private String fsubject = "";

	private String fMess = "";
	private String hMess = "";
	
	String host = "mail.nhmfc.gov.ph";
	final String from = "dcms@nhmfc.gov.ph";
	final String password = "filenet";

	@Override
	public void run() {
		this.sendMail();
	}
	
	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}
	public String getLaunchDate() {
		return launchDate;
	}

	public void setLaunchDate(String launchDate) {
		this.launchDate = launchDate;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getWobNum() {
		return wobNum;
	}

	public void setWobNum(String wobNum) {
		this.wobNum = wobNum;
	}

	public void setParameters(String to, String subject, String wobNum, String processName,
			String deadline, String launchDate, String fmess, String hmess) {
		
		setTo(to);
		setLaunchDate(launchDate);
		setWobNum(wobNum);
		setProcessName(processName);
		setDeadline(deadline);
		setFsubject(subject);
		setfMess(fmess);
		sethMess(hmess);
		
		setSubject( getfMess() +": "+ getProcessName() +" | Subject: "+ getFsubject() +" | Launch Date: " + getLaunchDate());
		setBody("<font color=red>This is a System Generated Escalation Email.</font><br><br>" + gethMess() +"<b>"+ getProcessName()
				+ " </b>has been escalated because it has not been completed before " + getDeadline() + "<br>"
				+ " Please click the link below.<br><br><br>"
				+ " <a href='http://192.168.20.31:9081/navigator/stepprocessor.jsp?queueName=Inbox&isoRegion=1&wobNum="
				+ getWobNum() + "'>Step Assignment...</a><br><br><br><br>");

	}

	private void sendMail() {
		try {
			Session session = getSession();
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));
			
			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(getTo()));

			// Set Subject: header field
			message.setSubject(getSubject());

			// Now set the actual message
			message.setContent(getBody(), "text/html");

			// Send message
			Transport transport = session.getTransport("smtp");
			transport.connect(host, from, password);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Session getSession() {
		// Get system properties
		Properties properties = System.getProperties();
		// Setup mail server
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.user", from);
		properties.put("mail.smtp.password", password);
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.ssl.trust", host);

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);
		return session;
	}

	public String getFsubject() {
		return fsubject;
	}

	public void setFsubject(String fsubject) {
		this.fsubject = fsubject;
	}

	public String getfMess() {
		return fMess;
	}

	public void setfMess(String fMess) {
		this.fMess = fMess;
	}

	public String gethMess() {
		return hMess;
	}

	public void sethMess(String hMess) {
		this.hMess = hMess;
	}
}