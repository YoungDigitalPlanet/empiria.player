package eu.ydp.empiria.player.client.module.info.handler;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.module.info.ContentFieldInfo;

public abstract class ProviderAssessmentValueHandlerBase implements FieldValueHandler {

    private VariableProviderSocket assessmentVariableProvider;

    @Inject
    public ProviderAssessmentValueHandlerBase(@Assisted VariableProviderSocket assessmentVariableProvider) {
        this.assessmentVariableProvider = assessmentVariableProvider;
    }

    @Override
    public String getValue(ContentFieldInfo info, int refItemIndex) {
        return countValue(info, assessmentVariableProvider);
    }

    protected abstract String countValue(ContentFieldInfo info, VariableProviderSocket provider);

}
