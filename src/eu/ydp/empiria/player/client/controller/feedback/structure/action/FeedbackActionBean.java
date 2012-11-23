package eu.ydp.empiria.player.client.controller.feedback.structure.action;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.google.common.collect.Lists;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name="action")
public class FeedbackActionBean {

	@XmlElement(name="showText")
	private ShowTextAction showText;
	
	@XmlElement(name="showUrl")
	private List<ShowUrlAction> showUrls = Lists.newArrayList();

	public ShowTextAction getShowText() {
		return showText;
	}

	public void setShowText(ShowTextAction showText) {
		this.showText = showText;
	}
	
	public List<ShowUrlAction> getShowUrls() {
		return showUrls;
	}

	public void setShowUrls(List<ShowUrlAction> showUrls) {
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
