package nhmfc.filenet;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class Step {
	@Attribute
    private String step;
    
	@Element
	private int stepTAT;

	public int getStepTAT() {
		return stepTAT;
	}

	public void setStepTAT(int stepTAT) {
		this.stepTAT = stepTAT;
	}
	
    public String getStep() {
		return step;
	}

	public void setStep(String step) {
		this.step = step;
	}
}
