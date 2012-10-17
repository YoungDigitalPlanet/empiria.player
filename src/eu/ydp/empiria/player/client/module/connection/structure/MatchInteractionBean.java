package eu.ydp.empiria.player.client.module.connection.structure;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import eu.ydp.empiria.player.client.module.abstractmodule.structure.IInteractionBean;
import eu.ydp.empiria.player.client.module.components.multiplepair.structure.IMultiplePairBean;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MatchInteractionBean implements IMultiplePairBean {

	@XmlAttribute
	private String id;

	@XmlAttribute(name = "class")
	private String type;
	
	@XmlAttribute
	private int matchMax;

	@XmlAttribute
	private int maxAssociations;

	@XmlAttribute
	private String responseIdentifier;

	@XmlAttribute
	private boolean shuffle;

	@XmlElement(name = "simpleMatchSet")
	private List<SimpleMatchSetBean> simpleMatchSets;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getMatchMax() {
		return matchMax;
	}

	public void setMatchMax(int matchMax) {
		this.matchMax = matchMax;
	}

	public int getMaxAssociations() {
		return maxAssociations;
	}

	public void setMaxAssociations(int maxAssociations) {
		this.maxAssociations = maxAssociations;
	}

	public String getResponseIdentifier() {
		return responseIdentifier;
	}

	public void setResponseIdentifier(String responseIdentifier) {
		this.responseIdentifier = responseIdentifier;
	}

	public boolean isShuffle() {
		return shuffle;
	}

	public void setShuffle(boolean shuffle) {
		this.shuffle = shuffle;
	}

	public List<SimpleMatchSetBean> getSimpleMatchSets() {		
		if (simpleMatchSets == null) {
			simpleMatchSets = new ArrayList<SimpleMatchSetBean>();
		}
		return simpleMatchSets;
	}

	public void setSimpleMatchSets(List<SimpleMatchSetBean> simpleMatchSets) {
		this.simpleMatchSets = simpleMatchSets;
	}

	@Override
	public List<SimpleAssociableChoiceBean> getFirstChoicesSet() {		 
		return (getSimpleMatchSets().get(0) != null) ? getSimpleMatchSets().get(0).getSimpleAssociableChoices() : null;
	}

	@Override
	public List<SimpleAssociableChoiceBean> getSecondChoicesSet() {
		return (getSimpleMatchSets().get(1) != null) ? getSimpleMatchSets().get(0).getSimpleAssociableChoices() : null;
	}
}
