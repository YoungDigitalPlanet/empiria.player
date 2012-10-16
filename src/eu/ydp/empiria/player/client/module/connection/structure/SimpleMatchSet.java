package eu.ydp.empiria.player.client.module.connection.structure;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="simpleMatchSet")
public class SimpleMatchSet {
	
	@XmlElement(name="simpleAssociableChoice")
	private List<SimpleAssociableChoice> simpleAssociableChoices;

	public List<SimpleAssociableChoice> getSimpleAssociableChoices() {
		return simpleAssociableChoices;
	}

	public void setSimpleAssociableChoices(List<SimpleAssociableChoice> simpleAssociableChoices) {
		this.simpleAssociableChoices = simpleAssociableChoices;
	}
}
