package eu.ydp.empiria.player.client.module.accordion.structure;

import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "section")
public class AccordionSectionBean {

    @XmlElement(name = "title")
    private AccordionTitleBean title;
    @XmlElement(name = "content")
    private AccordionContentBean content;

    public XMLContent getTitle() {
        return title.getTitle();
    }

    public void setTitle(AccordionTitleBean title) {
        this.title = title;
    }

    public XMLContent getContent() {
        return content.getContent();
    }

    public void setContent(AccordionContentBean content) {
        this.content = content;
    }
}
