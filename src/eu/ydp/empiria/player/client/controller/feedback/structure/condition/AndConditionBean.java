package eu.ydp.empiria.player.client.controller.feedback.structure.condition;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "and")
public class AndConditionBean extends FeedbackConditionBase implements FeedbackCondition {

}
