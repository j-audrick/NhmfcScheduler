package nhmfc.filenet.xmlcusto;

import com.ibm.json.java.JSONArray;
import com.ibm.json.java.JSONObject;
import java.util.List;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="choiceList")
public class WFDataFieldChoiceList
{
  @Element(name="choiceListName", required=false)
  private String name;
  @ElementList(name="choiceList", inline=false, required=false)
  private List<WFDataFieldChoice> choices;
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public List<WFDataFieldChoice> getChoices()
  {
    return this.choices;
  }
  
  public void setChoices(List<WFDataFieldChoice> choices)
  {
    this.choices = choices;
  }
  
  public JSONObject asJSON()
  {
    JSONObject obj = new JSONObject();
    JSONArray arr = new JSONArray();
    for (WFDataFieldChoice choice : this.choices) {
      arr.add(choice.asJSONObject());
    }
    obj.put("choices", arr);
    return obj;
  }
  
  public JSONArray asJSONArray()
  {
    JSONArray arr = new JSONArray();
    for (WFDataFieldChoice choice : this.choices) {
      arr.add(choice.asJSONObject());
    }
    return arr;
  }
}
