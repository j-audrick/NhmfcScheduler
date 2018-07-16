package nhmfc.filenet;

import java.io.File;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class Run {

	public static void main(String[] args) throws Exception {
		//Action to letters and borrowers Process
		try {
			Serializer serializer = new Persister();
			Workflow wf = new Workflow();
			wf.setTitle("Title 1");
			wf.setWfTAT(10);

			Step step = new Step();
			step.setStep("Step 1");
			step.setStepTAT(2);
			
			wf.setStep(step);
			
			File result = new File("ActionToLettersAndBorrowers.xml");
			serializer.write(wf, result);
		} catch (Exception e) {
			e.printStackTrace();
		}


		try {
	          Serializer serializer = new Persister();
	          File source = new File("wf.xml");
	          Workflow wf = serializer.read(Workflow.class, source);
	 
	          System.out.println(wf.getTitle());
	          System.out.println(wf.getWfTAT());
	          System.out.println(wf.getStep().getStep());
	          System.out.println(wf.getStep().getStepTAT());
	       
	     } catch (Exception e) {
	          e.printStackTrace();
	     }
	}

}
