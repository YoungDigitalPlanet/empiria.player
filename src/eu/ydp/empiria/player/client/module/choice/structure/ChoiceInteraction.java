package eu.ydp.empiria.player.client.module.choice.structure;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ChoiceInteraction {
	
	@XmlAttribute
	private String id;
	@XmlAttribute
	private int maxChoices;
	@XmlAttribute
	private String responseIdentifier;
	@XmlAttribute
	private boolean shuffle;
	@XmlElement(name="simpleChoice")
	private List<SimpleChoice> simpleChoices;
	@XmlElement
	private String prompt;
	
	public List<SimpleChoice> getChoiceOptions() {
		return simpleChoices;
	}

	public void setSimpleChoices(List<SimpleChoice> simpleChoices) {
		this.simpleChoices = simpleChoices;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public int getMaxChoices() {
		return maxChoices;
	}
	
	public void setMaxChoices(int maxChoices) {
		this.maxChoices = maxChoices;
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

	public String getPrompt() {
		return prompt;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}	
	
}
