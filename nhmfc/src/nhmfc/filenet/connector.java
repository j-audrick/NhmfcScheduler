package nhmfc.filenet;

//Import.
import java.util.Iterator;
import javax.security.auth.Subject;
import com.filenet.api.collection.ObjectStoreSet;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.UserContext;
import filenet.vw.api.*;

public class connector {
	public static void main(String[] args) {

	}

	public VWSession peSession() {
		System.setProperty("java.security.auth.login.config", "D:\\workplace\\JAR\\jaas.conf.WSI");
		// User Information
		String userName = "P8Admin";
		String password = "IBMFileNetP8";
		// Connection Point
		String connectionPoint = "P8ConnPt1";

		String strAppURI1 = "http://192.168.100.235:9080/wsi/FNCEWS40MTOM/";
		System.out.println("[ENTER  PEManager getPESession()]");
		VWSession peSession = null;

		try {
			peSession = new VWSession();
			peSession.setBootstrapCEURI(strAppURI1);

			peSession.logon(userName, password, connectionPoint);
			String sn = peSession.getPEServerName();
			System.out.println("++++++++++++++++" + sn);

			System.out.println("PE session established:" + peSession);
		} catch (VWException e) {
			System.out.println("Exception occured while establishing PE session.");
			e.printStackTrace();

		}
		System.out.println("[Exit PEManager getPESession()]");
		return peSession;
	}
}