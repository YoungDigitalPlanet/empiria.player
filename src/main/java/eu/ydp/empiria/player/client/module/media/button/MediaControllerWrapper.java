package eu.ydp.empiria.player.client.module.media.button;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import eu.ydp.empiria.player.client.module.media.MediaAvailableOptions;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;

public class MediaControllerWrapper implements MediaController {

    private final Widget widget;

    public MediaControllerWrapper(Widget widget) {
        FlowPanel panel = new FlowPanel();
        panel.add(widget);
        this.widget = panel;// widget;
    }

    @Override
    public boolean isSupported() {
        return true;
    }

    @Override
    public Widget asWidget() {
        return widget;
    }

    @Override
    public void setMediaDescriptor(MediaWrapper<?> mediaDescriptor) {
        //
    }

    @Override
    public MediaAvailableOptions getMediaAvailableOptions() {
        return null;
    }

    @Override
    public MediaWrapper<?> getMediaWrapper() {
        return null;
    }

    @Override
    public void setFullScreen(boolean fullScreen) {
        //
    }

    @Override
    public Element getElement() {
        return null;
    }

    @Override
    public void init() {
        //
    }

}
