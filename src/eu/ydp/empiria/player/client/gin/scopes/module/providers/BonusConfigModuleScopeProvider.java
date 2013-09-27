package eu.ydp.empiria.player.client.gin.scopes.module.providers;

import com.google.inject.Inject;
import com.google.inject.Provider;

import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusConfig;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonus.BonusService;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleCreationContext;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScopeStack;

public class BonusConfigModuleScopeProvider implements Provider<BonusConfig> {

	private static final String BONUS_ID_ATTR = "bonusId";

	@Inject
	private ModuleScopeStack moduleScopeStack;

	@Inject
	private BonusService bonusService;

	@Override
	public BonusConfig get() {
		ModuleCreationContext context = moduleScopeStack.getCurrentTopContext();
		String bonusId = context.getXmlElement().getAttribute(BONUS_ID_ATTR);
		return bonusService.getBonusConfig(bonusId);
	}

}
