package eu.ydp.empiria.player.client.module.progressbonus;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import eu.ydp.empiria.player.client.controller.variables.processor.OutcomeAccessor;

@Singleton
public class ProgressCalculator {

    @Inject
    private OutcomeAccessor outcomeAccessor;

    public int getProgress() {
        return outcomeAccessor.getAssessmentResult();
    }
}
