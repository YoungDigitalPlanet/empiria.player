package eu.ydp.empiria.player.client.controller.feedback.structure;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackActionBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.FeedbackConditionBean;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name="feedback")
public class FeedbackBean implements Feedback {
	
	@XmlElement(name="action")
	FeedbackActionBean action;
	
	@XmlElement(name="condition")
	FeedbackConditionBean condition;

	@Override
	public FeedbackActionBean getAction() {
		return action;
	}
	
	public void setActions(FeedbackActionBean action) {
		this.action = action;
	}
	
	@Override
	public FeedbackConditionBean getCondition() {
		return condition;
	}	

	public void setCondition(FeedbackConditionBean criterion) {
		this.condition = criterion;
	}
}
