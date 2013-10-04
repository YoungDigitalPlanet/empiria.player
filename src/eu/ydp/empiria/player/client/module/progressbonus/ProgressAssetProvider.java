package eu.ydp.empiria.player.client.module.progressbonus;

import java.util.List;

import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.ProgressAward;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.ProgressBonusConfig;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;
import eu.ydp.gwtutil.client.util.RandomWrapper;

public class ProgressAssetProvider {

	@Inject
	@ModuleScoped
	private ProgressBonusConfig progressBonusConfig;
	@Inject
	private RandomWrapper random;
	@Inject
	private ProgressAwardResolver awardResolver;

	public ProgressAsset createRandom() {
		List<ProgressAward> awards = progressBonusConfig.getAwards();
		int randomIndex = random.nextInt(awards.size() - 1);
		ProgressAward progressAward = awards.get(randomIndex);

		ProgressAsset progressAsset = createProgressAsset(progressAward);
		return progressAsset;
	}

	public ProgressAsset createFrom(int index) {
		List<ProgressAward> awards = progressBonusConfig.getAwards();
		ProgressAward progressAward = awards.get(index);
		ProgressAsset progressAsset = createProgressAsset(progressAward);
		return progressAsset;
	}

	private ProgressAsset createProgressAsset(ProgressAward progressAward) {
		return awardResolver.createProgressAsset(progressAward);
	}
}
