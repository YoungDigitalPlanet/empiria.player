package eu.ydp.empiria.player.client.module.external.interaction;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONArray;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.module.external.common.state.ExternalStateEncoder;
import eu.ydp.empiria.player.client.module.external.common.state.ExternalStateSaver;
import eu.ydp.empiria.player.client.module.external.interaction.api.ExternalApiProvider;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

public class ExternalInteractionStateProvider {

    private final ExternalApiProvider externalApi;
    private final ExternalStateSaver stateSaver;
    private final ExternalStateEncoder stateEncoder;

    @Inject
    public ExternalInteractionStateProvider(@ModuleScoped ExternalApiProvider externalApi, ExternalStateSaver stateSaver, ExternalStateEncoder stateEncoder) {
        this.externalApi = externalApi;
        this.stateSaver = stateSaver;
        this.stateEncoder = stateEncoder;
    }

    public JSONArray getState() {
        JavaScriptObject state = externalApi.getExternalApi().getStateFromExternal();
        stateSaver.setExternalState(state);
        return stateEncoder.encodeState(state);
    }
}
