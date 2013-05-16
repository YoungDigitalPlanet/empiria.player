package eu.ydp.empiria.player.client.module.ordering.structure;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import com.google.gwt.xml.client.Document;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;

import eu.ydp.empiria.player.client.jaxb.JAXBParserImpl;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.ShuffleHelper;
import eu.ydp.empiria.player.client.test.utils.ReflectionsUtils;
import eu.ydp.gwtutil.xml.XMLParser;

public class OrderInteractionStructureMock extends OrderInteractionStructure{

	public OrderInteractionStructureMock() {
		try {
			ReflectionsUtils reflectionsUtils = new ReflectionsUtils();
			reflectionsUtils.setValueInObjectOnField("shuffleHelper", this, mock(ShuffleHelper.class));
		} catch (Exception e) {
		}
	}

	public static final String XML = "<orderInteraction id=\"dummy1\" responseIdentifier=\"ORDERING_RESPONSE_1\" shuffle=\"true\">"+
			"<simpleChoice identifier=\"ORDERING_RESPONSE_1_0\">"+
				"Words in this sentence	"
			+ "</simpleChoice>"+
			"<simpleChoice identifier=\"ORDERING_RESPONSE_1_1\" fixed=\"true\">"+
				"should be put in"
			+ "</simpleChoice>"+
			"<simpleChoice identifier=\"ORDERING_RESPONSE_1_2\" fixed=\"false\">"+
				"the correct order."
			+ "</simpleChoice>"+
		"</orderInteraction>";


	@Override
	public OrderInteractionModuleJAXBParserFactory getParserFactory() {
		return mockParserFactory();
	}

	private OrderInteractionModuleJAXBParserFactory mockParserFactory() {
		return new OrderInteractionModuleJAXBParserFactory() {
			@Override
			public JAXBParser<OrderInteractionBean> create() {
				return new JAXBParserImpl<OrderInteractionBean>(OrderInteractionModuleJAXBParserFactory.class);
			}
		};
	}



	@Override
	protected eu.ydp.gwtutil.client.xml.XMLParser getXMLParser() {
		eu.ydp.gwtutil.client.xml.XMLParser xmlParser = mock(eu.ydp.gwtutil.client.xml.XMLParser.class);
		when(xmlParser.parse(Mockito.anyString())).thenAnswer(new Answer<Document>() {
			@Override
			public Document answer(InvocationOnMock invocation) throws Throwable {
				return XMLParser.parse((String) invocation.getArguments()[0]);
			}
		});
		return xmlParser;
	}
}
