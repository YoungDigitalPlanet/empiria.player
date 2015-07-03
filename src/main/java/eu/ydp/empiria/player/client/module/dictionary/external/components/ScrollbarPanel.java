package eu.ydp.empiria.player.client.module.dictionary.external.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.*;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;

public class ScrollbarPanel extends Composite {

    private static ScrollbarPanelUiBinder uiBinder = GWT.create(ScrollbarPanelUiBinder.class);

    interface ScrollbarPanelUiBinder extends UiBinder<Widget, ScrollbarPanel> {
    }

    @Inject
    private StyleNameConstants styleNameConstants;

    @UiField
    AbsolutePanel scrollPanelContainer;
    @UiField
    FlowPanel scrollPanel;
    @UiField
    FlowPanel scrollHeader;
    @UiField
    FlowPanel scrollBody;
    @UiField
    FlowPanel scrollFooter;
    @Inject
    private ScrollBarPanelNative scrollBarPanelNative;
    protected Timer scrollbarTimer;

    public ScrollbarPanel() {
        initWidget(uiBinder.createAndBindUi(this));

        scrollbarTimer = new Timer() {

            @Override
            public void run() {
                hideScrollBar();
            }
        };
    }

    public Panel getScrollPanel() {
        return scrollPanelContainer;
    }

    public void updateScrollBar(Panel container, Panel contents) {
        int scrollTop = getScrollTop(contents.getElement());
        int heightAll = getHeight(contents.getElement());
        int heightVisible = container.getOffsetHeight();
        Integer scrollbarHeight = heightVisible * heightVisible / heightAll;
        int scrollbarTop;
        if (heightAll - heightVisible > 0) {
            scrollbarTop = (heightVisible - scrollbarHeight) * (scrollTop) / (heightAll - heightVisible);
        } else {
            scrollbarTop = 0;
        }
        scrollPanel.setHeight(scrollbarHeight.toString() + "px");
        scrollPanelContainer.setWidgetPosition(scrollPanel, 0, scrollbarTop);
        showScrollBar();
    }

    protected void hideScrollBar() {
        fadeOutJs(scrollPanel.getElement(), 200);
    }

    protected void showScrollBar() {
        scrollbarTimer.cancel();
        scrollbarTimer.schedule(500);
        opacityto(scrollPanel.getElement(), 100);
        scrollPanel.setStyleName(styleNameConstants.QP_DICTIONARY_SCROLL_CONTAINER());
    }

    public int getScrollTop(Element el) {
        return scrollBarPanelNative.getScrollTop(el);
    }

    public void setScrollTop(Element el, int value) {
        scrollBarPanelNative.setScrollTop(el, value);
    }

    protected int getHeight(Element el) {
        return scrollBarPanelNative.getHeight(el);
    }

    protected void opacityto(com.google.gwt.dom.client.Element elm, int v) {
        scrollBarPanelNative.opacityto(elm, v);
    }

    protected void fadeOutJs(com.google.gwt.dom.client.Element element, int fadeEffectTime) {
        scrollBarPanelNative.fadeOutJs(element, fadeEffectTime);
    }
}
