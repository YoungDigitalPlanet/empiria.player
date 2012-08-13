package eu.ydp.empiria.player.client.module.object.impl;

import com.google.gwt.media.client.MediaBase;

import eu.ydp.empiria.player.client.util.PathUtil;

public class FlashLocalAudioImpl extends FlashLocalMediaImpl implements Audio {


	public FlashLocalAudioImpl(){
		super("audio");
	}


	@Override
	protected native void loadFlvPlayerThroughSwfobject(String id, String swfSrc, String installSrc, String mediaSrc, int width, int height) /*-{
		var flashvars = {soundFile:mediaSrc, playerID:id, animation:"no", noinfo:"yes"};
		$wnd.swfobject.embedSWF(swfSrc, id, width, height, "9", installSrc, flashvars);

	}-*/;


	@Override
	protected String getSwfSrc() {
		return  PathUtil.getPlayerPathDir() + "wpaudioplayer/wpaudioplayer.swf";
	}


	@Override
	public void addSrc(String src, String type) {
		if (this.src == null) {
			setSrc(src);
		}
	}

	@Override
	public void setShowNativeControls(boolean show) {}

	@Override
	public void setEventBusSourceObject(Object object) {}

	@Override
	public MediaBase getMedia() {
		return null;
	}

	@Override
	protected int getWidth() {
		return 160;
	}

	@Override
	protected int getHeight() {
		return 24;
	}

}
