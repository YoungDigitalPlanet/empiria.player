package eu.ydp.empiria.player.client.controller.feedback.structure;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackAction;
import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackActionBean;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.FeedbackCondition;
import eu.ydp.empiria.player.client.controller.feedback.structure.condition.FeedbackConditionBean;


@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name="feedback")
public class FeedbackBean implements Feedback {
	
	@XmlElement(name="action")
	FeedbackActionBean action;
	
	@XmlElement(name="condition")
	FeedbackConditionBean condition;
	
	public FeedbackActionBean getAction() {
		return action;
	}
	
	public void setActions(FeedbackActionBean action) {
		this.action = action;
	}

	public void setConditionElement(FeedbackConditionBean condition) {
		this.condition = condition;
	}

	@Override
	public List<FeedbackAction> getActions() {
		return action.getAllActions();
	}

	@Override
	public FeedbackCondition getCondition() {
		return (condition.getAllConditions().isEmpty())? 
							null: 
							condition.getAllConditions().get(0);
	}
}
