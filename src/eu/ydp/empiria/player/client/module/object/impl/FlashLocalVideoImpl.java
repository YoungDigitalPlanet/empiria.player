package eu.ydp.empiria.player.client.module.object.impl;

import com.google.gwt.dom.client.Document;
import com.google.gwt.media.client.MediaBase;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.util.PathUtil;

public class FlashLocalVideoImpl extends Composite implements Video {

	protected String id;
	protected String src;
	protected FlowPanel panelMain;
	protected FlowPanel panelContent;
	private int width = 320;
	private int height = 240;
	
	public FlashLocalVideoImpl(){
		id = Document.get().createUniqueId();
		panelMain = new FlowPanel();
		panelMain.setStyleName("qp-video-flash-local");
		panelContent = new FlowPanel();
		panelContent.getElement().setId(id);
		panelMain.add(panelContent);

		initWidget(panelMain);		
	}
	
	@Override
	public void onLoad() {
		String swfSrc = PathUtil.getPlayerPathDir() + "flvplayer/flvplayer.swf";
		String installSrc = PathUtil.getPlayerPathDir() + "swfobject/expressInstall.swf"; 
		loadFlvPlayerThroughSwfobject(id, swfSrc, installSrc, src, width, height);
	}
	
	private native void loadFlvPlayerThroughSwfobject(String id, String swfSrc, String installSrc, String videoSrc, int width, int height)/*-{
		var flashvars = {video:videoSrc, sizeMode:"1"};
		$wnd.swfobject.embedSWF(swfSrc, id, width, height, "9", installSrc, flashvars);
	}-*/;

	@Override
	public void setSrc(String src) {
		this.src = src;
	}

	@Override
	public void addSrc(String src, String type) {
		setSrc(src);
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
	public void setPoster(String url) {
	}

	@Override
	public void setShowNativeControls(boolean show) {
	}

	@Override
	public MediaBase getMedia() {
		return null;
	}

}
