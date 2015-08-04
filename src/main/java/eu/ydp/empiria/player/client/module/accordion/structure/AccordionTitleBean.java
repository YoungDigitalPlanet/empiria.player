package eu.ydp.empiria.player.client.module.accordion.structure;


import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

@XmlRootElement(name = "title")
public class AccordionTitleBean {

    @XmlValue
    private XMLContent title;

    public XMLContent getTitle() {
        return title;
    }

    public void setTitle(XMLContent title) {
        this.title = title;
    }
}
