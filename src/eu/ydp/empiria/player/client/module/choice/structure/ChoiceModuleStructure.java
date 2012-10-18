package eu.ydp.empiria.player.client.module.choice.structure;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NodeList;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;

import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;
import eu.ydp.gwtutil.client.collections.RandomizedSet;

public class ChoiceModuleStructure extends AbstractModuleStructure<ChoiceInteractionBean>{

	private RandomizedSet<Integer> randomizedIndices;
	
	@Override
	protected void prepareStructure(){
		randomizeChoices();
	}
	
	protected NodeList getParentNodesForFeedbacks(Document xmlDocument){
		return xmlDocument.getElementsByTagName(EmpiriaTagConstants.NAME_SIMPLE_CHOICE);
	}

	private void randomizeChoices(){
		List<SimpleChoiceBean> simpleChoices = getBean().getSimpleChoices();
		int optionsLength = simpleChoices.size();
		List<SimpleChoiceBean> newSimpleChoiceList = createEmptyChoiceOptionList(optionsLength);

		if(getBean().isShuffle()){
			fillRandomizedIndices();
		}

		for (int i = 0; i < optionsLength; i++) {
			int optionIndex = i;
			SimpleChoiceBean choiceOption = simpleChoices.get(i);

			if(getBean().isShuffle() && !choiceOption.isFixed()){
				optionIndex = randomizedIndices.pull();
				choiceOption = simpleChoices.get(optionIndex);
			}

			newSimpleChoiceList.set(optionIndex, choiceOption);
		}

		getBean().setSimpleChoices(newSimpleChoiceList);
	}

	private List<SimpleChoiceBean> createEmptyChoiceOptionList(int size){
		List<SimpleChoiceBean> choiceOptions = new ArrayList<SimpleChoiceBean>();

		for(int i = 0; i < size; i++){
			choiceOptions.add(null);
		}

		return choiceOptions;
	}

	private void fillRandomizedIndices(){
		int optionsLength = getBean().getSimpleChoices().size();
		randomizedIndices = new RandomizedSet<Integer>();

		for(int i = 0; i < optionsLength; i++){
			SimpleChoiceBean choiceOption = getBean().getSimpleChoices().get(i);

			if(!choiceOption.isFixed()){
				randomizedIndices.push(i);
			}
		}
	}

	public void setMulti(boolean multi){
		for(SimpleChoiceBean simpleChoice: getBean().getSimpleChoices()){
			simpleChoice.setMulti(multi);
		}
	}

	@Override
	protected JAXBParserFactory<ChoiceInteractionBean> createParser() {
		return GWT.create(ChoiceModuleJAXBParser.class);
	}

	public List<SimpleChoiceBean> getSimpleChoices() {
		return getBean().getSimpleChoices();
	}
}
