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

package eu.ydp.empiria.player.client.module.info.progress;

import com.google.common.base.Strings;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.gin.factory.InfoModuleFactory;
import eu.ydp.gwtutil.client.StringUtils;

public class InfoModuleProgressStyleName {
    private static final String EMPIRIA_INFO_ITEM_RESULT = "-empiria-info-item-result-";
    private static final String EMPIRIA_INFO_TEST_RESULT = "-empiria-info-test-result-";

    private final PercentResultProvider percentResultProvider;
    private final InfoModuleProgressMapping itemProgressMapping;
    private final InfoModuleProgressMapping testProgressMapping;

    @Inject
    public InfoModuleProgressStyleName(PercentResultProvider percentResultProvider, InfoModuleFactory infoModuleFactory) {
        this.percentResultProvider = percentResultProvider;

        InfoModuleCssProgressParser itemCssProgressParser = infoModuleFactory.getInfoModuleCssProgressParser(EMPIRIA_INFO_ITEM_RESULT);
        InfoModuleCssProgressParser testCssProgressParser = infoModuleFactory.getInfoModuleCssProgressParser(EMPIRIA_INFO_TEST_RESULT);

        itemProgressMapping = infoModuleFactory.getInfoModuleProgressMapping(itemCssProgressParser);
        testProgressMapping = infoModuleFactory.getInfoModuleProgressMapping(testCssProgressParser);
    }

    public String getCurrentStyleName(int refItemIndex) {
        int itemResult = percentResultProvider.getItemResult(refItemIndex);
        String itemStyleName = itemProgressMapping.getStyleNameForProgress(itemResult);
        if (!Strings.isNullOrEmpty(itemStyleName)) {
            return itemStyleName;
        }

        int testResult = percentResultProvider.getTestResult();
        String testStyleName = testProgressMapping.getStyleNameForProgress(testResult);
        if (!Strings.isNullOrEmpty(testStyleName)) {
            return testStyleName;
        }

        return StringUtils.EMPTY_STRING;
    }
}
