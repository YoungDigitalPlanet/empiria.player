package eu.ydp.empiria.player.client.module.connection.structure;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import eu.ydp.empiria.player.client.module.components.multiplepair.structure.MultiplePairBean;
import eu.ydp.gwtutil.client.StringUtils;

@XmlRootElement(name="matchInteraction")
@XmlAccessorType(XmlAccessType.FIELD)
public class MatchInteractionBean implements MultiplePairBean {

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

	public MatchInteractionBean() {
		id = StringUtils.EMPTY_STRING;
		responseIdentifier = StringUtils.EMPTY_STRING;		
		simpleMatchSets = new ArrayList<SimpleMatchSetBean>();
	}
	
	@Override	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override	
	public int getMatchMax() {
		return matchMax;
	}

	public void setMatchMax(int matchMax) {
		this.matchMax = matchMax;
	}

	@Override	
	public int getMaxAssociations() {
		return maxAssociations;
	}

	public void setMaxAssociations(int maxAssociations) {
		this.maxAssociations = maxAssociations;
	}

	@Override	
	public String getResponseIdentifier() {
		return responseIdentifier;
	}

	public void setResponseIdentifier(String responseIdentifier) {
		this.responseIdentifier = responseIdentifier;
	}

	@Override
	public boolean isShuffle() {
		return shuffle;
	}

	public void setShuffle(boolean shuffle) {
		this.shuffle = shuffle;
	}

	public List<SimpleMatchSetBean> getSimpleMatchSets() {		
		return simpleMatchSets;
	}

	public void setSimpleMatchSets(List<SimpleMatchSetBean> simpleMatchSets) {
		this.simpleMatchSets = simpleMatchSets;
	}

	@Override
	public List<SimpleAssociableChoiceBean> getFirstChoicesSet() {		 
		return simpleMatchSets.get(0).getSimpleAssociableChoices();
	}

	@Override
	public List<SimpleAssociableChoiceBean> getSecondChoicesSet() {
		return simpleMatchSets.get(1).getSimpleAssociableChoices();
	}
}
