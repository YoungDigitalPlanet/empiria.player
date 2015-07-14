package eu.ydp.empiria.player.client.module.selection.structure;

import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.HasFixed;
import eu.ydp.empiria.player.module.abstractmodule.structure.XMLContentTypeAdapter;
import eu.ydp.gwtutil.client.StringUtils;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "item")
public class SelectionItemBean implements HasFixed {

    @XmlAttribute
    private int matchMax;
    @XmlAttribute
    private String identifier = StringUtils.EMPTY_STRING;
    @XmlValue
    @XmlJavaTypeAdapter(value = XMLContentTypeAdapter.class)
    private XMLContent xmlContent;
    @XmlAttribute
    private boolean fixed;

    public int getMatchMax() {
        return matchMax;
    }

    public void setMatchMax(int matchMax) {
        this.matchMax = matchMax;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    @Override
    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public XMLContent getXmlContent() {
        return xmlContent;
    }

    public void setXmlContent(XMLContent xmlContent) {
        this.xmlContent = xmlContent;
    }

}
