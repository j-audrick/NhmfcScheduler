package nhmfc.filenet.xml;

/**
 *
 * @author Mico
 */
public enum HourUnits {

	 MINUTE("Set the turn around time unit to minutes"), HOUR("Set the turn around time unit to hours"), DAY(
			"Set the turn around time unit to days"), YEAR("Set the turn around time unit to year");

	private String description;

	private HourUnits(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
