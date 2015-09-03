package eu.ydp.empiria.player.client.module.core.base;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.components.ElementWrapperWidget;
import eu.ydp.empiria.player.client.module.InlineFormattingContainerType;

import java.util.List;

/**
 * Gives possibility to use inline formatting for modules implementing IInlineModule
 */
public class InlineContainerModule extends InlineModuleBase implements IInlineContainerModule {

    private ElementWrapperWidget widget;
    private InlineFormattingContainerType type;

    @Override
    public Widget getView() {
        return widget;
    }

    @Override
    protected void initModule(Element element) {
        widget = new ElementWrapperWidget(Document.get().createElement(element.getNodeName()));
        type = parseNodeName(element);
    }

    private InlineFormattingContainerType parseNodeName(Element element) {
        String nodeName = element.getNodeName().toUpperCase();

        InlineFormattingContainerType type = null;

        if ("B".equals(nodeName)) {
            type = InlineFormattingContainerType.BOLD;
        } else if ("STRONG".equals(nodeName)) {
            type = InlineFormattingContainerType.BOLD;
        } else if ("I".equals(nodeName)) {
            type = InlineFormattingContainerType.ITALIC;
        }

        return type;
    }

    @Override
    public InlineFormattingContainerType getType() {
        return type;
    }

    @Override
    public List<IModule> getChildrenModules() {
        return getModuleSocket().getChildren(this);
    }

    @Override
    public List<HasParent> getNestedChildren() {
        return getModuleSocket().getNestedChildren(this);
    }

}
