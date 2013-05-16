package eu.ydp.empiria.player.client.module.ordering.structure;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import eu.ydp.empiria.player.client.module.abstractmodule.structure.HasShuffle;
import eu.ydp.empiria.player.client.structure.ModuleBean;

@XmlRootElement(name = "orderInteraction")
@XmlAccessorType(XmlAccessType.NONE)
public class OrderInteractionBean extends ModuleBean implements HasShuffle {

	@XmlElement(name = "simpleChoice")
	private List<SimpleOrderChoiceBean> choiceBeans;

	@XmlAttribute
	private boolean shuffle;

	@XmlAttribute
	private String responseIdentifier;

	public List<SimpleOrderChoiceBean> getChoiceBeans() {
		return choiceBeans;
	}

	public void setChoiceBeans(List<SimpleOrderChoiceBean> choiceBeans) {
		this.choiceBeans = choiceBeans;
	}

	@Override
	public boolean isShuffle() {
		return shuffle;
	}

	public void setShuffle(boolean shuffle) {
		this.shuffle = shuffle;
	}

	public String getResponseIdentifier() {
		return responseIdentifier;
	}

	public void setResponseIdentifier(String responseIdentifier) {
		this.responseIdentifier = responseIdentifier;
	}


}
