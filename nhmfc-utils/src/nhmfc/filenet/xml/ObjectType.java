package nhmfc.filenet.xml;

/**
 *
 * @author Mico
 */
public enum ObjectType {

	MAINMAP("This is the main map of the workflow"), SUBMAP("This is a submap within the workflow"), STEP(
			"This is a step in the workflow");

	private String description;

	private ObjectType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
