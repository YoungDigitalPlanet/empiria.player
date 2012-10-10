package eu.ydp.empiria.player.client.controller.variables.processor.item;

import java.util.HashMap;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.xml.client.XMLParser;

import eu.ydp.empiria.player.client.controller.variables.objects.outcome.Outcome;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;

public class DefaultVariableProcessorTest extends GWTTestCase {

	@Override
	public String getModuleName() {
	    return "eu.ydp.empiria.player.Player";
	}
	
	public void testVariableEnsuringNoModules(){
		DefaultVariableProcessor proc = new DefaultVariableProcessor();
		
		HashMap<String, Response> responses = new HashMap<String, Response>();
		HashMap<String, Outcome> outcomes = new HashMap<String, Outcome>();

		proc.ensureVariables(responses, outcomes);
		assertTrue(outcomes.containsKey("DONE"));
		assertTrue(outcomes.containsKey("TODO"));
		assertTrue(outcomes.containsKey("DONEHISTORY"));
		assertTrue(outcomes.containsKey("DONECHANGES"));
		assertTrue(outcomes.containsKey("LASTMISTAKEN"));
		assertTrue(outcomes.containsKey("CHECKS"));
		assertTrue(outcomes.containsKey("SHOW_ANSWERS"));
		assertTrue(outcomes.containsKey("RESET"));
		assertTrue(outcomes.containsKey("MISTAKES"));
	}
	
	public void testVariableEnsuringOneModule(){
		DefaultVariableProcessor proc = new DefaultVariableProcessor();
		
		HashMap<String, Response> responses = new HashMap<String, Response>();
		HashMap<String, Outcome> outcomes = new HashMap<String, Outcome>();
		
		String responseXmlString = "<responseDeclaration identifier='RESPONSE' cardinality='single' baseType='identifier'><correctResponse><value>ChoiceA</value></correctResponse></responseDeclaration>";
		Response resp = new Response(XMLParser.parse(responseXmlString).getDocumentElement());
		resp.setModuleAdded();
		responses.put("RESPONSE", resp);

		proc.ensureVariables(responses, outcomes);
		assertTrue(outcomes.containsKey("DONE"));
		assertTrue(outcomes.containsKey("TODO"));
		assertTrue(outcomes.containsKey("DONEHISTORY"));
		assertTrue(outcomes.containsKey("DONECHANGES"));
		assertTrue(outcomes.containsKey("LASTMISTAKEN"));
		assertTrue(outcomes.containsKey("CHECKS"));
		assertTrue(outcomes.containsKey("SHOW_ANSWERS"));
		assertTrue(outcomes.containsKey("RESET"));
		assertTrue(outcomes.containsKey("MISTAKES"));

		assertTrue(outcomes.containsKey("RESPONSE-DONE"));
		assertTrue(outcomes.containsKey("RESPONSE-TODO"));
		assertTrue(outcomes.containsKey("RESPONSE-LASTCHANGE"));
		assertTrue(outcomes.containsKey("RESPONSE-PREVIOUS"));
		assertTrue(outcomes.containsKey("RESPONSE-LASTMISTAKEN"));
		assertTrue(outcomes.containsKey("RESPONSE-DONEHISTORY"));
		assertTrue(outcomes.containsKey("RESPONSE-DONECHANGES"));
		assertTrue(outcomes.containsKey("RESPONSE-MISTAKES"));
	}

	public void testVariableProcessingSingleCorrect(){
		DefaultVariableProcessor proc = new DefaultVariableProcessor();
		
		HashMap<String, Response> responses = new HashMap<String, Response>();
		HashMap<String, Outcome> outcomes = new HashMap<String, Outcome>();
		
		String responseXmlString = "<responseDeclaration identifier='RESPONSE' cardinality='single' baseType='identifier'><correctResponse><value>ChoiceA</value></correctResponse></responseDeclaration>";
		Response resp = new Response(XMLParser.parse(responseXmlString).getDocumentElement());
		resp.setModuleAdded();
		responses.put("RESPONSE", resp);
		
		proc.ensureVariables(responses, outcomes);
		
		resp.values.add("ChoiceA");
		proc.processResponseVariables(responses, outcomes, true);
		assertTrue(outcomes.containsKey("DONE"));
		assertEquals("1", outcomes.get("DONE").values.get(0));
	}
	
	public void testVariableProcessingSingleWong(){
		DefaultVariableProcessor proc = new DefaultVariableProcessor();
		
		HashMap<String, Response> responses = new HashMap<String, Response>();
		HashMap<String, Outcome> outcomes = new HashMap<String, Outcome>();
		
		String responseXmlString = "<responseDeclaration identifier='RESPONSE' cardinality='single' baseType='identifier'><correctResponse><value>ChoiceA</value></correctResponse></responseDeclaration>";
		Response resp = new Response(XMLParser.parse(responseXmlString).getDocumentElement());
		resp.setModuleAdded();
		responses.put("RESPONSE", resp);
		
		proc.ensureVariables(responses, outcomes);
		
		resp.values.add("ChoiceB");
		proc.processResponseVariables(responses, outcomes, true);
		assertTrue(outcomes.containsKey("DONE"));
		assertEquals("0", outcomes.get("DONE").values.get(0));
	}
	
	public void testVariableProcessingyMultipleCorrect(){
		DefaultVariableProcessor proc = new DefaultVariableProcessor();
		
		HashMap<String, Response> responses = new HashMap<String, Response>();
		HashMap<String, Outcome> outcomes = new HashMap<String, Outcome>();
		
		String responseXmlString = "<responseDeclaration identifier='RESPONSE' cardinality='multiple' baseType='identifier'><correctResponse><value>ChoiceA</value><value>ChoiceB</value></correctResponse></responseDeclaration>";
		Response resp = new Response(XMLParser.parse(responseXmlString).getDocumentElement());
		resp.setModuleAdded();
		responses.put("RESPONSE", resp);
		
		proc.ensureVariables(responses, outcomes);
		
		resp.values.add("ChoiceA");
		resp.values.add("ChoiceB");
		proc.processResponseVariables(responses, outcomes, true);
		assertTrue(outcomes.containsKey("DONE"));
		assertEquals("1", outcomes.get("DONE").values.get(0));
	}
	
	public void testVariableProcessingMultipleWrong(){
		DefaultVariableProcessor proc = new DefaultVariableProcessor();
		
		HashMap<String, Response> responses = new HashMap<String, Response>();
		HashMap<String, Outcome> outcomes = new HashMap<String, Outcome>();
		
		String responseXmlString = "<responseDeclaration identifier='RESPONSE' cardinality='multiple' baseType='identifier'><correctResponse><value>ChoiceA</value><value>ChoiceB</value></correctResponse></responseDeclaration>";
		Response resp = new Response(XMLParser.parse(responseXmlString).getDocumentElement());
		resp.setModuleAdded();
		responses.put("RESPONSE", resp);
		
		proc.ensureVariables(responses, outcomes);
		
		resp.values.add("ChoiceA");
		resp.values.add("ChoiceC");
		proc.processResponseVariables(responses, outcomes, true);
		assertTrue(outcomes.containsKey("DONE"));
		assertEquals("0", outcomes.get("DONE").values.get(0));
	}
	
	public void testVariableProcessingyOrderedCorrect(){
		DefaultVariableProcessor proc = new DefaultVariableProcessor();
		
		HashMap<String, Response> responses = new HashMap<String, Response>();
		HashMap<String, Outcome> outcomes = new HashMap<String, Outcome>();
		
		String responseXmlString = "<responseDeclaration identifier='RESPONSE' cardinality='ordered' baseType='identifier'><correctResponse><value>ChoiceA</value><value>ChoiceB</value><value>ChoiceC</value></correctResponse></responseDeclaration>";
		Response resp = new Response(XMLParser.parse(responseXmlString).getDocumentElement());
		resp.setModuleAdded();
		responses.put("RESPONSE", resp);
		
		proc.ensureVariables(responses, outcomes);
		
		resp.values.add("ChoiceA");
		resp.values.add("ChoiceB");
		resp.values.add("ChoiceC");
		proc.processResponseVariables(responses, outcomes, true);
		assertTrue(outcomes.containsKey("DONE"));
		assertEquals("1", outcomes.get("DONE").values.get(0));
	}
	
	public void testVariableProcessingOrderedWrong(){
		DefaultVariableProcessor proc = new DefaultVariableProcessor();
		
		HashMap<String, Response> responses = new HashMap<String, Response>();
		HashMap<String, Outcome> outcomes = new HashMap<String, Outcome>();
		
		String responseXmlString = "<responseDeclaration identifier='RESPONSE' cardinality='ordered' baseType='identifier'><correctResponse><value>ChoiceA</value><value>ChoiceB</value><value>ChoiceC</value></correctResponse></responseDeclaration>";
		Response resp = new Response(XMLParser.parse(responseXmlString).getDocumentElement());
		resp.setModuleAdded();
		responses.put("RESPONSE", resp);
		
		proc.ensureVariables(responses, outcomes);
		
		resp.values.add("ChoiceA");
		resp.values.add("ChoiceC");
		resp.values.add("ChoiceB");
		proc.processResponseVariables(responses, outcomes, true);
		assertTrue(outcomes.containsKey("DONE"));
		assertEquals("0", outcomes.get("DONE").values.get(0));
	}
	
	public void testVariableProcessingOrderedWrong2(){
		DefaultVariableProcessor proc = new DefaultVariableProcessor();
		
		HashMap<String, Response> responses = new HashMap<String, Response>();
		HashMap<String, Outcome> outcomes = new HashMap<String, Outcome>();
		
		String responseXmlString = "<responseDeclaration identifier='RESPONSE' cardinality='ordered' baseType='identifier'><correctResponse><value>ChoiceA</value><value>ChoiceB</value><value>ChoiceC</value></correctResponse></responseDeclaration>";
		Response resp = new Response(XMLParser.parse(responseXmlString).getDocumentElement());
		resp.setModuleAdded();
		responses.put("RESPONSE", resp);
		
		proc.ensureVariables(responses, outcomes);
		
		resp.values.add("ChoiceA");
		proc.processResponseVariables(responses, outcomes, true);
		assertTrue(outcomes.containsKey("DONE"));
		assertEquals("0", outcomes.get("DONE").values.get(0));
	}
	
	public void testVariableProcessingyCommutativeCorrect(){
		DefaultVariableProcessor proc = new DefaultVariableProcessor();
		
		HashMap<String, Response> responses = new HashMap<String, Response>();
		HashMap<String, Outcome> outcomes = new HashMap<String, Outcome>();
		
		String responseXmlString = "<responseDeclaration identifier='RESPONSE' cardinality='commutative' baseType='string'><correctResponse><value>ChoiceA</value><value group=\"x\">ChoiceB</value><value group=\"x\">ChoiceC</value></correctResponse></responseDeclaration>";
		Response resp = new Response(XMLParser.parse(responseXmlString).getDocumentElement());
		resp.setModuleAdded();
		responses.put("RESPONSE", resp);
		
		proc.ensureVariables(responses, outcomes);
		
		resp.values.add("ChoiceA");
		resp.values.add("ChoiceC");
		resp.values.add("ChoiceB");
		proc.processResponseVariables(responses, outcomes, true);
		assertTrue(outcomes.containsKey("DONE"));
		assertEquals("1", outcomes.get("DONE").values.get(0));
	}
	
	public void testCheckingMistakesInOrderedIsWrong() {
		DefaultVariableProcessor proc = new DefaultVariableProcessor();
		
		HashMap<String, Response> responses = new HashMap<String, Response>();
		HashMap<String, Outcome> outcomes = new HashMap<String, Outcome>();
		
		String responseXmlString = "<responseDeclaration baseType='string' cardinality='ordered' identifier='RESPONSE'><correctResponse><value forIndex='0'>ChoiceA</value><value forIndex='1'>ChoiceB</value></correctResponse></responseDeclaration>";
		Response resp = new Response(XMLParser.parse(responseXmlString).getDocumentElement());
		resp.setModuleAdded();
		responses.put("RESPONSE", resp);
		
		proc.ensureVariables(responses, outcomes);
		
		resp.values.add("ChoiceC");
		resp.values.add("ChoiceD");
		outcomes.get("RESPONSE-LASTCHANGE").values = DefaultVariableProcessorHelper.getDifference(responses.get("RESPONSE"), outcomes.get("RESPONSE-PREVIOUS"));
		proc.processResponseVariables(responses, outcomes, true);
		assertTrue(outcomes.containsKey("ERRORS"));
		assertEquals("1", outcomes.get("ERRORS").values.get(0));
	}
	
	public void testCheckingMistakesInOrderedIsCorrect() {
		DefaultVariableProcessor proc = new DefaultVariableProcessor();
		
		HashMap<String, Response> responses = new HashMap<String, Response>();
		HashMap<String, Outcome> outcomes = new HashMap<String, Outcome>();
		
		String responseXmlString = "<responseDeclaration baseType='string' cardinality='ordered' identifier='RESPONSE'><correctResponse><value forIndex='0'>ChoiceA</value><value forIndex='1'>ChoiceB</value></correctResponse></responseDeclaration>";
		Response resp = new Response(XMLParser.parse(responseXmlString).getDocumentElement());
		resp.setModuleAdded();
		responses.put("RESPONSE", resp);
		
		proc.ensureVariables(responses, outcomes);
		
		resp.values.add("ChoiceA");
		resp.values.add("ChoiceB");
		outcomes.get("RESPONSE-LASTCHANGE").values = DefaultVariableProcessorHelper.getDifference(responses.get("RESPONSE"), outcomes.get("RESPONSE-PREVIOUS"));
		proc.processResponseVariables(responses, outcomes, true);
		assertTrue(outcomes.containsKey("ERRORS"));
		assertEquals("0", outcomes.get("ERRORS").values.get(0));
	}
	
	public void testVariableProcessingCommutativeWrong(){
		DefaultVariableProcessor proc = new DefaultVariableProcessor();
		
		HashMap<String, Response> responses = new HashMap<String, Response>();
		HashMap<String, Outcome> outcomes = new HashMap<String, Outcome>();
		
		String responseXmlString = "<responseDeclaration identifier='RESPONSE' cardinality='commutative' baseType='string'><correctResponse><value>ChoiceA</value><value group=\"x\">ChoiceB</value><value group=\"x\">ChoiceC</value></correctResponse></responseDeclaration>";
		Response resp = new Response(XMLParser.parse(responseXmlString).getDocumentElement());
		resp.setModuleAdded();
		responses.put("RESPONSE", resp);
		
		proc.ensureVariables(responses, outcomes);
		
		resp.values.add("ChoiceC");
		resp.values.add("ChoiceB");
		resp.values.add("ChoiceA");
		proc.processResponseVariables(responses, outcomes, true);
		assertTrue(outcomes.containsKey("DONE"));
		assertEquals("0", outcomes.get("DONE").values.get(0));
	}
	
	public void testTwoResponses(){
		DefaultVariableProcessor proc = new DefaultVariableProcessor();
		
		HashMap<String, Response> responses = new HashMap<String, Response>();
		HashMap<String, Outcome> outcomes = new HashMap<String, Outcome>();
		
		String responseXmlString1 = "<responseDeclaration identifier='RESPONSE' cardinality='single' baseType='identifier'><correctResponse><value>ChoiceA</value></correctResponse></responseDeclaration>";
		Response resp1 = new Response(XMLParser.parse(responseXmlString1).getDocumentElement());
		resp1.setModuleAdded();
		responses.put("RESPONSE", resp1);
		
		String responseXmlString2 = "<responseDeclaration identifier='RESPONSE2' cardinality='single' baseType='identifier'><correctResponse><value>ChoiceB</value></correctResponse></responseDeclaration>";
		Response resp2 = new Response(XMLParser.parse(responseXmlString2).getDocumentElement());
		resp2.setModuleAdded();
		responses.put("RESPONSE2", resp2);
		
		proc.ensureVariables(responses, outcomes);
		
		resp1.values.add("ChoiceA");
		resp2.values.add("ChoiceB");
		
		proc.processResponseVariables(responses, outcomes, true);
		assertTrue(outcomes.containsKey("DONE"));
		assertEquals("2", outcomes.get("DONE").values.get(0));
	}

}
