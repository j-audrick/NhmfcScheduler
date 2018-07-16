package nhmfc.filenet.xmlcusto;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

@Root(name="wfDataFieldDef", strict=false)
public class WFDataFieldDef
{
  @Element(name="name", required=true)
  private String name;
  @ElementMap(name="attribs", key="key", value="value", required=false)
  private Map<String, String> attribs;
  @ElementList(name="allowedValues", inline=false, empty=true, required=false)
  private List<String> allowedValues;
  @ElementList(name="allowedObjectValues", inline=false, empty=true, required=false)
  private List<Object> allowedObjectValues;
  @Element(name="choiceList", required=false)
  private WFDataFieldChoiceList choiceList;
  
  @ElementList(name="lstChoice", inline=false, empty=true, required=false) //created
  private List<WFDataFieldChoice> lstChoice;
  
  public WFDataFieldDef()
  {
    this.allowedValues = new ArrayList();
  }
  
  public List<Object> getAllowedObjectValues()
  {
    return this.allowedObjectValues;
  }
  
  public void setAllowedObjectValues(List<Object> allowedObjectValues)
  {
    this.allowedObjectValues = allowedObjectValues;
  }
  
  public Map<String, String> getAttribs()
  {
    return this.attribs;
  }
  
  public void setAttribs(Map<String, String> attribs)
  {
    this.attribs = attribs;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public List<String> getAllowedValues()
  {
    return this.allowedValues;
  }
  
  public void setAllowedValues(List<String> allowedValues)
  {
    this.allowedValues = allowedValues;
  }
  
  public List<WFDataFieldChoice> getLstChoice() //created
  {
	  return this.lstChoice;
  }
  
  public void setLstChoiceList(List<WFDataFieldChoice> lstChoice) //created
  {
	  this.lstChoice = lstChoice;
  }
  
  public JSONArray asJSONAllowedValues()
  {
    if (this.allowedValues == null) {
      return null;
    }
    JSONArray arr = new JSONArray();
    for (String allowedValue : this.allowedValues) {
      arr.add(allowedValue);
    }
    return arr;
  }
  
  public WFDataFieldChoiceList getChoiceList()
  {
    return this.choiceList;
  }
  
  public void setChoiceList(WFDataFieldChoiceList choiceList)
  {
    this.choiceList = choiceList;
  }
  
/*  public JSONObject asJSONChoiceList()
  {
    if ((this.lstChoice == null) || (this.lstChoice.isEmpty())) {
      return null;
    }
    JSONObject jso = new JSONObject();
    JSONArray arr = new JSONArray();
    for (WFDataFieldChoice lstChoice : this.lstChoice)
    {
      JSONObject obj = new JSONObject();
      obj.put("displayName", lstChoice.getName());
      obj.put("active", lstChoice.getActive());
      obj.put("value", lstChoice.getValue());
      arr.add(obj);
    }
    jso.put("choices", arr);
    return jso;
  }*/
  public JSONObject asJSONChoiceList()
  {
    if ((this.allowedValues == null) || (this.allowedValues.isEmpty())) {
      return null;
    }
    JSONObject jso = new JSONObject();
    JSONArray arr = new JSONArray();
    for (String allowedValue : this.allowedValues)
    {
      JSONObject obj = new JSONObject();
      obj.put("displayName", allowedValue);
      obj.put("active", "true");
      obj.put("value", allowedValue);
      arr.add(obj);
    }
    jso.put("choices", arr);
    return jso;
  }
  //created
  public JSONObject asJSONChoiceListWN() 
  {
	  if ((this.allowedValues == null) || (this.allowedValues.isEmpty())) {
	      return null;
	    }
	    JSONObject jso = new JSONObject();
	    JSONArray arr = new JSONArray();
	    for (String allowedValue : this.allowedValues)
	    {
	      JSONObject obj = new JSONObject();
	      obj.put("displayName", allowedValue);
	      obj.put("active", "true");
	      obj.put("value", allowedValue);
	      arr.add(obj);
	    }
	    jso.put("choices", arr);
	    return jso;
  }
}
