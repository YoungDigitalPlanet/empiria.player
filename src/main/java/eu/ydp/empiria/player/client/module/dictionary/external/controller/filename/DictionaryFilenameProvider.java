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

package eu.ydp.empiria.player.client.module.dictionary.external.controller.filename;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.resources.EmpiriaPaths;
import eu.ydp.gwtutil.client.util.NumberFormatWrapper;

public class DictionaryFilenameProvider {

    private static final String RELATIVE_EXPLANATIONS_DIR = "dictionary/explanations/";

    private final NumberFormatWrapper numberFormatWrapper;

    private final EmpiriaPaths empiriaPaths;

    @Inject
    public DictionaryFilenameProvider(EmpiriaPaths empiriaPaths, NumberFormatWrapper numberFormatWrapper) {
        this.empiriaPaths = empiriaPaths;
        this.numberFormatWrapper = numberFormatWrapper;
    }

    public String getFilePathForIndex(int index) {
        final String explanationsDir = extractExplanationsDir();
        int offset = calculateOffset(index);
        return explanationsDir + formatNumber(offset) + ".xml";
    }

    private String extractExplanationsDir() {
        return empiriaPaths.getCommonsPath() + RELATIVE_EXPLANATIONS_DIR;
    }

    private int calculateOffset(int index) {
        return new Integer(index / 50) * 50;
    }

    private String formatNumber(int num) {
        return numberFormatWrapper.formatNumber("00000", num);
    }
}
