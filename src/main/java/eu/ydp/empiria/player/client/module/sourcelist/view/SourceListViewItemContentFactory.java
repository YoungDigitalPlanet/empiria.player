package eu.ydp.empiria.player.client.module.sourcelist.view;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemType;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemValue;
import eu.ydp.gwtutil.client.xml.XMLParser;

public class SourceListViewItemContentFactory {

    private static final String startTagString = "<" + DivElement.TAG + ">";
    private static final String endTagString = "</" + DivElement.TAG + ">";

    @Inject
    private XMLParser xmlParser;

    public IsWidget createSourceListContentWidget(SourcelistItemValue item, InlineBodyGeneratorSocket inlineBodyGeneratorSocket){
        SourcelistItemType type = item.getType();
        String content = item.getContent();
        return createSourceListContentWidget(type, content, inlineBodyGeneratorSocket);

    }

    public IsWidget createSourceListContentWidget(SourcelistItemType type, String content, InlineBodyGeneratorSocket inlineBodyGeneratorSocket) {
        switch (type) {
            case IMAGE:
                return new Image(content);
            case TEXT:
                return new Label(content);
            case COMPLEX_TEXT:
                Node node = getNode(content);
                return inlineBodyGeneratorSocket.generateInlineBody(node);
        }

        return new Widget();
    }

    private Node getNode(String content) {
        String nodeString = startTagString + content + endTagString;
        Document parse = xmlParser.parse(nodeString);
        return parse.getDocumentElement();
    }
}
