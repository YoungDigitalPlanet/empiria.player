package eu.ydp.empiria.player.client.module.external.interaction.structure;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.gwtutil.client.json.YJsonArray;
import eu.ydp.gwtutil.client.service.json.IJSONService;
import eu.ydp.gwtutil.client.xml.XMLParser;

public class ExternalInteractionModuleStructure extends AbstractModuleStructure<ExternalInteractionModuleBean, ExternalInteractionModuleJAXBParserFactory> {

    @Inject
    private ExternalInteractionModuleJAXBParserFactory parserFactory;

    @Inject
    private XMLParser xmlParser;

    @Inject
    private IJSONService iJsonService;

    @Override
    public YJsonArray getSavedStructure() {
        return iJsonService.createArray();
    }

    @Override
    protected ExternalInteractionModuleJAXBParserFactory getParserFactory() {
        return parserFactory;
    }

    @Override
    protected void prepareStructure(YJsonArray structure) {

    }

    @Override
    protected XMLParser getXMLParser() {
        return xmlParser;
    }

    @Override
    protected NodeList getParentNodesForFeedbacks(Document xmlDocument) {
        return null;
    }
}
