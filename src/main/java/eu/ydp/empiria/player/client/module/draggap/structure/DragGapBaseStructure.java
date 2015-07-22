package eu.ydp.empiria.player.client.module.draggap.structure;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParserFactory;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.gwtutil.client.json.YJsonArray;
import eu.ydp.gwtutil.client.service.json.IJSONService;
import eu.ydp.gwtutil.client.xml.XMLParser;

public abstract class DragGapBaseStructure<T extends DragGapBaseBean, U extends JAXBParserFactory<T>> extends AbstractModuleStructure<T, U> {

    @Inject
    private XMLParser xmlParser;

    @Inject
    private IJSONService iJsonService;

    @Override
    public YJsonArray getSavedStructure() {
        return iJsonService.createArray();
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
    protected void prepareStructure(YJsonArray structure) {
    }
}
