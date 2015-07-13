package eu.ydp.empiria.player.client.module.img.picture.player.structure;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.NodeList;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.abstractmodule.structure.AbstractModuleStructure;
import eu.ydp.gwtutil.client.json.YJsonArray;
import eu.ydp.gwtutil.client.service.json.IJSONService;
import eu.ydp.gwtutil.client.xml.XMLParser;

public class PicturePlayerStructure extends AbstractModuleStructure<PicturePlayerBean, PicturePlayerJAXBParser> {

    private PicturePlayerJAXBParser parser;
    private XMLParser xmlParser;

    @Inject
    public PicturePlayerStructure(PicturePlayerJAXBParser parser, XMLParser xmlParser, IJSONService ijsonService) {
        this.parser = parser;
        this.xmlParser = xmlParser;
        this.ijsonService = ijsonService;
    }

    private IJSONService ijsonService;

    @Override
    public YJsonArray getSavedStructure() {
        return ijsonService.createArray();
    }

    @Override
    protected PicturePlayerJAXBParser getParserFactory() {
        return parser;
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
