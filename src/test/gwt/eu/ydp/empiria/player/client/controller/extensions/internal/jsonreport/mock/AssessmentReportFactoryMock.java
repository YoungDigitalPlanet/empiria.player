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

package eu.ydp.empiria.player.client.controller.extensions.internal.jsonreport.mock;

import eu.ydp.empiria.player.client.controller.report.AssessmentReportFactory;
import eu.ydp.empiria.player.client.controller.report.HintInfo;
import eu.ydp.empiria.player.client.controller.report.ItemReportProvider;
import eu.ydp.empiria.player.client.controller.report.ResultInfo;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;

public class AssessmentReportFactoryMock implements AssessmentReportFactory{

    private final SessionDataSupplierMock sessionSupplier = new SessionDataSupplierMock();

    private final DataSourceDataSupplierMock dataSupplier = new DataSourceDataSupplierMock();

    @Override
    public ResultInfo getResultInfo(VariableProviderSocket variableProvider) {
        return new ResultInfo(variableProvider);
    }

    @Override
    public HintInfo getHintInfo(VariableProviderSocket variableProvider) {
        return new HintInfo(variableProvider);
    }

    @Override
    public ItemReportProvider getItemReportProvider(int index) {
        return new ItemReportProvider(index, dataSupplier, sessionSupplier, this);
    }

    public DataSourceDataSupplierMock getDataSupplier() {
        return dataSupplier;
    }

    public SessionDataSupplierMock getSessionSupplier() {
        return sessionSupplier;
    }
}
