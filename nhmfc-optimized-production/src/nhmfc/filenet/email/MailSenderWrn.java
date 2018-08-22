package nhmfc.filenet.email;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSenderWrn implements Runnable {

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

	public void setParameters(Integer rec, String subject, String wobNum, String processName,
			String deadline, String launchDate, String fmess, String hmess) {
		
		if (rec == 151) { // Admin
			this.to = "nora.encina.dcms@nhmfc.gov.ph";
		} else if (rec == 152) { // AMD
			this.to = "rovic.mande.dcms@nhmfc.gov.ph";
		} else if (rec == 170) { // AUDITRISK
			this.to = "editha.staines.dcms@nhmfc.gov.ph";
		} else if (rec == 173) { // AVD
			this.to = "jeffrey.calimlim.dcms@nhmfc.gov.ph";
		} else if (rec == 155) { // BOARD
			this.to = "dante.patapat.dcms@nhmfc.gov.ph";
		} else if (rec == 165) { // CAD
			this.to = "cynthia.mayo.dcms@nhmfc.gov.ph";
		} else if (rec == 156) { // CAMG
			this.to = "jake.alpajaro.dcms@nhmfc.gov.ph";
		} else if (rec == 174) { // CASH
			this.to = "alma.delachica.dcms@nhmfc.gov.ph";
		} else if (rec == 163) { // CBD
			this.to = "margie.barbiran.dcms@nhmfc.gov.ph";
		} else if (rec == 171) { // COCMD
			this.to = "jun.redondo.dcms@nhmfc.gov.ph";
		} else if (rec == 162) { // CORPLAN
			this.to = "jaena.rosal.dcms@nhmfc.gov.ph";
		} else if (rec == 166) { // CSSG
			this.to = "lourdes.bacani.dcms@nhmfc.gov.ph";
		} else if (rec == 159) { // CUSTODIAN
			this.to = "loida.raymundo.dcms@nhmfc.gov.ph";
		} else if (rec == 169) { // FAMG
			this.to = "romeo.roldan.dcms@nhmfc.gov.ph";
		} else if (rec == 150) { // FUB
			this.to = "felix.bustos.dcms@nhmfc.gov.ph";
		} else if (rec == 157) { // GSD
			this.to = "nepo.reyes.dcms@nhmfc.gov.ph";
		} else if (rec == 158) { // HRD
			this.to = "gwen.remorin.dcms@nhmfc.gov.ph";
		} else if (rec == 154) { // INSURANCE
			this.to = "marluna.sarmiento@nhmfc.gov.ph";
		} else if (rec == 164) { // LEGAL
			this.to = "rodolfo.erbon.dcms@nhmfc.gov.ph";
		} else if (rec == 175) { // MAD
			this.to = "june.redondo.dcms@nhmfc.gov.ph";
		} else if (rec == 161) { // OEVP
			this.to = "june.ramos.dcms@nhmfc.gov.ph";
		} else if (rec == 52) { // OP
			this.to = "pia.bustos.dcms@nhmfc.gov.ph";
		} else if (rec == 172) { // PAMD
			this.to = "nemia.benosa@nhmfc.gov.ph";
		} else if (rec == 153) { // RASD
			this.to = "noe.valencia.dcms@nhmfc.gov.ph";
		} else if (rec == 160) { // RECORDS
			this.to = "lizalyn.candelario.dcms@nhmfc.gov.ph";
		} else if (rec == 168) { // SPD
			this.to = "carol.ccortez.dcms@nhmfc.gov.ph";
		}
		
		setTo(to);
		setLaunchDate(launchDate);
		setWobNum(wobNum);
		setProcessName(processName);
		setDeadline(deadline);
		setFsubject(subject);
		setfMess(fmess);
		sethMess(hmess);
		
		setSubject( getfMess() +": "+ getProcessName() +" | Subject: "+ getFsubject() +" | Launch Date: " + getLaunchDate());
		setBody("<font color=red>This is a System Generated Warning Email.</font><br><br>" + gethMess() +"<b>"+ getProcessName()
				+ " </b>is going to be tagged OVERDUE if it has not been completed before " + getDeadline() + "<br>"
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