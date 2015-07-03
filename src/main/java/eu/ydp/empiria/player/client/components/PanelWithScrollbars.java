package eu.ydp.empiria.player.client.components;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import eu.ydp.gwtutil.client.NumberUtils;

import java.util.Iterator;

public class PanelWithScrollbars implements IsWidget, HasWidgets {

    private static PanelWithScrollbarsUiBinder uiBinder = GWT.create(PanelWithScrollbarsUiBinder.class);

    interface PanelWithScrollbarsUiBinder extends UiBinder<Widget, PanelWithScrollbars> {
    }

    @UiField
    AbsolutePanel mainPanel;
    @UiField
    AbsolutePanel horizontalScrollPanel;
    @UiField
    AbsolutePanel verticalScrollPanel;
    @UiField
    AbsolutePanel horizontalScrollSlider;
    @UiField
    AbsolutePanel verticalScrollSlider;
    private int mainPanelHeight;
    private int mainPanelWidth;
    private Timer fadeoutTimer;

    public PanelWithScrollbars() {
        uiBinder.createAndBindUi(this);

        fadeoutTimer = new Timer() {

            @Override
            public void run() {
                fadeOutElement(verticalScrollPanel.getElement(), 200);
                fadeOutElement(horizontalScrollPanel.getElement(), 200);
            }
        };
    }

    public void setSize(String width, String height) {
        mainPanel.setSize(width, height);
        mainPanelHeight = NumberUtils.tryParseInt(height.replaceAll("\\D", ""), 0);
        mainPanelWidth = NumberUtils.tryParseInt(width.replaceAll("\\D", ""), 0);
    }

    public void setHorizontalPosition(double position, double sizeCurrent, double sizeAll, boolean showScrolls) {
        double left = horizontalScrollPanel.getOffsetWidth() * ((position / sizeAll));
        horizontalScrollPanel.setWidgetPosition(horizontalScrollSlider, (int) left, 0);
        double width = horizontalScrollPanel.getOffsetWidth() * sizeCurrent / sizeAll;
        horizontalScrollSlider.setWidth(String.valueOf((int) width) + "px");
        if (showScrolls)
            showScrollbars();
    }

    public void setVerticalPosition(double position, double sizeCurrent, double sizeAll, boolean showScrolls) {
        double top = verticalScrollPanel.getOffsetHeight() * ((position / sizeAll));
        verticalScrollPanel.setWidgetPosition(verticalScrollSlider, 0, (int) top);
        double height = verticalScrollPanel.getOffsetHeight() * sizeCurrent / sizeAll;
        verticalScrollSlider.setHeight(String.valueOf((int) height) + "px");
        if (showScrolls)
            showScrollbars();
    }

    @Override
    public Widget asWidget() {
        return mainPanel;
    }

    @Override
    public void add(Widget w) {
        mainPanel.add(w);
    }

    public void add(IsWidget w) {
        mainPanel.add(w);
    }

    @Override
    public void clear() {
        mainPanel.clear();
    }

    @Override
    public Iterator<Widget> iterator() {
        return mainPanel.iterator();
    }

    @Override
    public boolean remove(Widget w) {
        return mainPanel.remove(w);
    }

    private void showScrollbars() {
        fadeoutTimer.cancel();

        int hScrollHeight = horizontalScrollPanel.getOffsetHeight();
        mainPanel.setWidgetPosition(horizontalScrollPanel, 0, mainPanelHeight - hScrollHeight);
        int vScrollWidth = verticalScrollPanel.getOffsetWidth();
        mainPanel.setWidgetPosition(verticalScrollPanel, mainPanelWidth - vScrollWidth, 0);

        setScrollbarsVisible();

        fadeoutTimer.schedule(500);
    }

    private void setScrollbarsVisible() {
        verticalScrollPanel.setVisible(true);
        horizontalScrollPanel.setVisible(true);
        opacityto(verticalScrollPanel.getElement(), 100);
        opacityto(horizontalScrollPanel.getElement(), 100);

    }

    protected native void opacityto(com.google.gwt.dom.client.Element elm, int v)/*-{
        elm.style.opacity = v / 100;
        elm.style.MozOpacity = v / 100;
        elm.style.KhtmlOpacity = v / 100;
        elm.style.filter = " alpha(opacity =" + v + ")";
    }-*/;

    protected native void fadeOutElement(com.google.gwt.dom.client.Element element, int fadeEffectTime)/*-{
        var instance = this;
        var _this = element;
        instance.@eu.ydp.empiria.player.client.components.PanelWithScrollbars::opacityto(Lcom/google/gwt/dom/client/Element;I)(_this, 100);
        var delay = fadeEffectTime;
        _this.style.zoom = 1; // for ie, set haslayout
        _this.style.display = "block";
        for (i = 0; i <= 100; i += 5) {
            (function (j) {
                setTimeout(function () {
                    j = 100 - j;
                    instance.@eu.ydp.empiria.player.client.components.PanelWithScrollbars::opacityto(Lcom/google/gwt/dom/client/Element;I)(_this, j);
                }, j * delay / 100);

            })(i);
        }
    }-*/;

}
