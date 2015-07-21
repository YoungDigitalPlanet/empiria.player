package eu.ydp.empiria.player.client.module.sourcelist.view;

import com.google.gwt.dom.client.DivElement;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.XMLParser;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemType;

public class SourceListViewItemContentFactory {
    private static final String startTagString = "<" + DivElement.TAG + ">";
    private static final String endTagString = "</" + DivElement.TAG + ">";

    public IsWidget getSourceListViewItemContent(SourcelistItemType type, String content, InlineBodyGeneratorSocket inlineBodyGeneratorSocket) {
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
        Document parse = XMLParser.parse(nodeString);
        return parse.getDocumentElement();
    }
}
