package eu.ydp.empiria.player.client.module.connection.structure;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class MatchInteraction {

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
	private List<SimpleMatchSet> simpleMatchSets;

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

	public List<SimpleMatchSet> getSimpleMatchSets() {
		return simpleMatchSets;
	}

	public void setSimpleMatchSets(List<SimpleMatchSet> simpleMatchSets) {
		this.simpleMatchSets = simpleMatchSets;
	}
}
