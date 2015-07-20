package eu.ydp.empiria.player.client.module.sourcelist.view;

import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.dragdrop.SourcelistItemType;
import eu.ydp.gwtutil.client.event.factory.EventHandlerProxy;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;

public class SourceListViewItemWidget extends FlowPanel {

    private static final String STYLE = "style";
    private static final String VISIBILITY_HIDDEN = "visibility:hidden";
    private static final String VISIBILITY_VISIBLE = "visibility:visible";
    private
    @Inject
    SourceListViewItemContentFactory contentFactory;
    private
    @Inject
    UserInteractionHandlerFactory interactionHandlerFactory;

    private final DisableDefaultBehaviorCommand disableDefaultBehavior = new DisableDefaultBehaviorCommand();

    public void initView(SourcelistItemType sourcelistItemType, String itemContent, String styleName, InlineBodyGeneratorSocket inlineBodyGeneratorSocket) {
        setStyleName(styleName);
        IsWidget content = contentFactory.getSourceListViewItemContent(sourcelistItemType, itemContent, inlineBodyGeneratorSocket);
        add(content);
        disableDefaultBehaviorOnSelect(content);

    }

    private void disableDefaultBehaviorOnSelect(IsWidget content) {
        EventHandlerProxy userOverHandler = interactionHandlerFactory.createUserOverHandler(disableDefaultBehavior);
        userOverHandler.apply(content.asWidget());
    }

    public int getWidth() {
        return getElement().getOffsetWidth();
    }

    public int getHeight() {
        return getElement().getOffsetHeight();
    }

    public void show() {
        getElement().setAttribute(STYLE, VISIBILITY_VISIBLE);
    }

    public void hide() {
        getElement().setAttribute(STYLE, VISIBILITY_HIDDEN);
    }
}
