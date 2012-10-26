package eu.ydp.empiria.player.client.module.connection.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.HashMap;

import eu.ydp.empiria.player.client.module.components.multiplepair.structure.MultiplePairBean;
import eu.ydp.gwtutil.client.StringUtils;

@XmlRootElement(name="matchInteraction")
@XmlAccessorType(XmlAccessType.FIELD)
public class MatchInteractionBean implements MultiplePairBean<SimpleAssociableChoiceBean> {

	@XmlAttribute
	private String id;

	@XmlAttribute(name = "class")
	private String type;
	
	@XmlAttribute
	private int maxAssociations;

	@XmlAttribute
	private String responseIdentifier;

	@XmlAttribute
	private boolean shuffle;

	@XmlElement(name = "simpleMatchSet")
	private List<SimpleMatchSetBean> simpleMatchSets;

	private final Map<String, SimpleAssociableChoiceBean> flatChoicesMap;
	
	public MatchInteractionBean() {
		id = StringUtils.EMPTY_STRING;
		responseIdentifier = StringUtils.EMPTY_STRING;		
		simpleMatchSets = new ArrayList<SimpleMatchSetBean>();
		flatChoicesMap = new HashMap<String, SimpleAssociableChoiceBean>(); 
	}
	
	private Map<String, SimpleAssociableChoiceBean> getFlatChoicesMap() {
		if (flatChoicesMap.size() <= 0) {
			for (SimpleAssociableChoiceBean choice : getSourceChoicesSet()) {
				flatChoicesMap.put(choice.getIdentifier(), choice);
			}
			for (SimpleAssociableChoiceBean choice : getTargetChoicesSet()) {
				flatChoicesMap.put(choice.getIdentifier(), choice);
			}			
		}
		return flatChoicesMap;
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
	public List<SimpleAssociableChoiceBean> getSourceChoicesSet() {		 
		return simpleMatchSets.get(0).getSimpleAssociableChoices();
	}
	
	public List<String> getSourceChoicesIdentifiersSet() {
		return getChoicesIdentifiersSet(getSourceChoicesSet());
	}	
	
	@Override
	public List<SimpleAssociableChoiceBean> getTargetChoicesSet() {
		return simpleMatchSets.get(1).getSimpleAssociableChoices();
	}

	public List<String> getTargetChoicesIdentifiersSet() {
		return getChoicesIdentifiersSet(getTargetChoicesSet());
	}	
		
	private List<String> getChoicesIdentifiersSet(List<SimpleAssociableChoiceBean> choices) {
		ArrayList<String> identifiersSet = new ArrayList<String>(); 
		
		for (SimpleAssociableChoiceBean simpleAssociableChoiceBean : choices) {
			identifiersSet.add(simpleAssociableChoiceBean.getIdentifier());
		}
		
		return identifiersSet;
	}

	@Override
	public SimpleAssociableChoiceBean getChoiceByIdentifier(String sourceItem) {
		return getFlatChoicesMap().get(sourceItem);
	}
	
}
