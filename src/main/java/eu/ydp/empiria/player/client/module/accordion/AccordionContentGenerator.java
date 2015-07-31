package eu.ydp.empiria.player.client.module.accordion;

import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Node;
import eu.ydp.empiria.player.client.controller.body.BodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;

public class AccordionContentGenerator {

    private final InlineBodyGeneratorSocket inlineBodyGeneratorSocket;
    private final BodyGeneratorSocket bodyGeneratorSocket;

    public AccordionContentGenerator(InlineBodyGeneratorSocket inlineBodyGeneratorSocket, BodyGeneratorSocket bodyGeneratorSocket) {
        this.inlineBodyGeneratorSocket = inlineBodyGeneratorSocket;
        this.bodyGeneratorSocket = bodyGeneratorSocket;
    }

    public void generateBody(Node xmlNode, HasWidgets parent) {
        bodyGeneratorSocket.generateBody(xmlNode, parent);
    }

    public Widget generateInlineBody(Node xmlNode) {
        return inlineBodyGeneratorSocket.generateInlineBody(xmlNode);
    }
}
