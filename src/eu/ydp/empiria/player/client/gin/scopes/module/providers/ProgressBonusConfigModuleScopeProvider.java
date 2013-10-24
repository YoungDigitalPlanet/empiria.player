package eu.ydp.empiria.player.client.gin.scopes.module.providers;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.ProgressBonusConfig;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.ProgressBonusService;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleCreationContext;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScopeStack;

public class ProgressBonusConfigModuleScopeProvider implements Provider<ProgressBonusConfig> {

	private static final String PROGRESSBONUS_ID_ATTR = "progressBonusId";

	@Inject
	private ModuleScopeStack moduleScopeStack;
	
	@Inject
	private ProgressBonusService service;
	
	@Override
	public ProgressBonusConfig get() {
		ModuleCreationContext context = moduleScopeStack.getCurrentTopContext();
		String progressBonusId = context.getXmlElement().getAttribute(PROGRESSBONUS_ID_ATTR);
		return service.getProgressBonusConfig(progressBonusId);
	}

}
