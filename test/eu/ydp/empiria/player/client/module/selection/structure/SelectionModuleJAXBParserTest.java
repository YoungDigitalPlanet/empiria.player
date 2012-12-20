package eu.ydp.empiria.player.client.module.selection.structure;

import java.util.List;

import org.junit.Test;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;

public class SelectionModuleJAXBParserTest extends GWTTestCase{

	private String testXml = "<selectionInteraction id=\"dummy1\" matchMax=\"1\" responseIdentifier=\"SELECTION_RESPONSE_1\" multi=\"false\" shuffle=\"true\">"+
			"		<item identifier=\"SELECTION_RESPONSE_1_0\" matchMax=\"1\" fixed=\"true\"><math>a1</math></item>"+
			"		<item identifier=\"SELECTION_RESPONSE_1_3\" matchMax=\"2\" fixed=\"false\">b1</item>"+
			"		<simpleChoice identifier=\"SELECTION_RESPONSE_1_1\" matchMax=\"1\"><math>a2</math></simpleChoice>"+
			"		<simpleChoice identifier=\"SELECTION_RESPONSE_1_4\" matchMax=\"2\">b2</simpleChoice>"+
			"</selectionInteraction>";
	
	@Test
	public void testCreate() {
		SelectionModuleJAXBParser jaxbParserFactory = GWT.create(SelectionModuleJAXBParser.class);
		JAXBParser<SelectionInteractionBean> jaxbParser = jaxbParserFactory.create();
		
		SelectionInteractionBean interactionBean = jaxbParser.parse(testXml);
		
		assertNotNull(interactionBean);
		assertEquals("dummy1", interactionBean.getId());
		assertEquals(1, interactionBean.getMatchMax());
		assertEquals("SELECTION_RESPONSE_1", interactionBean.getResponseIdentifier());
		assertEquals(false, interactionBean.isMulti());
		assertEquals(true, interactionBean.isShuffle());
		
		//validate items
		List<SelectionItemBean> items = interactionBean.getItems();
		assertEquals(2, items.size());
		
		validateItem(items.get(0), "SELECTION_RESPONSE_1_0", 1, true, "<math>a1</math>");
		validateItem(items.get(1), "SELECTION_RESPONSE_1_3", 2, false, "b1");
		
		//validate choices
		List<SelectionSimpleChoiceBean> simpleChoices = interactionBean.getSimpleChoices();
		assertEquals(2, simpleChoices.size());
		
		validateChoice(simpleChoices.get(0), "SELECTION_RESPONSE_1_1", 1, "<math>a2</math>");
		validateChoice(simpleChoices.get(1), "SELECTION_RESPONSE_1_4", 2, "b2");
	}

	private void validateChoice(SelectionSimpleChoiceBean choiceBean, String identifier, int matchMax, String content) {
		assertEquals(identifier, choiceBean.getIdentifier());
		assertEquals(matchMax, choiceBean.getMatchMax());
		
		XMLContent xmlContent = choiceBean.getXmlContent();
		Element element = xmlContent.getValue();
		Node value = element.getFirstChild();
		assertEquals(content, value.toString());
	}

	private void validateItem(SelectionItemBean item1, String identifier, int matchMax, boolean fixed, String content) {
		assertEquals(identifier, item1.getIdentifier());
		assertEquals(matchMax, item1.getMatchMax());
		assertEquals(fixed, item1.isFixed());
		
		Element element = item1.getXmlContent().getValue();
		Node value = element.getFirstChild();
		assertEquals(content, value.toString());
	}

	@Override
	public String getModuleName() {
		return "eu.ydp.empiria.player.Player";
	}

}
