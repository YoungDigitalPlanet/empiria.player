package eu.ydp.empiria.player.client.module.selection.structure;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import com.peterfranza.gwt.jaxb.client.parser.utils.XMLContent;

import eu.ydp.empiria.player.client.module.components.multiplepair.structure.PairChoiceBean;
import eu.ydp.empiria.player.client.structure.SimpleChoiceBaseBean;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "simpleChoice")
public class SelectionSimpleChoiceBean extends SimpleChoiceBaseBean implements PairChoiceBean{

	@XmlAttribute
	protected int matchMax;
	
	@XmlValue
	private String value;

	public int getMatchMax() {
		return matchMax;
	}

	public void setMatchMax(int matchMax) {
		this.matchMax = matchMax;
	}

	@Override
	public boolean isFixed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public XMLContent getXmlContent() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
