package eu.ydp.empiria.player.client.module.connection.structure;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="simpleMatchSet")
public class SimpleMatchSetBean {
	
	@XmlElement(name="simpleAssociableChoice")
	private List<SimpleAssociableChoiceBean> simpleAssociableChoices;

	public List<SimpleAssociableChoiceBean> getSimpleAssociableChoices() {
		return simpleAssociableChoices;
	}

	public void setSimpleAssociableChoices(List<SimpleAssociableChoiceBean> simpleAssociableChoices) {
		this.simpleAssociableChoices = simpleAssociableChoices;
	}
}
