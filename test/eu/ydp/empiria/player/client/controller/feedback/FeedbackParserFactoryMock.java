package eu.ydp.empiria.player.client.controller.feedback;

import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;

import eu.ydp.empiria.player.client.AbstractJAXBTestBase;
import eu.ydp.empiria.player.client.controller.feedback.structure.FeedbackBean;

public class FeedbackParserFactoryMock extends AbstractJAXBTestBase<FeedbackBean> implements FeedbackParserFactory {

	@Override
	public JAXBParser<FeedbackBean> create() {
		return new JAXBParser<FeedbackBean>() {

			@Override
			public FeedbackBean parse(String xml) {
				createUnmarshaller();
				return createBeanFromXMLString(xml);
			}
			
		};
	}

}
