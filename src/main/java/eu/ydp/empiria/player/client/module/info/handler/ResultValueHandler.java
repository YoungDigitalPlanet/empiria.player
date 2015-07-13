package eu.ydp.empiria.player.client.module.info.handler;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.ResultExtractorsFactory;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.controller.variables.VariableResult;
import eu.ydp.empiria.player.client.module.info.ContentFieldInfo;

public class ResultValueHandler extends ProviderItemValueHandlerBase {

    private ResultExtractorsFactory resultExtractorsFactory;

    @Inject
    public ResultValueHandler(@Assisted SessionDataSupplier sessionDataSupplier,
                              ResultExtractorsFactory resultExtractorsFactory) {
        super(sessionDataSupplier);
        this.resultExtractorsFactory = resultExtractorsFactory;
    }

    @Override
    protected String countValue(ContentFieldInfo info, VariableProviderSocket provider) {
        VariableResult result = resultExtractorsFactory.createVariableResult(provider);
        return String.valueOf(result.getResult());
    }

}
