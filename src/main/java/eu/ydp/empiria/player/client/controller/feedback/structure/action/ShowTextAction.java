package eu.ydp.empiria.player.client.controller.feedback.structure.action;

import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;
import eu.ydp.empiria.player.module.abstractmodule.structure.XMLContentTypeAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "showText")
public class ShowTextAction implements FeedbackAction {

    @XmlValue
    @XmlJavaTypeAdapter(value = XMLContentTypeAdapter.class)
    private XMLContent content;
    @XmlAttribute
    private Integer notify;
    @XmlAttribute
    private String notifyOperator;

    public void setContent(XMLContent content) {
        this.content = content;
    }

    public XMLContent getContent() {
        return content;
    }

    public Integer getNotify() {
        return notify;
    }

    public void setNotify(Integer notify) {
        this.notify = notify;
    }

    public String getNotifyOperator() {
        return notifyOperator;
    }

    public void setNotifyOperator(String notifyOperator) {
        this.notifyOperator = notifyOperator;
    }

    public boolean hasNotify() {
        return notify != null;
    }

    @Override
    public String toString() {
        return "ShowTextAction [text=" + content.getValue().toString() + " notify= " + notify + " notifyOperator= " + notifyOperator + "]";
    }
}
