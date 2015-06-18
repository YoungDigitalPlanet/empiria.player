package eu.ydp.empiria.player.client.module.external.common.view;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;
import eu.ydp.empiria.player.client.module.external.common.ExternalFrameLoadHandler;
import eu.ydp.empiria.player.client.module.external.common.api.ExternalApi;
import eu.ydp.empiria.player.client.module.external.common.api.ExternalEmpiriaApi;

public class ExternalFrame<T extends ExternalApi> extends Composite {

	private final Frame frame = new Frame();

	public ExternalFrame() {
		initWidget(frame);
	}

	public void init(final ExternalEmpiriaApi api, final ExternalFrameLoadHandler<T> onLoadHandler) {
		frame.addLoadHandler(new LoadHandler() {
			@Override
			public void onLoad(LoadEvent event) {
				T obj = init(frame.getElement(), api);
				onLoadHandler.onExternalModuleLoaded(obj);
			}
		});
	}

	public void setUrl(String url) {
		frame.setUrl(url);
	}

	private native T init(Element frame, ExternalEmpiriaApi api)/*-{
        return frame.contentWindow.init(api);
    }-*/;
}
