package eu.ydp.empiria.player.client.module.ordering.structure;

import com.google.gwt.core.client.GWT;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;

import eu.ydp.empiria.player.client.AbstractEmpiriaPlayerGWTTestCase;

@SuppressWarnings("PMD")
public class OrderInteractionModuleJAXBParserFactoryGWTTest extends AbstractEmpiriaPlayerGWTTestCase  {

	private final String XML = "<orderInteraction id=\"dummy1\" responseIdentifier=\"ORDERING_RESPONSE_1\" shuffle=\"true\">"+
									"<simpleChoice identifier=\"ORDERING_RESPONSE_1_0\">"+
										"Words in this sentence	"
									+ "</simpleChoice>"+
									"<simpleChoice identifier=\"ORDERING_RESPONSE_1_1\">"+
										"should be put in"
									+ "</simpleChoice>"+
									"<simpleChoice identifier=\"ORDERING_RESPONSE_1_2\">"+
										"the correct order."
									+ "</simpleChoice>"+
								"</orderInteraction>";

	 public void testXMLParse(){
		 //dummy test sprawdzenie czy bindowanie dziala
		 OrderInteractionBean bean = parse(XML);
		 assertEquals(3, bean.getChoiceBeans().size());

	 }

	private OrderInteractionBean parse(String xml) {
		OrderInteractionModuleJAXBParserFactory jaxbParserFactory = GWT.create(OrderInteractionModuleJAXBParserFactory.class);
		JAXBParser<OrderInteractionBean> jaxbParser = jaxbParserFactory.create();
		OrderInteractionBean expressionsBean = jaxbParser.parse(xml);
		return expressionsBean;
	}
}
