package eu.ydp.empiria.player.client.module.object.impl;

import com.google.gwt.core.client.GWT;
import com.google.gwt.media.client.MediaBase;

import eu.ydp.empiria.player.client.module.media.MediaWrapper;
import eu.ydp.gwtutil.client.PathUtil;

public class FlashLocalVideoImpl extends FlashLocalMediaImpl implements Video {

	private int width = 320;
	private int height = 240;

	public FlashLocalVideoImpl(){
		super("video");
	}


	@Override
	protected native void loadFlvPlayerThroughSwfobject(String id, String swfSrc, String installSrc, String videoSrc, int width, int height)/*-{
		var flashvars = {video:videoSrc, sizeMode:"1"};
		$wnd.swfobject.embedSWF(swfSrc, id, width, height, "9", installSrc, flashvars);
	}-*/;


	@Override
	protected String getSwfSrc() {
		return GWT.getModuleBaseURL() + "flvplayer/flvplayer.swf";
	}


	@Override
	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public void addSrc(String src, String type) {
		if (this.src == null) {
			setSrc(src);
		}
	}

	@Override
	public void setPoster(String url) { }

	@Override
	public void setShowNativeControls(boolean show) { }

	@Override
	public void setEventBusSourceObject(MediaWrapper<?> object) {}
	@Override
	public MediaBase getMedia() {
		return null;
	}



	@Override
	protected int getWidth() {
		return width;
	}


	@Override
	protected int getHeight() {
		return height;
	}


}
