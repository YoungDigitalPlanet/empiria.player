package eu.ydp.empiria.player.client.module.object.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.media.client.MediaBase;
import eu.ydp.empiria.player.client.module.media.BaseMediaConfiguration;
import eu.ydp.empiria.player.client.module.media.MediaWrapper;

public class FlashLocalVideoImpl extends FlashLocalMediaImpl implements Video {

    private final BaseMediaConfiguration baseMediaConfiguration;

    public FlashLocalVideoImpl(BaseMediaConfiguration baseMediaConfiguration) {
        super("video");
        this.baseMediaConfiguration = baseMediaConfiguration;
    }

    @Override
    protected native void loadFlvPlayerThroughSwfobject(String id, String swfSrc, String installSrc, String videoSrc, int width, int height)/*-{
        var flashvars = {
            video: videoSrc,
            sizeMode: "1"
        };
        var params = {
            allowFullScreen: true
        };
        $wnd.swfobject.embedSWF(swfSrc, id, width, height, "9", installSrc,
            flashvars, params);
    }-*/;

    @Override
    protected String getSwfSrc() {
        return GWT.getModuleBaseURL() + "flvplayer/flvplayer.swf";
    }

    @Override
    public void setWidth(int width) {
    }

    @Override
    public void setHeight(int height) {
    }

    @Override
    public void addSrc(String src, String type) {
        if (this.src == null) {
            setSrc(src);
        }
    }

    @Override
    public void setPoster(String url) {
    }

    @Override
    public void setShowNativeControls(boolean show) {
    }

    @Override
    public void setEventBusSourceObject(MediaWrapper<?> object) {
    }

    @Override
    public MediaBase getMedia() {
        return null;
    }

    @Override
    protected int getWidth() {
        return baseMediaConfiguration.getWidth();
    }

    @Override
    protected int getHeight() {
        return baseMediaConfiguration.getHeight();
    }

}
