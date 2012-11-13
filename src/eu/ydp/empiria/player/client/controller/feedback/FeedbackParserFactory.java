package eu.ydp.empiria.player.client.controller.feedback;

import com.peterfranza.gwt.jaxb.client.parser.JAXBBindings;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;

import eu.ydp.empiria.player.client.controller.feedback.structure.AndConditionBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.CountConditionBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.FeedbackActionBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.FeedbackBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.FeedbackConditionBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.NotConditionBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.OrConditionBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.PropertyConditionBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.ShowTextAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.ShowUrlAction;

@JAXBBindings(value=FeedbackBean.class, objects={FeedbackConditionBean.class, FeedbackActionBean.class, ShowTextAction.class, ShowUrlAction.class,
	PropertyConditionBean.class, CountConditionBean.class, AndConditionBean.class, OrConditionBean.class, NotConditionBean.class})
public interface FeedbackParserFactory extends JAXBParserFactory<FeedbackBean> {

}
