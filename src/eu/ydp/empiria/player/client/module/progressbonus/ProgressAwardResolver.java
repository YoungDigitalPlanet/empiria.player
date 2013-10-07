package eu.ydp.empiria.player.client.module.progressbonus;

import java.util.List;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.ProgressAssetConfig;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.ProgressAward;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.ProgressConfig;
import eu.ydp.empiria.player.client.module.model.image.ShowImageDTO;
import eu.ydp.empiria.player.client.resources.EmpiriaPaths;

public class ProgressAwardResolver {

	private static final String TEMPLATE_PLACEHOLDER = "%";

	@Inject
	private ProgressAssetBuilder assetBuilder;
	@Inject
	private EmpiriaPaths empiriaPaths;
	private final Function<ProgressAssetConfig, ShowImageDTO> assetConfigToDTOConverter = new Function<ProgressAssetConfig, ShowImageDTO>() {

		@Override
		public ShowImageDTO apply(ProgressAssetConfig assetConfig) {
			String oldPath = assetConfig.getPath();
			String resolvedPath = empiriaPaths.getCommonsFilePath(oldPath);
			return new ShowImageDTO(resolvedPath, assetConfig.getSize());
		}
	};

	public ProgressAsset createProgressAsset(ProgressAward progressAward) {
		List<ProgressConfig> progresses = progressAward.getProgresses();
		for (ProgressConfig progress : progresses) {
			parseProgressConfig(progress);
		}

		return assetBuilder.build();
	}

	private void parseProgressConfig(ProgressConfig progress) {
		int from = progress.getFrom();
		List<ProgressAssetConfig> assetsConfigs = progress.getAssets();
		List<ShowImageDTO> dtos = createImageDTOs(assetsConfigs);
		assetBuilder.add(from, dtos);
	}

	private List<ShowImageDTO> createImageDTOs(List<ProgressAssetConfig> assetsConfigs) {
		List<ProgressAssetConfig> resolvedConfigs = Lists.newArrayList();
		for (ProgressAssetConfig assetConfig : assetsConfigs) {
			if (isTemplatedAssetConfig(assetConfig)) {
				List<ProgressAssetConfig> resolveTemplatedAssetConfig = resolveTemplatedAssetConfig(assetConfig);
				resolvedConfigs.addAll(resolveTemplatedAssetConfig);
			} else {
				resolvedConfigs.add(assetConfig);
			}
		}
		return Lists.transform(resolvedConfigs, assetConfigToDTOConverter);
	}

	private boolean isTemplatedAssetConfig(ProgressAssetConfig assetConfig) {
		return assetConfig.getPath().contains(TEMPLATE_PLACEHOLDER);
	}

	private List<ProgressAssetConfig> resolveTemplatedAssetConfig(ProgressAssetConfig assetConfig) {
		List<ProgressAssetConfig> resolvedConfigs = Lists.newArrayList();
		for (int i = 1; i <= assetConfig.getCount(); i++) {
			final String assetIndex = String.valueOf(i);
			ProgressAssetConfig configFromTemplate = createAssetConfigFromTemplate(assetConfig, assetIndex);
			resolvedConfigs.add(configFromTemplate);
		}
		return resolvedConfigs;
	}

	private ProgressAssetConfig createAssetConfigFromTemplate(ProgressAssetConfig assetConfig, String assetIndex) {
		ProgressAssetConfig resolvedConfig = new ProgressAssetConfig();
		resolvedConfig.setSize(assetConfig.getSize());
		String oldPath = assetConfig.getPath();
		String newPath = oldPath.replace(TEMPLATE_PLACEHOLDER, assetIndex);
		resolvedConfig.setPath(newPath);
		return resolvedConfig;
	}
}
