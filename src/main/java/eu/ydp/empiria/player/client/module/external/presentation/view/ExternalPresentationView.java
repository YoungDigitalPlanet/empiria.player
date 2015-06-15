package eu.ydp.empiria.player.client.module.external.presentation.view;

import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.empiria.player.client.module.external.common.ExternalFrameLoadHandler;
import eu.ydp.empiria.player.client.module.external.common.api.ExternalApi;
import eu.ydp.empiria.player.client.module.external.common.api.ExternalEmpiriaApi;

public interface ExternalPresentationView extends IsWidget {
	void init(ExternalEmpiriaApi api, ExternalFrameLoadHandler<ExternalApi> onLoadHandler, String url);
}
