package eu.ydp.empiria.player.client.module.connection.structure;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import eu.ydp.empiria.player.client.module.components.multiplepair.structure.AMultiplePairChoiceBean;

@XmlRootElement(name="simpleAssociableChoice")
@XmlAccessorType(XmlAccessType.FIELD)
public class SimpleAssociableChoiceBean extends AMultiplePairChoiceBean {
	
	@XmlAttribute
	private boolean fixed;
	
	public boolean isFixed() {
		return fixed;
	}

	public void setFixed(boolean fixed) {
		this.fixed = fixed;
	}
}
