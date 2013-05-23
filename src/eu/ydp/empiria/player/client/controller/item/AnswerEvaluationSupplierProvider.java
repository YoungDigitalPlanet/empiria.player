package eu.ydp.empiria.player.client.controller.item;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.controller.variables.processor.AnswerEvaluationSupplier;
import eu.ydp.empiria.player.client.gin.scopes.page.CurrentPageScopeProvider;
import eu.ydp.empiria.player.client.gin.scopes.page.PageScopedProvider;

public class AnswerEvaluationSupplierProvider extends PageScopedProvider<AnswerEvaluationSupplier> {

	@Inject
	public AnswerEvaluationSupplierProvider(Provider<AnswerEvaluationSupplier> provider, CurrentPageScopeProvider currentScopeProvider) {
		super(provider, currentScopeProvider);
	}

}
