package nhmfc.filenet.xmlcusto;

import java.util.HashMap;
import java.util.Map;
import org.simpleframework.xml.ElementMap;
import org.simpleframework.xml.Root;

@Root(name="wfDataFieldMap", strict=false)
public class WFDataFieldMap
{
  @ElementMap(name="map", inline=false, key="dataField", required=false, entry="fieldDef", empty=true)
  private Map<String, WFDataFieldDef> map;
  
  public WFDataFieldMap()
  {
    this.map = new HashMap();
  }
  
  public Map<String, WFDataFieldDef> getMap()
  {
    return this.map;
  }
  
  public void setMap(Map<String, WFDataFieldDef> map)
  {
    this.map = map;
  }
}
