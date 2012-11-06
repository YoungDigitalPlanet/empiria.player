package eu.ydp.empiria.player.client.controller.feedback.structure;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name="feedback")
public class FeedbackBean implements Feedback {
	
	@XmlElement(name="action", defaultValue="null")
	List<FeedbackAction> actionList;

	@Override
	public List<FeedbackAction> getActions() {
		return null;
	}
	
	public void setActionList(List<FeedbackAction> actionList) {
		this.actionList = actionList;
	}

	@Override
	public FeedbackCriterion getCriterion() {
		return null;
	}

}
