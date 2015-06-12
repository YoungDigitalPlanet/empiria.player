package eu.ydp.empiria.player.client.module.external.interaction.view;

import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.empiria.player.client.module.external.common.ExternalInteractionFrameLoadHandler;
import eu.ydp.empiria.player.client.module.external.object.ExternalInteractionEmpiriaApi;

public interface ExternalInteractionView extends IsWidget {
	void init(ExternalInteractionEmpiriaApi api, ExternalInteractionFrameLoadHandler onLoadHandler, String url);
}
