package eu.ydp.empiria.player.client.module.ordering;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;
import eu.ydp.empiria.player.client.module.AbstractResponseModel;
import eu.ydp.empiria.player.client.module.ResponseModelChangeListener;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

import java.util.Collection;
import java.util.List;

public class OrderInteractionModuleModel extends AbstractResponseModel<String> {

    @Inject
    public OrderInteractionModuleModel(@ModuleScoped Response response) {
        super(response);
    }

    public void initialize(ResponseModelChangeListener modelChangeListener) {
        setResponseModelChange(modelChangeListener);
    }

    @Override
    protected List<String> parseResponse(Collection<String> values) {
        return Lists.newArrayList(values);
    }

    @Override
    public void onModelChange() {
        super.onModelChange();
    }

    @Override
    public void reset() {

    }
}
