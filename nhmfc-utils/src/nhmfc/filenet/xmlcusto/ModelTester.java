package nhmfc.filenet.xmlcusto;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.CycleStrategy;
import org.simpleframework.xml.strategy.Strategy;

public class ModelTester {
	public static void main(String[] args) throws Exception {
		Strategy strat = new CycleStrategy("id", "ref");
		Serializer serializer = new Persister(strat);

		File f = new File("C:\\users\\QPS-AUDRICK\\Desktop\\wfMapNew.xml");
		WFDataFieldMap wfMap = (WFDataFieldMap) serializer.read(WFDataFieldMap.class, f);

		WFDataFieldDef def = new WFDataFieldDef();
		
		def.setName("Document_Type"); //datafieldname
		
		WFDataFieldChoiceList allowedAtt = new WFDataFieldChoiceList(); //AllowedValues
		List<WFDataFieldChoice> allChoice =  new ArrayList<WFDataFieldChoice>();
		List<String> allowedValues = new ArrayList<String>();
		//WFDataFieldChoiceList wfdd = new WFDataFieldChoiceList();
		WFDataFieldChoice wfdc1 = new WFDataFieldChoice();
		WFDataFieldChoice wfdc2 = new WFDataFieldChoice();
		WFDataFieldChoice wfdc3 = new WFDataFieldChoice();
		WFDataFieldChoice wfdc4 = new WFDataFieldChoice();
		WFDataFieldChoice wfdc5 = new WFDataFieldChoice();
		WFDataFieldChoice wfdc6 = new WFDataFieldChoice();
		WFDataFieldChoice wfdc7 = new WFDataFieldChoice();
		WFDataFieldChoice wfdc8 = new WFDataFieldChoice();
		WFDataFieldChoice wfdc9 = new WFDataFieldChoice();
		WFDataFieldChoice wfdc10 = new WFDataFieldChoice();
		WFDataFieldChoice wfdc11 = new WFDataFieldChoice();
		WFDataFieldChoice wfdc12 = new WFDataFieldChoice();
		WFDataFieldChoice wfdc13 = new WFDataFieldChoice();
		WFDataFieldChoice wfdc14 = new WFDataFieldChoice();
		WFDataFieldChoice wfdc15 = new WFDataFieldChoice();
		WFDataFieldChoice wfdc16 = new WFDataFieldChoice();
		WFDataFieldChoice wfdc17 = new WFDataFieldChoice();
		WFDataFieldChoice wfdc18 = new WFDataFieldChoice();
		WFDataFieldChoice wfdc19 = new WFDataFieldChoice();
		WFDataFieldChoice wfdc20 = new WFDataFieldChoice();
		WFDataFieldChoice wfdc21 = new WFDataFieldChoice();
		WFDataFieldChoice wfdc22 = new WFDataFieldChoice();
		WFDataFieldChoice wfdc23 = new WFDataFieldChoice();
		WFDataFieldChoice wfdc24 = new WFDataFieldChoice();
		WFDataFieldChoice wfdc25 = new WFDataFieldChoice();
		WFDataFieldChoice wfdc26 = new WFDataFieldChoice();
		WFDataFieldChoice wfdc27 = new WFDataFieldChoice();
		
		wfdc1.setName("ADMIN");
		wfdc1.setActive("true");
		wfdc1.setValue("nora.encina.dcms@nhmfc.gov.ph");
		allChoice.add(wfdc1);
		
		wfdc2.setName("AMD");
		wfdc2.setActive("true");
		wfdc2.setValue("rovic.mande.dcms@nhmfc.gov.ph");
		allChoice.add(wfdc2);
		
		wfdc3.setName("AUDITRISK");
		wfdc3.setActive("true");
		wfdc3.setValue("editha.staines.dcms@nhmfc.gov.ph");
		allChoice.add(wfdc3);
		
		wfdc4.setName("AVD");
		wfdc4.setActive("true");
		wfdc4.setValue("jeffrey.calimlim.dcms@nhmfc.gov.ph");
		allChoice.add(wfdc4);
		
		wfdc5.setName("BOARD");
		wfdc5.setActive("true");
		wfdc5.setValue("dante.patapat.dcms@nhmfc.gov.ph");
		allChoice.add(wfdc5);
		
		wfdc6.setName("CAD");
		wfdc6.setActive("true");
		wfdc6.setValue("cynthia.mayo.dcms@nhmfc.gov.ph");
		allChoice.add(wfdc6);
		
		wfdc7.setName("CAMG");
		wfdc7.setActive("true");
		wfdc7.setValue("jake.alpajaro.dcms@nhmfc.gov.ph");
		allChoice.add(wfdc7);
		
		wfdc8.setName("CASH");
		wfdc8.setActive("true");
		wfdc8.setValue("alma.delachica.dcms@nhmfc.gov.ph");
		allChoice.add(wfdc8);
		
		wfdc9.setName("CBD");
		wfdc9.setActive("true");
		wfdc9.setValue("margie.barbiran.dcms@nhmfc.gov.ph");
		allChoice.add(wfdc9);
		
		wfdc10.setName("COCMD");
		wfdc10.setActive("true");
		wfdc10.setValue("jun.redondo.dcms@nhmfc.gov.ph");
		allChoice.add(wfdc10);
		
		wfdc11.setName("CORPLAN");
		wfdc11.setActive("true");
		wfdc11.setValue("jaena.rosal.dcms@nhmfc.gov.ph");
		allChoice.add(wfdc11);
		
		wfdc12.setName("CSSG");
		wfdc12.setActive("true");
		wfdc12.setValue("lourdes.bacani.dcms@nhmfc.gov.ph");
		allChoice.add(wfdc12);
		
		wfdc13.setName("CUSTODIAN");
		wfdc13.setActive("true");
		wfdc13.setValue("loida.raymundo.dcms@nhmfc.gov.ph");
		allChoice.add(wfdc13);
		
		wfdc14.setName("FAMG");
		wfdc14.setActive("true");
		wfdc14.setValue("romeo.roldan.dcms@nhmfc.gov.ph");
		allChoice.add(wfdc14);
		
		wfdc15.setName("FUB");
		wfdc15.setActive("true");
		wfdc15.setValue("felix.bustos.dcms@nhmfc.gov.ph");
		allChoice.add(wfdc15);
		
		wfdc16.setName("GSD");
		wfdc16.setActive("true");
		wfdc16.setValue("nepo.reyes.dcms@nhmfc.gov.ph");
		allChoice.add(wfdc16);
		
		wfdc18.setName("INSURANCE");
		wfdc18.setActive("true");
		wfdc18.setValue("marluna.sarmiento.dcms@nhmfc.gov.ph");
		allChoice.add(wfdc18);
		
		wfdc19.setName("LEGAL");
		wfdc19.setActive("true");
		wfdc19.setValue("rodolfo.erbon.dcms@nhmfc.gov.ph");
		allChoice.add(wfdc19);
		
		wfdc20.setName("MAD");
		wfdc20.setActive("true");
		wfdc20.setValue("mark.sumisim.dcms@nhmfc.gov.ph");
		allChoice.add(wfdc20);
		
		wfdc21.setName("OEVP");
		wfdc21.setActive("true");
		wfdc21.setValue("june.ramos.dcms@nhmfc.gov.ph");
		allChoice.add(wfdc21);
		
		wfdc22.setName("OP");
		wfdc22.setActive("true");
		wfdc22.setValue("pia.bustos.dcms@nhmfc.gov.ph");
		allChoice.add(wfdc22);
		
		wfdc23.setName("PAMD");
		wfdc23.setActive("true");
		wfdc23.setValue("nemia.benosa.dcms@nhmfc.gov.ph");
		allChoice.add(wfdc23);
		
		wfdc24.setName("RASD");
		wfdc24.setActive("true");
		wfdc24.setValue("noe.valencia.dcms@nhmfc.gov.ph");
		allChoice.add(wfdc24);
		
		wfdc25.setName("RECORDS");
		wfdc25.setActive("true");
		wfdc25.setValue("lizalyn.candelario.dcms@nhmfc.gov.ph");
		allChoice.add(wfdc25);
		
		wfdc17.setName("SG");
		wfdc17.setActive("true");
		wfdc17.setValue("rose.jose.dcms@nhmfc.gov.ph");
		allChoice.add(wfdc17);
		
		wfdc26.setName("SPD");
		wfdc26.setActive("true");
		wfdc26.setValue("carol.ccortez.dcms@nhmfc.gov.ph");
		allChoice.add(wfdc26);
		
		wfdc27.setName("TSD");
		wfdc27.setActive("true");
		wfdc27.setValue("luisa.favila.dcms@nhmfc.gov.ph");
		allChoice.add(wfdc27);
		
		WFDataFieldChoice wfdce1 = new WFDataFieldChoice();
		WFDataFieldChoice wfdce2 = new WFDataFieldChoice();
		WFDataFieldChoice wfdce3 = new WFDataFieldChoice();
		WFDataFieldChoice wfdce4 = new WFDataFieldChoice();
		WFDataFieldChoice wfdce5 = new WFDataFieldChoice();
		WFDataFieldChoice wfdce6 = new WFDataFieldChoice();
		WFDataFieldChoice wfdce7 = new WFDataFieldChoice();
		WFDataFieldChoice wfdce8 = new WFDataFieldChoice();
		WFDataFieldChoice wfdce9 = new WFDataFieldChoice();
		WFDataFieldChoice wfdce10 = new WFDataFieldChoice();
		WFDataFieldChoice wfdce11= new WFDataFieldChoice();
		WFDataFieldChoice wfdce12 = new WFDataFieldChoice();
		WFDataFieldChoice wfdce13 = new WFDataFieldChoice();
		WFDataFieldChoice wfdce14 = new WFDataFieldChoice();
		WFDataFieldChoice wfdce15 = new WFDataFieldChoice();
		WFDataFieldChoice wfdce16 = new WFDataFieldChoice();
		WFDataFieldChoice wfdce17 = new WFDataFieldChoice();
		WFDataFieldChoice wfdce18 = new WFDataFieldChoice();
		WFDataFieldChoice wfdce19 = new WFDataFieldChoice();
		WFDataFieldChoice wfdce20 = new WFDataFieldChoice();
		WFDataFieldChoice wfdce21 = new WFDataFieldChoice();
		WFDataFieldChoice wfdce22 = new WFDataFieldChoice();
		WFDataFieldChoice wfdce23 = new WFDataFieldChoice();
		WFDataFieldChoice wfdce24 = new WFDataFieldChoice();
		WFDataFieldChoice wfdce25 = new WFDataFieldChoice();
		WFDataFieldChoice wfdce26 = new WFDataFieldChoice();
		WFDataFieldChoice wfdce27 = new WFDataFieldChoice();
		
		wfdce1.setName("ADMIN Escalation");
		wfdce1.setActive("true");
		wfdce1.setValue("lourdes.bacani.dcms@nhmfc.gov.ph");
		allChoice.add(wfdce1);
		
		wfdce2.setName("AMD Escalation");
		wfdce2.setActive("true");
		wfdce2.setValue("romeo.roldan.dcms@nhmfc.gov.ph");
		allChoice.add(wfdce2);
		
		wfdce3.setName("AUDITRISK Escalation");
		wfdce3.setActive("true");
		wfdce3.setValue("june.ramos.dcms@nhmfc.gov.ph");
		allChoice.add(wfdce3);
		
		wfdce4.setName("AVD Escalation");
		wfdce4.setActive("true");
		wfdce4.setValue("lourdes.bacani.dcms@nhmfc.gov.ph");
		allChoice.add(wfdce4);
		
		wfdce5.setName("BOARD Escalation");
		wfdce5.setActive("true");
		wfdce5.setValue("felix.bustos.dcms@nhmfc.gov.ph");
		allChoice.add(wfdce5);
		
		wfdce6.setName("CAD Escalation");
		wfdce6.setActive("true");
		wfdce6.setValue("romeo.roldan.dcms@nhmfc.gov.phh");
		allChoice.add(wfdce6);
		
		wfdce7.setName("CAMG Escalation");
		wfdce7.setActive("true");
		wfdce7.setValue("june.ramos.dcms@nhmfc.gov.ph");
		allChoice.add(wfdce7);
		
		wfdce8.setName("CASH Escalation");
		wfdce8.setActive("true");
		wfdce8.setValue("romeo.roldan.dcms@nhmfc.gov.ph");
		allChoice.add(wfdce8);
		
		wfdce9.setName("CBD Escalation");
		wfdce9.setActive("true");
		wfdce9.setValue("romeo.roldan.dcms@nhmfc.gov.ph");
		allChoice.add(wfdce9);
		
		wfdce10.setName("COCMD Escalation");
		wfdce10.setActive("true");
		wfdce10.setValue("jake.alpajaro.dcms@nhmfc.gov.ph");
		allChoice.add(wfdce10);
		
		wfdce11.setName("CORPLAN Escalation");
		wfdce11.setActive("true");
		wfdce11.setValue("felix.bustos.dcms@nhmfc.gov.ph");
		allChoice.add(wfdce11);
		
		wfdce12.setName("CSSG Escalation");
		wfdce12.setActive("true");
		wfdce12.setValue("june.ramos.dcms@nhmfc.gov.ph");
		allChoice.add(wfdce12);
		
		wfdce13.setName("CUSTODIAN Escalation");
		wfdce13.setActive("true");
		wfdce13.setValue("romeo.roldan.dcms@nhmfc.gov.ph");
		allChoice.add(wfdce13);
		
		wfdce14.setName("FAMG Escalation");
		wfdce14.setActive("true");
		wfdce14.setValue("june.ramos.dcms@nhmfc.gov.ph");
		allChoice.add(wfdce14);
		
		wfdce15.setName("FUB Escalation");
		wfdce15.setActive("true");
		wfdce15.setValue("felix.bustos.dcms@nhmfc.gov.ph");
		allChoice.add(wfdce15);
		
		wfdce16.setName("GSD Escalation");
		wfdce16.setActive("true");
		wfdce16.setValue("lourdes.bacani.dcms@nhmfc.gov.ph");
		allChoice.add(wfdce16);
		
		wfdce18.setName("INSURANCE Escalation");
		wfdce18.setActive("true");
		wfdce18.setValue("june.ramos.dcms@nhmfc.gov.ph");
		allChoice.add(wfdce18);
		
		wfdce19.setName("LEGAL Escalation");
		wfdce19.setActive("true");
		wfdce19.setValue("lourdes.bacani.dcms@nhmfc.gov.ph");
		allChoice.add(wfdce19);
		
		wfdce20.setName("MAD Escalation");
		wfdce20.setActive("true");
		wfdce20.setValue("rose.jose.dcms@nhmfc.gov.ph");
		allChoice.add(wfdce20);
		
		wfdce21.setName("OEVP Escalation");
		wfdce21.setActive("true");
		wfdce21.setValue("felix.bustos.dcms@nhmfc.gov.ph");
		allChoice.add(wfdce21);
		
		wfdce22.setName("OP Escalation");
		wfdce22.setActive("true");
		wfdce22.setValue("felix.bustos.dcms@nhmfc.gov.ph");
		allChoice.add(wfdce22);
		
		wfdce23.setName("PAMD Escalation");
		wfdce23.setActive("true");
		wfdce23.setValue("felix.bustos.dcms@nhmfc.gov.ph");
		allChoice.add(wfdce23);
		
		wfdce24.setName("RASD Escalation");
		wfdce24.setActive("true");
		wfdce24.setValue("noe.valencia.dcms@nhmfc.gov.ph");
		allChoice.add(wfdce24);
		
		wfdce25.setName("RECORDS Escalation");
		wfdce25.setActive("true");
		wfdce25.setValue("jake.alpajaro.dcms@nhmfc.gov.ph");
		allChoice.add(wfdce25);
		
		wfdce17.setName("SG Escalation");
		wfdce17.setActive("true");
		wfdce17.setValue("felix.bustos.dcms@nhmfc.gov.ph");
		allChoice.add(wfdce17);
		
		wfdce26.setName("SPD Escalation");
		wfdce26.setActive("true");
		wfdce26.setValue("lourdes.bacani.dcms@nhmfc.gov.ph");
		allChoice.add(wfdce26);
		
		wfdce27.setName("TSD Escalation");
		wfdce27.setActive("true");
		wfdce27.setValue("rose.jose.dcms@nhfmc.gov.ph");
		allChoice.add(wfdce27);
		//allowedAtt.add("scen2");
		
		allowedAtt.setName("Email List");
		allowedAtt.setChoices(allChoice);
		Map<String, String> attribs = new HashMap(); 
		//attribs.put("choiceListNested", "true"); //setting of validation attribs
		//attribs.put("displayMode","readonly");
		attribs.put("required", "true");
		
		//attribs.put("maxEntry", "300");
		//attribs.put("maxLength","300");
		//attribs.put("format", ".*");

		allowedValues.add("Board Material");
		allowedValues.add("Board Resolution");
		allowedValues.add("Certified True Copy of Board Resolution");
		allowedValues.add("Circulars");
		allowedValues.add("Company wide Memos");
		allowedValues.add("Group Orders");
		allowedValues.add("IMR");
		allowedValues.add("Investment Docs");
		allowedValues.add("Letters");
		allowedValues.add("Loan Documents");
		allowedValues.add("Minutes of the Meeting");
		allowedValues.add("Office Order");
		allowedValues.add("PO");
		allowedValues.add("Secretary Certificate");
		allowedValues.add("Special orders");
		allowedValues.add("TCT");
		allowedValues.add("Transmittal List");

		
		def.setAttribs(attribs);
		def.setAllowedValues(allowedValues);
		
		wfMap.getMap().put(def.getName(), def); //[Name][WFDataFieldDef]
		serializer.write(wfMap, f);
	}
	public static WFDataFieldDef wfDDF() {
		
		return null;
	}
}