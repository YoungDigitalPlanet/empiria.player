package eu.ydp.empiria.player.client.controller.variables.objects.response;

import com.google.inject.Inject;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;

/*Extracted from class eu.ydp.empiria.player.client.controller.variables.objects.response.Response
 * to separate object responsibilities from object creation (like xml parsing)
 * 
 * TODO: Refactor me please (I'm ugly!)
 */

public class ResponseNodeParser {

	private final ResponseNodeJAXBParserFactory responseNodeJAXBParserFactory;
	private final ResponseBeanConverter responseBeanConverter;

	@Inject
	public ResponseNodeParser(ResponseNodeJAXBParserFactory responseNodeJAXBParserFactory, ResponseBeanConverter responseBeanConverter) {
		this.responseNodeJAXBParserFactory = responseNodeJAXBParserFactory;
		this.responseBeanConverter = responseBeanConverter;
	}

	public Response parseResponseFromNode(String responseDeclaration) {
		JAXBParser<ResponseBean> jaxbParser = responseNodeJAXBParserFactory.create();
		ResponseBean responseJAXBModel = jaxbParser.parse(responseDeclaration);
		return responseBeanConverter.convert(responseJAXBModel);

		// CorrectAnswers correctAnswers = new CorrectAnswers();
		//
		// List<String> values = new Vector<String>();
		// Vector<String> groupsNames = new Vector<String>();
		//
		// String identifier = ((Element)
		// responseDeclaration).getAttribute("identifier");
		//
		// Cardinality cardinality = Cardinality.fromString(((Element)
		// responseDeclaration).getAttribute("cardinality"));
		//
		// Evaluate evaluate = Evaluate.fromString(((Element)
		// responseDeclaration).getAttribute("evaluate"));
		//
		// CheckMode checkMode = CheckMode.fromString(((Element)
		// responseDeclaration).getAttribute("checkMode"));
		//
		// BaseType baseType = BaseType.fromString(((Element)
		// responseDeclaration).getAttribute("baseType"));
		//
		// NodeList nodes = ((Element)
		// responseDeclaration).getElementsByTagName("value");
		//
		// for (int i = 0; i < nodes.getLength(); i++) {
		// Element valueElement = ((Element) nodes.item(i));
		// String nodeValue;
		// if (nodes.item(i).hasChildNodes()) {
		// nodeValue = nodes.item(i).getFirstChild().getNodeValue();
		// } else {
		// nodeValue = "";
		// }
		//
		// int forIndex = correctAnswers.getResponseValuesCount();
		// if (valueElement.hasAttribute("forIndex")) {
		// Integer forIndex2 =
		// NumberUtils.tryParseInt(valueElement.getAttribute("forIndex"), null);
		// if (forIndex2 != null) {
		// forIndex = forIndex2;
		// }
		// }
		//
		// if (correctAnswers.getResponseValuesCount() > forIndex) {
		// correctAnswers.add(nodeValue, forIndex);
		// } else {
		// correctAnswers.add(new ResponseValue(nodeValue));
		// }
		//
		// String groupMode = valueElement.getAttribute("groupMode");
		// if (valueElement.hasAttribute("group") && (groupMode == null ||
		// ("groupItem".equals(groupMode)))) {
		// String currGroupName = ((Element)
		// nodes.item(i)).getAttribute("group");
		// if (!groupsNames.contains(currGroupName)) {
		// groupsNames.add(currGroupName);
		// }
		// }
		// }

		// return new Response(correctAnswers, values, groupsNames, identifier,
		// evaluate, baseType, cardinality, CountMode.SINGLE, null, checkMode);
		// return null;
	}

}
