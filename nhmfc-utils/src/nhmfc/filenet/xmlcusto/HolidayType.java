package nhmfc.filenet.xmlcusto;

public enum HolidayType
{
  REGULAR("Regular Calendar Holiday"),  GOVERNMENT_MANDATED("Government Mandated Holiday");
  
  private final String description;
  
  private HolidayType(String description)
  {
    this.description = description;
  }
  
  public String getDescription()
  {
    return this.description;
  }
}
