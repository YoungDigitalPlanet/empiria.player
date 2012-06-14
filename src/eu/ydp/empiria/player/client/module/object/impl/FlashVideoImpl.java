package eu.ydp.empiria.player.client.module.object.impl;

import com.google.gwt.dom.client.Document;
import com.google.gwt.media.client.MediaBase;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;

public class FlashVideoImpl extends Composite implements Video {

	protected String id;
	protected String src;

	public FlashVideoImpl() {
		id = Document.get().createUniqueId();
		HTML html = new HTML("<div id='" + id + "' class='qp-video'></div>");

		initWidget(html);
	}

	public void setSrc(String src) {
		this.src = src;
	}

	@Override
	public void onLoad() {
		initFAV(id, src);
	}

	private native void initFAV(String id, String src)/*-{
		if (typeof $wnd.FAVideo == 'function')
			var vp = new $wnd.FAVideo(id, src, 0, 0, {
				autoLoad : true,
				autoPlay : false
			});
	}-*/;

	@Override
	public void addSrc(String src, String type) {
		setSrc(src);
	}

	@Override
	public void setWidth(int width) {
	}

	@Override
	public void setHeight(int height) {
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
