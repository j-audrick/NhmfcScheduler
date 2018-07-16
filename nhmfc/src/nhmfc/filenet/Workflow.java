package nhmfc.filenet;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

public class Workflow {
	@Attribute
    private String title;
	
	@Element
	private int wfTAT;
	
	@Element
    private Step step;
	
    
    public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
    public Step getStep() {
		return step;
	}
	public void setStep(Step step) {
		this.step = step;
	}
    
	public int getWfTAT() {
		return wfTAT;
	}

	public void setWfTAT(int wfTAT) {
		this.wfTAT = wfTAT;
	}



	

	
  
}
