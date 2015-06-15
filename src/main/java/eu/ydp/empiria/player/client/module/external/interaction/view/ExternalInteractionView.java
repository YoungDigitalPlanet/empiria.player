package eu.ydp.empiria.player.client.module.external.interaction.view;

import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.empiria.player.client.module.external.common.ExternalFrameLoadHandler;
import eu.ydp.empiria.player.client.module.external.interaction.api.ExternalInteractionApi;
import eu.ydp.empiria.player.client.module.external.interaction.api.ExternalInteractionEmpiriaApi;

public interface ExternalInteractionView extends IsWidget {
	void init(ExternalInteractionEmpiriaApi api, ExternalFrameLoadHandler<ExternalInteractionApi> onLoadHandler, String url);
}
