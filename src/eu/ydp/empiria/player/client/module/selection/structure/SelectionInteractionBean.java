package eu.ydp.empiria.player.client.module.selection.structure;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import eu.ydp.empiria.player.client.module.abstractmodule.structure.HasShuffle;
import eu.ydp.empiria.player.client.structure.InteractionModuleBean;

@XmlRootElement(name="selectionInteraction")
@XmlAccessorType(XmlAccessType.NONE)
public class SelectionInteractionBean extends InteractionModuleBean implements HasShuffle{
	
	@XmlAttribute
	private int matchMax;
	
	@XmlAttribute
	private int maxAssociations;
	
	@XmlAttribute
	private boolean multi;
	
	@XmlAttribute
	private boolean shuffle;
	
	@XmlElement(name = "simpleChoice")
	private List<SelectionSimpleChoiceBean> simpleChoices;
	
	@XmlElement(name = "item")
	private List<SelectionItemBean> items;

	public int getMatchMax() {
		return matchMax;
	}

	public void setMatchMax(int matchMax) {
		this.matchMax = matchMax;
	}
	
	public List<SelectionSimpleChoiceBean> getSimpleChoices() {
		return simpleChoices;
	}
	
	public void setSimpleChoices(List<SelectionSimpleChoiceBean> simpleChoices) {
		this.simpleChoices = simpleChoices;
	}

	public int getMaxAssociations() {
		return maxAssociations;
	}

	public void setMaxAssociations(int maxAssociations) {
		this.maxAssociations = maxAssociations;
	}

	public boolean isMulti() {
		return multi;
	}

	public void setMulti(boolean multi) {
		this.multi = multi;
	}

	public List<SelectionItemBean> getItems() {
		return items;
	}

	public void setItems(List<SelectionItemBean> items) {
		this.items = items;
	}

	public boolean isShuffle() {
		return shuffle;
	}

	public void setShuffle(boolean shuffle) {
		this.shuffle = shuffle;
	}
}
