package eu.ydp.empiria.player.client.controller.feedback.structure;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import eu.ydp.empiria.player.client.controller.feedback.structure.action.FeedbackTextAction;


@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name="showText")
public class ShowTextAction implements FeedbackTextAction {
	
	@XmlValue
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
