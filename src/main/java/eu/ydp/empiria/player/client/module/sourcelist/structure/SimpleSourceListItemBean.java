package eu.ydp.empiria.player.client.module.sourcelist.structure;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;
import eu.ydp.empiria.player.client.module.ModuleTagName;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.HasFixed;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemType;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemValue;
import eu.ydp.empiria.player.module.abstractmodule.structure.XMLContentTypeAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "simpleSourceListItem")
@XmlAccessorType(XmlAccessType.FIELD)
public class SimpleSourceListItemBean implements HasFixed {

    private static final String SRC_ATTR = "src";
    private static final String IMG_NODE = "img";
    private static final String MATH_NODE = ModuleTagName.INLINE_MATH_JAX.toString();

    @XmlAttribute
    private String alt;

    @XmlAttribute
    private boolean fixed;

    @XmlValue
    @XmlJavaTypeAdapter(value = XMLContentTypeAdapter.class)
    private XMLContent content;

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public SourcelistItemValue getItemValue() {
        NodeList imgNodes = content.getValue().getElementsByTagName(IMG_NODE);
        if (imgNodes.getLength() > 0) {
            return createImageItemValue();
        }

        NodeList mathNodes = content.getValue().getElementsByTagName(MATH_NODE);
        if (mathNodes.getLength() > 0) {
            return createMathItemValue();
        }

        return createTextItemValue();
    }

    private SourcelistItemValue createImageItemValue() {
        NodeList imgNodes = content.getValue().getElementsByTagName(IMG_NODE);
        Element imgElement = (Element) imgNodes.item(0);
        String src = imgElement.getAttribute(SRC_ATTR);
        return new SourcelistItemValue(SourcelistItemType.IMAGE, src, alt);
    }

    private SourcelistItemValue createTextItemValue() {
        return createTypedItemValue(SourcelistItemType.TEXT);
    }

    private SourcelistItemValue createMathItemValue() {
        return createTypedItemValue(SourcelistItemType.MATH);
    }

    private SourcelistItemValue createTypedItemValue(SourcelistItemType type){
        NodeList textNode = content.getValue().getChildNodes();
        String text = textNode.toString();
        return new SourcelistItemValue(type, text, alt);
    }

    public void setContent(XMLContent content) {
        this.content = content;
    }

    public XMLContent getContent() {
        return content;
    }

    @Override
    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }
}
