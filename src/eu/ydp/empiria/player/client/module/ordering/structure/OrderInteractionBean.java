package eu.ydp.empiria.player.client.module.ordering.structure;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import eu.ydp.empiria.player.client.structure.ModuleBean;

@XmlRootElement(name = "orderInteraction")
@XmlAccessorType(XmlAccessType.NONE)
public class OrderInteractionBean extends ModuleBean {

	@XmlElement(name = "simpleChoice")
	private List<SimpleOrderChoiceBean> choiceBeans;

	public List<SimpleOrderChoiceBean> getChoiceBeans() {
		return choiceBeans;
	}

	public void setChoiceBeans(List<SimpleOrderChoiceBean> choiceBeans) {
		this.choiceBeans = choiceBeans;
	}
}
