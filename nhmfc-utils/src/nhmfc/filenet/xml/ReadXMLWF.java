package nhmfc.filenet.xml;



import java.io.InputStream;
import java.io.PrintStream;
import java.util.Map;
import javax.security.auth.Subject;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.CycleStrategy;
import org.simpleframework.xml.strategy.Strategy;

import com.filenet.api.core.Connection;
import com.filenet.api.core.Document;
import com.filenet.api.core.Domain;
import com.filenet.api.core.Factory;
import com.filenet.api.core.ObjectStore;
import com.filenet.api.exception.EngineRuntimeException;
import com.filenet.api.util.UserContext;

public class ReadXMLWF
{
  public static Connection getCEConnectionHTTP()
  {
    Connection connection = null;
    try
    {
      String UserName = "P8Admin";
      String Password = "IBMFileNetP8";
      String Url = "http://192.168.20.31:9080/wsi/FNCEWS40MTOM/";
      String Stanza = "FileNetP8WSI";
      connection = Factory.Connection.getConnection(Url);
      
      Subject subject = UserContext.createSubject(connection, UserName, Password, Stanza);
      UserContext uc = UserContext.get();
      uc.pushSubject(subject);
      return connection;
    }
    catch (EngineRuntimeException engineRuntimeException)
    {
      System.out.println(
        "RuntimeException occured while getting the connection = " + engineRuntimeException.getMessage());
      
      engineRuntimeException.printStackTrace();
    }
    return connection;
  }
  
  public static Map<String, ProcessEngineObject> getXMLWFdata()
    throws Exception
  {
    Domain domain = Factory.Domain.fetchInstance(getCEConnectionHTTP(), null, null);
    ObjectStore objStore = Factory.ObjectStore.fetchInstance(domain, "DCMS", null);
    
    Document d1 = Factory.Document.fetchInstance(objStore, "/test/devWorkflows5.xml", null);
    
    InputStream stream = d1.accessContentStream(0);
    
    Strategy strategy = new CycleStrategy("id", "ref");
    Serializer serializer = new Persister(strategy);
    
    PEObjectWorkflow peowf = (PEObjectWorkflow)serializer.read(PEObjectWorkflow.class, stream);
    Map<String, ProcessEngineObject> data = peowf.getWorkflowData();
    return data;
  }
}
