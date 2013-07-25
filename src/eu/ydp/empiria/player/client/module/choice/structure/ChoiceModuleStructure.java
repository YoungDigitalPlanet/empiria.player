package eu.ydp.empiria.player.client.module.choice.structure;

//import java.util.ArrayList;
import java.util.List;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.ShuffleHelper;
import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;
//import eu.ydp.gwtutil.client.collections.RandomizedSet;
import eu.ydp.gwtutil.client.json.YJsonArray;
import eu.ydp.gwtutil.client.service.json.IJSONService;
import eu.ydp.gwtutil.client.xml.XMLParser;

public class ChoiceModuleStructure extends AbstractModuleStructure<ChoiceInteractionBean, ChoiceModuleJAXBParser> {

	@Inject
	private ChoiceModuleJAXBParser parserFactory;

	@Inject
	private XMLParser xmlParser;
	@Inject
	private IJSONService ijsonService;

	@Inject
	private ShuffleHelper shuffleHelper;

	@Override
	protected void prepareStructure(YJsonArray structure) {
		List<SimpleChoiceBean> randomizedChoices = shuffleHelper.randomizeIfShould(bean, bean.getSimpleChoices());
		bean.setSimpleChoices(randomizedChoices);
	}

	@Override
	protected NodeList getParentNodesForFeedbacks(Document xmlDocument) {
		return xmlDocument.getElementsByTagName(EmpiriaTagConstants.NAME_SIMPLE_CHOICE);
	}

	public void setMulti(boolean multi) {
		for (SimpleChoiceBean simpleChoice : getBean().getSimpleChoices()) {
			simpleChoice.setMulti(multi);
		}
	}

	@Override
	protected ChoiceModuleJAXBParser getParserFactory() {
		return parserFactory;
	}

	public List<SimpleChoiceBean> getSimpleChoices() {
		return getBean().getSimpleChoices();
	}

	@Override
	protected XMLParser getXMLParser() {
		return xmlParser;
	}

	@Override
	public YJsonArray getSavedStructure() {
		return ijsonService.createArray();
	}
}
