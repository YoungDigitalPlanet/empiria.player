package eu.ydp.empiria.player.client.module.sourcelist.structure;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;

import eu.ydp.empiria.player.client.module.abstractmodule.structure.HasFixed;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemType;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemValue;
import eu.ydp.empiria.player.module.abstractmodule.structure.XMLContentTypeAdapter;

@XmlRootElement(name = "simpleSourceListItem")
@XmlAccessorType(XmlAccessType.FIELD)
public class SimpleSourceListItemBean implements HasFixed {

	private static final String SRC_ATTR = "src";
	private static final String IMG_NODE = "img";

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
		} else {
			return createTextItemValue();
		}
	}

	private SourcelistItemValue createImageItemValue() {
		NodeList imgNodes = content.getValue().getElementsByTagName(IMG_NODE);
		Element imgElement = (Element) imgNodes.item(0);
		String src = imgElement.getAttribute(SRC_ATTR);
		return new SourcelistItemValue(SourcelistItemType.IMAGE, src, alt);
	}

	private SourcelistItemValue createTextItemValue() {
		Node textNode = content.getValue().getChildNodes().item(0);
		String text = textNode.getNodeValue();
		return new SourcelistItemValue(SourcelistItemType.TEXT, text, alt);
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
