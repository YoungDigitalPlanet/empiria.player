package eu.ydp.empiria.player.client.module.choice.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.XMLParser;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;

import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;
import eu.ydp.gwtutil.client.collections.RandomizedSet;

public class ChoiceModuleStructure {

	ChoiceMouduleJAXBParser parser = GWT.create(ChoiceMouduleJAXBParser.class);
	
	private ChoiceInteraction choiceInteraction;
	
	private RandomizedSet<Integer> randomizedIndices;
	
	private Map<String, Element> simpleChoiceFeedbacks;

	public void createFromXml(String xml) {
		choiceInteraction = parse(xml);
		prepareStructure();
		prepareFeedbackNodes(xml);
	}
	
	private ChoiceInteraction parse(String xml){
		return getParser().parse(xml);
	}
	
	private JAXBParser<ChoiceInteraction> getParser(){
		return parser.create();
	}
	
	private void prepareStructure(){
		randomizeChoices();
	}
	
	private void prepareFeedbackNodes(String xml){
		//TODO: rewrite to JAXB
		Document xmlDoc = XMLParser.parse(xml);
		NodeList simpleChoiceNodes = xmlDoc.getElementsByTagName(EmpiriaTagConstants.NAME_SIMPLE_CHOICE);
		simpleChoiceFeedbacks = new HashMap<String, Element>();
		
		for(int i = 0; i < simpleChoiceNodes.getLength(); i++){
			Element choiceNode = (Element) simpleChoiceNodes.item(i);
			Element feedbackNode = getInlineFeedbackNode(choiceNode);
			
			if(feedbackNode != null){
				String indentifier = feedbackNode.getAttribute(EmpiriaTagConstants.ATTR_IDENTIFIER);
				simpleChoiceFeedbacks.put(indentifier, feedbackNode);
			}
		}
	}
	
	private Element getInlineFeedbackNode(Element parentNode){
		Element feedbackElement = null;
		NodeList feedbackElements = parentNode.getElementsByTagName(EmpiriaTagConstants.NAME_FEEDBACK_INLINE);
		
		if(feedbackElements != null && feedbackElements.getLength() > 0){
			feedbackElement = (Element) feedbackElements.item(0);
		}
		
		return feedbackElement;
	}
	
	private void randomizeChoices(){
		List<ChoiceOption> simpleChoices = choiceInteraction.getChoiceOptions(); 
		int optionsLength = simpleChoices.size();
		List<ChoiceOption> newSimpleChoiceList = createEmptyChoiceOptionList(optionsLength);
		
		if(choiceInteraction.isShuffle()){
			fillRandomizedIndices();
		}
		
		for (int i = 0; i < optionsLength; i++) {
			int optionIndex = i;
			ChoiceOption choiceOption = simpleChoices.get(i);
			
			if(choiceInteraction.isShuffle() && !choiceOption.isFixed()){
				optionIndex = randomizedIndices.pull();
				choiceOption = simpleChoices.get(optionIndex);
			}
			
			newSimpleChoiceList.set(optionIndex, choiceOption);
		}
		
		choiceInteraction.setSimpleChoices(newSimpleChoiceList);
	}
	
	private List<ChoiceOption> createEmptyChoiceOptionList(int size){
		List<ChoiceOption> choiceOptions = new ArrayList<ChoiceOption>();
		
		for(int i = 0; i < size; i++){
			choiceOptions.add(null);
		}
		
		return choiceOptions;
	}
	
	private void fillRandomizedIndices(){
		int optionsLength = choiceInteraction.getChoiceOptions().size();
		randomizedIndices = new RandomizedSet<Integer>();
		
		for(int i = 0; i < optionsLength; i++){
			ChoiceOption choiceOption = choiceInteraction.getChoiceOptions().get(i);
			
			if(!choiceOption.isFixed()){
				randomizedIndices.push(i);
			}
		}
	}
	
	public void setMulti(boolean multi){
		for(ChoiceOption simpleChoice: choiceInteraction.getChoiceOptions()){
			simpleChoice.setMulti(multi);
		}
	}
	
	public Element getSimpleChoiceFeedbackElement(String identifer){
		return simpleChoiceFeedbacks.get(identifer);
	}
	
	public String getPrompt(){
		return choiceInteraction.getPrompt();
	}
	
	public List<ChoiceOption> getChoiceOptions(){
		return choiceInteraction.getChoiceOptions();
	}
}
