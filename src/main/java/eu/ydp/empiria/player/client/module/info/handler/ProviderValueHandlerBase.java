package eu.ydp.empiria.player.client.module.info.handler;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.module.info.ContentFieldInfo;

public abstract class ProviderValueHandlerBase implements FieldValueHandler {

    protected SessionDataSupplier sessionDataSupplier;

    @Inject
    public ProviderValueHandlerBase(@Assisted SessionDataSupplier sessionDataSupplier) {
        this.sessionDataSupplier = sessionDataSupplier;
    }

    @Override
    public String getValue(ContentFieldInfo info, int refItemIndex) {
        VariableProviderSocket variableProvider = getVariableProvider(refItemIndex);
        return countValue(info, variableProvider);
    }

    abstract protected String countValue(ContentFieldInfo info, VariableProviderSocket provider);

    abstract protected VariableProviderSocket getVariableProvider(int refItemIndex);

}
