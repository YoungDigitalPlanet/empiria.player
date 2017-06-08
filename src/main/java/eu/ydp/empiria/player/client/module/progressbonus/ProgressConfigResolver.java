/*
 * Copyright 2017 Young Digital Planet S.A.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package eu.ydp.empiria.player.client.module.progressbonus;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.ProgressAssetConfig;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.ProgressBonusConfig;
import eu.ydp.empiria.player.client.controller.extensions.internal.bonusprogress.ProgressConfig;
import eu.ydp.empiria.player.client.module.model.image.ShowImageDTO;
import eu.ydp.empiria.player.client.resources.EmpiriaPaths;
import eu.ydp.gwtutil.client.gin.scopes.module.ModuleScoped;

import java.util.List;
import java.util.Map;

public class ProgressConfigResolver {

    private static final String TEMPLATE_PLACEHOLDER = "%";

    @Inject
    @ModuleScoped
    private ProgressBonusConfig progressBonusConfig;
    @Inject
    private EmpiriaPaths empiriaPaths;
    private final Map<Integer, List<ShowImageDTO>> builderMap = Maps.newTreeMap();

    private final Function<ProgressAssetConfig, ShowImageDTO> assetConfigToDTOConverter = new Function<ProgressAssetConfig, ShowImageDTO>() {

        @Override
        public ShowImageDTO apply(ProgressAssetConfig assetConfig) {
            String oldPath = assetConfig.getPath();
            String resolvedPath = empiriaPaths.getCommonsFilePath(oldPath);
            return new ShowImageDTO(resolvedPath, assetConfig.getSize());
        }
    };

    public Map<Integer, List<ShowImageDTO>> resolveProgressConfig() {
        List<ProgressConfig> progresses = progressBonusConfig.getProgresses();
        for (ProgressConfig progress : progresses) {
            parseProgressConfig(progress);
        }
        return builderMap;
    }

    private void parseProgressConfig(ProgressConfig progress) {
        int from = progress.getFrom();
        List<ProgressAssetConfig> assetsConfigs = progress.getAssets();
        List<ShowImageDTO> images = createImages(assetsConfigs);
        builderMap.put(from, images);
    }

    private List<ShowImageDTO> createImages(List<ProgressAssetConfig> assetsConfigs) {
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
        String oldPath = assetConfig.getPath();
        String newPath = oldPath.replace(TEMPLATE_PLACEHOLDER, assetIndex);
        ProgressAssetConfig resolvedConfig = new ProgressAssetConfig(newPath, 1, assetConfig.getSize());
        return resolvedConfig;
    }
}
