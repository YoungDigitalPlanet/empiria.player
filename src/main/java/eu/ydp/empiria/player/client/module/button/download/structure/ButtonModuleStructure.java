package eu.ydp.empiria.player.client.module.button.download.structure;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.gwtutil.client.json.YJsonArray;
import eu.ydp.gwtutil.client.service.json.IJSONService;
import eu.ydp.gwtutil.client.xml.XMLParser;

public class ButtonModuleStructure extends AbstractModuleStructure<ButtonBean, ButtonModuleJAXBParser> {

    @Inject
    private XMLParser xmlParser;
    @Inject
    private IJSONService ijsonService;
    @Inject
    private ButtonModuleJAXBParser moduleJAXBParser;

    @Override
    public YJsonArray getSavedStructure() {
        return ijsonService.createArray();
    }

    @Override
    protected ButtonModuleJAXBParser getParserFactory() {
        return moduleJAXBParser;
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
