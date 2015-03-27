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
	}

}
