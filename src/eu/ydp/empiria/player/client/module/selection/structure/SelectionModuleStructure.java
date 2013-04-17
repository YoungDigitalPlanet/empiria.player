package eu.ydp.empiria.player.client.module.selection.structure;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.ShuffleHelper;
import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;
import eu.ydp.gwtutil.client.xml.XMLParser;

public class SelectionModuleStructure extends AbstractModuleStructure<SelectionInteractionBean, SelectionModuleJAXBParser> {

	@Inject
	private SelectionModuleJAXBParser parserFactory;
	@Inject
	private XMLParser xmlParser;
	@Inject
	private ShuffleHelper shuffleHelper;

	@Override
	protected SelectionModuleJAXBParser getParserFactory() {
		return parserFactory;
	}

	@Override
	protected JSONArray prepareStructure() {
		shuffleHelper.randomize(bean, bean.getItems());
		return null;
	}

	@Override
	protected XMLParser getXMLParser() {
		return xmlParser;
	}

	@Override
	protected NodeList getParentNodesForFeedbacks(Document xmlDocument) {
		return xmlDocument.getElementsByTagName(EmpiriaTagConstants.NAME_SIMPLE_CHOICE);
	}

}
