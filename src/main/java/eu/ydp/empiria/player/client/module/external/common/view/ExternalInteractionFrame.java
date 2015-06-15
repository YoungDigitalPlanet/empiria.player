package eu.ydp.empiria.player.client.module.external.common.view;

import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.LoadEvent;
import com.google.gwt.event.dom.client.LoadHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;
import eu.ydp.empiria.player.client.module.external.common.ExternalInteractionFrameLoadHandler;
import eu.ydp.empiria.player.client.module.external.interaction.api.ExternalInteractionEmpiriaApi;
import eu.ydp.empiria.player.client.module.external.interaction.api.ExternalInteractionApi;

public class ExternalInteractionFrame extends Composite {

	private final Frame frame = new Frame();

	public ExternalInteractionFrame() {
		initWidget(frame);
	}

	public void init(final ExternalInteractionEmpiriaApi api, final ExternalInteractionFrameLoadHandler onLoadHandler) {
		frame.addLoadHandler(new LoadHandler() {
			@Override
			public void onLoad(LoadEvent event) {
				ExternalInteractionApi obj = init(frame.getElement(), api);
				onLoadHandler.onExternalModuleLoaded(obj);
			}
		});
	}

	public void setUrl(String url) {
		frame.setUrl(url);
	}

	private native ExternalInteractionApi init(Element frame, ExternalInteractionEmpiriaApi api)/*-{
        return frame.contentWindow.init(api);
    }-*/;
}
