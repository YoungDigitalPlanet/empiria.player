package eu.ydp.empiria.player.client.module.ordering.structure;

import java.util.List;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.ShuffleHelper;
import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;
import eu.ydp.gwtutil.client.json.YJsonArray;
import eu.ydp.gwtutil.client.service.json.IJSONService;
import eu.ydp.gwtutil.client.xml.XMLParser;

public class OrderInteractionStructure extends AbstractModuleStructure<OrderInteractionBean, OrderInteractionModuleJAXBParserFactory>  {

	@Inject
	private OrderInteractionModuleJAXBParserFactory parserFactory;

	@Inject
	private XMLParser xmlParser;

	@Inject
	private IJSONService ijsonService;

	@Inject
	private ShuffleHelper shuffleHelper;

	@Override
	public YJsonArray getSavedStructure() {
		return ijsonService.createArray();
	}

	@Override
	protected OrderInteractionModuleJAXBParserFactory getParserFactory() {
		return parserFactory;
	}

	@Override
	protected void prepareStructure(YJsonArray structure) {
		List<SimpleOrderChoiceBean> randomizedBeans = shuffleHelper.randomize(bean, bean.getChoiceBeans());
		bean.setChoiceBeans(randomizedBeans);
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
