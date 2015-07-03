package eu.ydp.empiria.player.client.module.selection.structure;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.ShuffleHelper;
import eu.ydp.empiria.player.client.resources.EmpiriaTagConstants;
import eu.ydp.gwtutil.client.json.YJsonArray;
import eu.ydp.gwtutil.client.service.json.IJSONService;
import eu.ydp.gwtutil.client.xml.XMLParser;

public class SelectionModuleStructure extends AbstractModuleStructure<SelectionInteractionBean, SelectionModuleJAXBParser> {

    @Inject
    private SelectionModuleJAXBParser parserFactory;
    @Inject
    private XMLParser xmlParser;
    @Inject
    private ShuffleHelper shuffleHelper;
    @Inject
    private IJSONService ijsonService;

    @Override
    protected SelectionModuleJAXBParser getParserFactory() {
        return parserFactory;
    }

    @Override
    protected void prepareStructure(YJsonArray structure) {
        shuffleHelper.randomizeIfShould(bean, bean.getItems());
    }

    @Override
    protected XMLParser getXMLParser() {
        return xmlParser;
    }

    @Override
    protected NodeList getParentNodesForFeedbacks(Document xmlDocument) {
        return xmlDocument.getElementsByTagName(EmpiriaTagConstants.NAME_SIMPLE_CHOICE);
    }

    @Override
    public YJsonArray getSavedStructure() {
        return ijsonService.createArray();
    }

}
