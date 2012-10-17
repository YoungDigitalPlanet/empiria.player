package eu.ydp.empiria.player.client.module.connection.structure;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="simpleAssociableChoice")
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
