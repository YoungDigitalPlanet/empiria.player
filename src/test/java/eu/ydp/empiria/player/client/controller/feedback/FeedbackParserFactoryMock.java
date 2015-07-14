package eu.ydp.empiria.player.client.controller.feedback;

import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;
import eu.ydp.empiria.player.client.controller.feedback.structure.FeedbackBean;
import eu.ydp.empiria.player.client.jaxb.JAXBParserImpl;

public class FeedbackParserFactoryMock implements FeedbackParserFactory {

    @Override
    public JAXBParser<FeedbackBean> create() {
        return new JAXBParserImpl<FeedbackBean>(FeedbackParserFactory.class);
    }
}
