package eu.ydp.empiria.player.client.module.external.interaction;

import com.google.common.collect.Lists;
import com.google.gwt.json.client.JSONArray;
import com.google.inject.Inject;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.AbstractResponseModel;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

import java.util.Collection;
import java.util.List;

public class ExternalInteractionResponseModel extends AbstractResponseModel<String> {

    private final ExternalInteractionStateProvider stateProvider;

    @Inject
    public ExternalInteractionResponseModel(@ModuleScoped Response response, ExternalInteractionStateProvider stateProvider) {
        super(response);
        this.stateProvider = stateProvider;

    }

    @Override
    protected List<String> parseResponse(Collection<String> values) {
        return Lists.newArrayList(values);
    }

    @Override
    public JSONArray getState() {
        return stateProvider.getState();
    }
}
