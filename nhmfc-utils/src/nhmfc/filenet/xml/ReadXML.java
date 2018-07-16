package nhmfc.filenet.xml;

import java.io.File;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.CycleStrategy;
import org.simpleframework.xml.strategy.Strategy;

public class ReadXML {

	public static ProcessEngineObject getXMLdata (String xmlname) throws Exception {
		Strategy strategy = new CycleStrategy("id", "ref");
		Serializer serializer = new Persister(strategy);
		File source = new File("xmls/Workflow/"+ xmlname +".xml");
		ProcessEngineObject op = serializer.read(ProcessEngineObject.class, source);
		return op;
	}
	
	
}
