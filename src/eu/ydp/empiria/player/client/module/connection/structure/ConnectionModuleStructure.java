package eu.ydp.empiria.player.client.module.connection.structure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;

import eu.ydp.empiria.player.client.module.abstractModule.structure.AbstractModuleStructure;
import eu.ydp.gwtutil.client.collections.RandomizedSet;

public class ConnectionModuleStructure extends AbstractModuleStructure<MatchInteractionBean> {
	
	private RandomizedSet<Integer> randomizedIndices;	
	
	@Override
	protected JAXBParserFactory<MatchInteractionBean> createParser() {
		return GWT.create(ConnectionModuleJAXBParser.class);
	}	
	
	@Override
	protected void prepareStructure() {
		randomizeSets();		
	}

	@Override
	protected void prepareFeedbackNodes(String xml) {	
		super.prepareFeedbackNodes(xml);
		
		feedbacks.put(null, null); // TODO: to be implemented using JAXB based on in eu.ydp.empiria.player.client.module.choice.structure.prepareFeedbackNodes()		
	}
	
	private void randomizeSets() {
		List<SimpleMatchSetBean> simpleMatchSets = interaction.getSimpleMatchSets();
		
		for (Iterator<SimpleMatchSetBean> iterator = simpleMatchSets.iterator(); iterator.hasNext();) {
			SimpleMatchSetBean simpleMatchSetBean = iterator.next();
			simpleMatchSetBean.setSimpleAssociableChoices(randomizeChoices(simpleMatchSetBean.getSimpleAssociableChoices()));
		}
	}
	
	/**
	 * FIXME: Do przemyslenia. Ponizsze moga zostac przeniesione do jakiegos wspolnego abstracta dla selections i connection.
	 * Minimalny refactor bedzie potrzebny - trzeba je tylko zmienic w generyczne i zatrybi w obu miejscach.  
	 */

	private List<SimpleAssociableChoiceBean> randomizeChoices(List<SimpleAssociableChoiceBean> associableChoices) {
		List<SimpleAssociableChoiceBean> newSimpleChoiceList = createEmptyChoiceOptionList(associableChoices.size());
		
		if (interaction.isShuffle()) {
			fillRandomizedIndices(associableChoices);
		}	
		
		int iterationIndex = 0;
		for (Iterator<SimpleAssociableChoiceBean> iterator = associableChoices.iterator(); iterator.hasNext();) {
			int optionIndex = iterationIndex;
			SimpleAssociableChoiceBean choiceOption = iterator.next();
			
			if (interaction.isShuffle() && !choiceOption.isFixed()) {
				optionIndex = randomizedIndices.pull();
				choiceOption = associableChoices.get(optionIndex);
			}			
			
			newSimpleChoiceList.set(optionIndex, choiceOption);
			
			iterationIndex++;
		}
		
		return newSimpleChoiceList;
	}
	
	private void fillRandomizedIndices(List<SimpleAssociableChoiceBean> associableChoices){
		int optionsLength = associableChoices.size();
		randomizedIndices = new RandomizedSet<Integer>();
		
		for (int i = 0; i < optionsLength; i++) {
			SimpleAssociableChoiceBean choiceOption = associableChoices.get(i);

			if (!choiceOption.isFixed()) {
				randomizedIndices.push(i);
			}
		}
	}	
	
	private List<SimpleAssociableChoiceBean> createEmptyChoiceOptionList(int size){
		List<SimpleAssociableChoiceBean> choicesOptions = new ArrayList<SimpleAssociableChoiceBean>();

		for (int i = 0; i < size; i++) {
			choicesOptions.add(null);
		}

		return choicesOptions;
	}
	
}
