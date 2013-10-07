package eu.ydp.empiria.player.client.gin.scopes.module.providers;

import com.google.inject.Provider;

import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.ProgressBonusConfig;

public class ProgressBonusConfigModuleScopeProvider implements Provider<ProgressBonusConfig> {

	@Override
	public ProgressBonusConfig get() {
		return new ProgressBonusConfig();
	}

}
