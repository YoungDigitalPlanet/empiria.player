package eu.ydp.empiria.player.client.module.object.impl;

import com.google.gwt.dom.client.Document;
import com.google.gwt.media.client.MediaBase;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.util.PathUtil;

public class FlashLocalVideoImpl extends FlashLocalMediaImpl implements Video {

	private int width = 320;
	private int height = 240;
	
	public FlashLocalVideoImpl(){
		super("video");
	}

	
	protected native void loadFlvPlayerThroughSwfobject(String id, String swfSrc, String installSrc, String videoSrc, int width, int height)/*-{
		var flashvars = {video:videoSrc, sizeMode:"1"};
		$wnd.swfobject.embedSWF(swfSrc, id, width, height, "9", installSrc, flashvars);
	}-*/;


	@Override
	protected String getSwfSrc() {
		String swfSrc = PathUtil.getPlayerPathDir() + "flvplayer/flvplayer.swf"; 
		return swfSrc;
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
		if (this.src == null)
			setSrc(src);
	}
	
	@Override
	public void setPoster(String url) { }

	@Override
	public void setShowNativeControls(boolean show) { }

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
