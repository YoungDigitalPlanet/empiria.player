package eu.ydp.empiria.player.client.module.labelling.structure;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;

import eu.ydp.empiria.player.module.abstractmodule.structure.XMLContentTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "child")
public class ChildBean {

	@XmlAttribute
	private int x;
	@XmlAttribute
	private int y;
	@XmlValue
	@XmlJavaTypeAdapter(value = XMLContentTypeAdapter.class)
	private XMLContent content;

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public XMLContent getContent() {
		return content;
	}

	public void setContent(XMLContent content) {
		this.content = content;
	}

}
