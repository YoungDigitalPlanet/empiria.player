package eu.ydp.empiria.player.client.controller.feedback.structure.action;

import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;
import eu.ydp.empiria.player.module.abstractmodule.structure.XMLContentTypeAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "showText")
public class ShowTextAction implements FeedbackAction {

    @XmlValue
    @XmlJavaTypeAdapter(value = XMLContentTypeAdapter.class)
    private XMLContent content;

    public void setContent(XMLContent content) {
        this.content = content;
    }

    public XMLContent getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "ShowTextAction [text=" + content.toString() + "]";
    }
}
