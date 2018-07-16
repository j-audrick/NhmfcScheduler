package nhmfc.filenet.xmlcusto;


import com.ibm.json.java.JSONObject;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name="Choice")
public class WFDataFieldChoice
{
  @Element(name="name", required=false)
  private String name;
  @Element(name="active", required=false)
  private String active;
  @Element(name="value", required=false)
  private String value;
  @Element(name="choiceList", required=false)
  private WFDataFieldChoiceList choiceList;
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getActive()
  {
    return this.active;
  }
  
  public void setActive(String active)
  {
    this.active = active;
  }
  
  public String getValue()
  {
    return this.value;
  }
  
  public void setValue(String value)
  {
    this.value = value;
  }
  
  public WFDataFieldChoiceList getChoiceList()
  {
    return this.choiceList;
  }
  
  public void setChoiceList(WFDataFieldChoiceList choiceList)
  {
    this.choiceList = choiceList;
  }
  
  public JSONObject asJSONObject()
  {
    JSONObject obj = new JSONObject();
    obj.put("displayName", this.name);
    obj.put("active", this.active);
    if ((this.value != null) && (this.choiceList == null)) {
      obj.put("value", this.value);
    } else if ((this.value == null) && (this.choiceList != null)) {
      obj.put("choices", this.choiceList.asJSONArray());
    }
    return obj;
  }
}
