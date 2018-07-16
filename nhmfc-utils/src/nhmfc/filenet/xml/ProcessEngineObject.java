package nhmfc.filenet.xml;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

/**
 *
 * ProcessEngineObject is a representation of a workflow including its steps and
 * submaps. This class also contains the turn around time information needed for
 * each workflow/step. Also, this contains the overdue email information needed
 * to send the email notification.
 * 
 * @author Mico
 */
@Root(name = "ProcessEngineObject", strict = false)
public class ProcessEngineObject {

	@Element(name = "name", required = true)
	private String name;

	@Element(name = "workflowID", required = false)
	private String workflowID;

	@Element(name = "turnAroundTimeAmount", required = true)
	private Integer turnAroundTimeAmount;

	@Element(name = "turnAroundTimeUnit", required = true)
	private HourUnits turnAroundTimeUnit;

	@Element(name = "type")
	private ObjectType type;

	@Element(name = "overdueEmail", required = false)
	private Email overdueEmail;

	@ElementList(name = "steps", inline = false, empty = true, required = false)
	private List<ProcessEngineObject> steps;

	public ProcessEngineObject(@Element (name = "name") String name, @Element (name = "turnAroundTimeAmount") Integer turnAroundTimeAmount, 
			@Element (name = "turnAroundTimeUnit") HourUnits turnAroundTimeUnit, @Element (name = "type") ObjectType type) {
		this.name = name;
		this.turnAroundTimeAmount = turnAroundTimeAmount;
		this.turnAroundTimeUnit = turnAroundTimeUnit;
		this.type = type;
	}

	public ProcessEngineObject(@Element (name = "name") String name, @Element (name = "turnAroundTimeAmount") Integer turnAroundTimeAmount, 
			@Element (name = "turnAroundTimeUnit") HourUnits turnAroundTimeUnit, @Element (name = "type") ObjectType type, 
			@Element (name = "overdueEmail") Email overdueEmail) {
		this.name = name;
		this.turnAroundTimeAmount = turnAroundTimeAmount;
		this.turnAroundTimeUnit = turnAroundTimeUnit;
		this.type = type;
		this.overdueEmail = overdueEmail;
	}

	public ProcessEngineObject(@Element (name = "name") String name, @Element (name = "workflowID") String workflowID, 
			@Element (name = "turnAroundTimeAmount") Integer turnAroundTimeAmount, @Element (name = "turnAroundTimeUnit") HourUnits turnAroundTimeUnit, 
			@Element (name = "type") ObjectType type, @Element (name = "overdueEmail") Email overdueEmail) {
		this.name = name;
		this.workflowID = workflowID;
		this.turnAroundTimeAmount = turnAroundTimeAmount;
		this.turnAroundTimeUnit = turnAroundTimeUnit;
		this.type = type;
		this.overdueEmail = overdueEmail;
	}

	public void addStep(ProcessEngineObject step) {
		if (steps == null) {
			steps = new ArrayList<ProcessEngineObject>();
		}
		if (!steps.contains(step)) {
			steps.add(step);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWorkflowID() {
		return workflowID;
	}

	public void setWorkflowID(String workflowID) {
		this.workflowID = workflowID;
	}

	public Integer getTurnAroundTimeAmount() {
		return turnAroundTimeAmount;
	}

	public void setTurnAroundTimeAmount(Integer turnAroundTimeAmount) {
		this.turnAroundTimeAmount = turnAroundTimeAmount;
	}

	public HourUnits getTurnAroundTimeUnit() {
		return turnAroundTimeUnit;
	}

	public void setTurnAroundTimeUnit(HourUnits turnAroundTimeUnit) {
		this.turnAroundTimeUnit = turnAroundTimeUnit;
	}

	public ObjectType getType() {
		return type;
	}

	public void setType(ObjectType type) {
		this.type = type;
	}

	public Email getOverdueEmail() {
		return overdueEmail;
	}

	public void setOverdueEmail(Email overdueEmail) {
		this.overdueEmail = overdueEmail;
	}

	public List<ProcessEngineObject> getSteps() {
		return steps;
	}

}
