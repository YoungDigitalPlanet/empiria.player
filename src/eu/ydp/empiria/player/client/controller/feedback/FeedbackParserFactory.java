package eu.ydp.empiria.player.client.controller.feedback;

import com.peterfranza.gwt.jaxb.client.parser.JAXBBindings;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;

import eu.ydp.empiria.player.client.controller.feedback.structure.FeedbackAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.FeedbackBean;

@JAXBBindings(value=FeedbackBean.class, objects={FeedbackAction.class})
public interface FeedbackParserFactory extends JAXBParserFactory<FeedbackBean> {

}
