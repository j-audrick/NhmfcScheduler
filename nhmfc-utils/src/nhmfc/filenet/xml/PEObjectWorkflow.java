package nhmfc.filenet.xml;

import java.util.Map;

import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

@Root(name = "PEObjectWorkflow", strict = false)
public class PEObjectWorkflow {
	@ElementMap (name = "WorkflowData", required = true)
	private Map <String, ProcessEngineObject> WorkflowData;

	public Map<String, ProcessEngineObject> getWorkflowData() {
		return WorkflowData;
	}

	public void setWorkflowData(Map<String, ProcessEngineObject> workflowData) {
		WorkflowData = workflowData;
	}
	
	public PEObjectWorkflow(@ElementMap (name = "WorkflowData") Map <String, ProcessEngineObject> WorkflowData) {
		this.WorkflowData = WorkflowData;
	}
}
