package eu.ydp.empiria.player.client.controller.feedback.structure.action;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.google.gwt.xml.client.Node;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;

import eu.ydp.empiria.player.module.abstractmodule.structure.XMLContentTypeAdapter;
import eu.ydp.gwtutil.client.StringUtils;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "showText")
public class ShowTextAction implements FeedbackTextAction {

	@XmlValue
	@XmlJavaTypeAdapter(value = XMLContentTypeAdapter.class)
	private XMLContent content;

	private String text = StringUtils.EMPTY_STRING;

	public void setContent(XMLContent content) {
		this.content = content;

		Node childNode = content.getValue().getChildNodes().item(0);
		if (childNode != null) {
			setText(childNode.getNodeValue());
		}
	}

	public XMLContent getContent() {
		return content;
	}

	public void setText(String text) {
		this.text = text.trim();
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		return "ShowTextAction [text=" + text + "]";
	}
}
