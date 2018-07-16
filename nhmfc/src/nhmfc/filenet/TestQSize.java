package nhmfc.filenet;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import javax.security.auth.Subject;
import com.filenet.api.collection.ObjectStoreSet;
import com.filenet.api.core.Connection;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.util.UserContext;
import filenet.vw.api.*;

public class TestQSize {
	public static void main(String[] args) throws ParseException {
		System.setProperty("java.security.auth.login.config", "D:\\workplace\\JAR\\jaas.conf.WSI");

		// User Information
		String userName = "P8Admin";
		String password = "IBMFileNetP8";
		// Connection Point
		String connectionPoint = "pecp1";
		// Create a Process Engine Session Object
		VWSession myPESession = new VWSession();
		// Set Bootstrap Content Engine URI
		myPESession.setBootstrapCEURI("http://192.168.20.31:9080/wsi/FNCEWS40MTOM/");
		// Start try-catch block to handle exception when log onto PE
		try {
			myPESession.logon(userName, password, connectionPoint);
			System.out.println("Connected");
		} catch (VWException vwe) {
			System.out.println("nVWException Key: " + vwe.getKey() + "n");
			System.out.println("VWException Cause Class Name: " + vwe.getCauseClassName() + "n");
			System.out.println("VWException CauseDescription: " + vwe.getCauseDescription() + "n");
			System.out.println("VWException Message: " + vwe.getMessage());
		}
		System.out.println("-------------");

		// Workflow name to launch
		String workflowName = "Sample workflow 1";
		// Retrieve transfered work classes
		String[] workClassNames = myPESession.fetchWorkClassNames(true);
		for (int i = 0; i < workClassNames.length; i++) {
			System.out.println(workClassNames[i]);
		}
		System.out.println("-------------");
		// // Launch Workflow
		// VWStepElement stepElement = myPESession.createWorkflow(workflowName);
		// // Get and Set Workflow parameters for the Launch Step
		// System.out.println(stepElement.getWorkflowName());
		//
		// String[] properties = stepElement.getParameterNames();
		// for (int i = 0; i < properties.length; i++) {
		// System.out.println(properties[i]);
		// }

		System.out.println("END");
		System.out.println("-------------");
		// Dispatch Workflow Launch Step
		// stepElement.doDispatch();
		String workspaceID = String
				.valueOf(myPESession.fetchWorkflowSignature("Actions to Letters Process").getWorkspaceId());
		// System.out.println(workspaceID);
		VWWorkflowDefinition defn = myPESession.fetchWorkflowDefinition(Integer.parseInt(workspaceID),
				"Actions to Letters Process", false);

		VWMapDefinition vwMapDefinition = defn.getMainMap();
		VWMapNode[] mapNode = vwMapDefinition.getSteps();
		
		System.out.println(defn.getName());
		for (int i = 0; i < mapNode.length; i++) {
			if (mapNode[i].toString().equals("NotifAndLog")  || mapNode[i].toString().equals("Log")) {
				
			} else {
				System.out.println(mapNode[i]);
			}
		}


		System.out.println("-------------");

		// MOFICATION OF WORK ITEM

		System.out.println("-------------");

		System.out.println("---------------------END-----------------------");

		// // Queue Name
		// String queueName = "Inbox";
		// // Retrieve the Queue to be searched and Queue depth
		// VWQueue queue = myPESession.getQueue(queueName);
		// System.out.println("Queue Depth: " + queue.fetchCount()); // Perform Query
		// VWQueueQuery queueQuery = queue.createQuery(null, null, null, 0, null, null,
		// VWFetchType.FETCH_TYPE_WORKOBJECT);
		// // Process Results
		// while (queueQuery.hasNext()) {
		// VWWorkObject queueElement = (VWWorkObject) queueQuery.next();
		//
		// System.out.println(queueElement.getWorkflowName());
		//
		// }

	}
}
