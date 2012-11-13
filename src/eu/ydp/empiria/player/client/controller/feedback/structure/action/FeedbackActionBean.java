package eu.ydp.empiria.player.client.controller.feedback.structure.action;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@SuppressWarnings("unused")
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name="action")
public class FeedbackActionBean {

	@XmlElement(name="showText")
	private ShowTextAction showText;
	
	@XmlElement(name="showUrl")
	private List<ShowUrlAction> showUrls = new ArrayList<ShowUrlAction>();

	private ShowTextAction getShowText() {
		return showText;
	}

	private void setShowText(ShowTextAction showText) {
		this.showText = showText;
	}
	
	private List<ShowUrlAction> getShowUrls() {
		return showUrls;
	}

	private void setShowUrls(List<ShowUrlAction> showUrls) {
		this.showUrls = showUrls;
	}
	
	public List<FeedbackAction> getAllActions() {
		List<FeedbackAction> allActions = new ArrayList<FeedbackAction>();
		if (showText != null) {
			allActions.add(showText);
		}
		allActions.addAll(showUrls);
		
		return allActions;
	}
}
