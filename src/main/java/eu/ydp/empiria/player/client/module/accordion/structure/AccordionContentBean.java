package eu.ydp.empiria.player.client.module.accordion.structure;


import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "content")
public class AccordionContentBean {
    @XmlValue
    private XMLContent content;

    public XMLContent getContent() {
        return content;
    }

    public void setContent(XMLContent content) {
        this.content = content;
    }
}
