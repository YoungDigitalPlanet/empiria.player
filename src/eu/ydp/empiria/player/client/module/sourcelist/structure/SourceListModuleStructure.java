package eu.ydp.empiria.player.client.module.sourcelist.structure;

import java.util.List;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.ShuffleHelper;
import eu.ydp.gwtutil.client.xml.XMLParser;

public class SourceListModuleStructure extends AbstractModuleStructure<SourceListBean, SourceListJAXBParser> {

	@Inject
	SourceListJAXBParser parser;

	@Inject
	XMLParser xmlParser;

	@Override
	protected SourceListJAXBParser getParserFactory() {
		return parser;
	}

	@Override
	protected void prepareStructure() {
		if (getBean().isShuffle()) {
			shuffle();
		}
	}

	protected void shuffle() {
		ShuffleHelper shuffleHelper = new ShuffleHelper();
		List<SimpleSourceListItemBean> randomizeList = shuffleHelper.randomize(bean, bean.getSimpleSourceListItemBeans());
		bean.setSimpleSourceListItemBeans(randomizeList);
	}

	@Override
	protected NodeList getParentNodesForFeedbacks(Document xmlDocument) {
		return null;
	}

	@Override
	protected XMLParser getXMLParser() {
		return xmlParser;
	}

}
