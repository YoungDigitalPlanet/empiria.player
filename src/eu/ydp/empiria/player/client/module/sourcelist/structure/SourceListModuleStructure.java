package eu.ydp.empiria.player.client.module.sourcelist.structure;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
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
