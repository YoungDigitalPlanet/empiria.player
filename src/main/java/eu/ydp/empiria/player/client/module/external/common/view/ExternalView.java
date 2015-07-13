package eu.ydp.empiria.player.client.module.external.common.view;

import com.google.gwt.user.client.ui.IsWidget;
import eu.ydp.empiria.player.client.module.external.common.ExternalFrameLoadHandler;
import eu.ydp.empiria.player.client.module.external.common.api.ExternalApi;
import eu.ydp.empiria.player.client.module.external.common.api.ExternalEmpiriaApi;

public interface ExternalView<T extends ExternalApi, K extends ExternalEmpiriaApi> extends IsWidget {
    void init(K api, ExternalFrameLoadHandler<T> onLoadHandler, String url);
}
