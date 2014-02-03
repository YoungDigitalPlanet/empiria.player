package eu.ydp.empiria.player.client.module.progressbonus;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.variables.processor.OutcomeAccessor;

public class ProgressCalculator {

	@Inject
	private OutcomeAccessor outcomeAccessor;

	public int getProgress() {
		return outcomeAccessor.getAssessmentResult();
	}
}
