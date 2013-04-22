package eu.ydp.empiria.player.client.module.connection.structure;

import java.util.List;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.ShuffleHelper;
import eu.ydp.gwtutil.client.json.YJsonArray;
import eu.ydp.gwtutil.client.xml.XMLParser;

public class ConnectionModuleStructure extends AbstractModuleStructure<MatchInteractionBean, ConnectionModuleJAXBParser> {

	@Inject
	ConnectionModuleJAXBParser connectionModuleJAXBParser;

	@Inject
	private XMLParser xmlParser;

	@Inject
	private ShuffleHelper shuffleHelper;

	@Inject
	private StructureController structureController;
	private YJsonArray savedStructure;

	@Override
	protected ConnectionModuleJAXBParser getParserFactory() {
		return connectionModuleJAXBParser;
	}

	@Override
	protected void prepareStructure(YJsonArray structure) {

		if (structureController.isStructureExist(structure)) {
			List<SimpleMatchSetBean> simpleMatchSets = bean.getSimpleMatchSets();
			bean.setSimpleMatchSets(structureController.loadStructure(structure, simpleMatchSets));

		} else {
			randomizeSets();
		}
		savedStructure = structureController.saveStructure(bean.getSimpleMatchSets());

	}

	private void randomizeSets() {
		List<SimpleMatchSetBean> simpleMatchSets = bean.getSimpleMatchSets();

		for (SimpleMatchSetBean simpleMatchSetBean : simpleMatchSets) {
			simpleMatchSetBean.setSimpleAssociableChoices(randomizeChoices(simpleMatchSetBean.getSimpleAssociableChoices()));
		}
	}

	private List<SimpleAssociableChoiceBean> randomizeChoices(List<SimpleAssociableChoiceBean> associableChoices) {
		return shuffleHelper.randomize(bean, associableChoices);
	}

	@Override
	protected XMLParser getXMLParser() {
		return xmlParser;
	}

	@Override
	protected NodeList getParentNodesForFeedbacks(Document xmlDocument) {
		return null;
	}

	@Override
	public YJsonArray getSavedStructure() {
		return savedStructure;
	}

}
