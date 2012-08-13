package eu.ydp.empiria.player.client.module.object.impl;

import com.google.gwt.dom.client.Document;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

import eu.ydp.empiria.player.client.util.PathUtil;

public abstract class FlashLocalMediaImpl extends Composite {

	protected String elementId;
	protected String src;
	protected FlowPanel panelMain;
	protected FlowPanel panelContent;

	public FlashLocalMediaImpl(String name){
		elementId = Document.get().createUniqueId();
		panelMain = new FlowPanel();
		panelMain.setStyleName("qp-" + name + "-flash-local");
		panelContent = new FlowPanel();
		panelContent.getElement().setId(elementId);
		panelMain.add(panelContent);

		initWidget(panelMain);
	}

	@Override
	public void onLoad() {
		String swfSrc = getSwfSrc();
		String installSrc = PathUtil.getPlayerPathDir() + "swfobject/expressInstall.swf";
		String srcNormalized = PathUtil.normalizePath(src);
		loadFlvPlayerThroughSwfobject(elementId, swfSrc, installSrc, srcNormalized, getWidth(), getHeight());
	}

	protected abstract void loadFlvPlayerThroughSwfobject(String id, String swfSrc, String installSrc, String videoSrc, int width, int height);

	protected abstract String getSwfSrc();
	protected abstract int getWidth();
	protected abstract int getHeight();


	public void setSrc(String src) {
		this.src = src;
	}

}
