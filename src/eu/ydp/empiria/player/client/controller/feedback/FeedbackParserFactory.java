package eu.ydp.empiria.player.client.controller.feedback;

import com.peterfranza.gwt.jaxb.client.parser.JAXBBindings;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;

import eu.ydp.empiria.player.client.controller.feedback.structure.FeedbackBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackActionBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ShowTextAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.ShowUrlAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.AndConditionBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.CountConditionBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.FeedbackConditionBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.NotConditionBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.OrConditionBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.PropertyConditionBean;

@JAXBBindings(value=FeedbackBean.class, objects={FeedbackConditionBean.class, FeedbackActionBean.class, ShowTextAction.class, ShowUrlAction.class,
	PropertyConditionBean.class, CountConditionBean.class, AndConditionBean.class, OrConditionBean.class, NotConditionBean.class})
public interface FeedbackParserFactory extends JAXBParserFactory<FeedbackBean> {

}
