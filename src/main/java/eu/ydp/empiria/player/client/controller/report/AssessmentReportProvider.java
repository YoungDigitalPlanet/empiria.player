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

package eu.ydp.empiria.player.client.controller.report;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;

import java.util.List;

public class AssessmentReportProvider extends AbstractReportProvider {

    @Inject
    public AssessmentReportProvider(DataSourceDataSupplier dataSupplier, SessionDataSupplier sessionSupplier,
                                    AssessmentReportFactory factory) {
        super(dataSupplier, sessionSupplier, factory);
    }

    @Override
    public String getTitle() {
        return dataSupplier.getAssessmentTitle();
    }

    public List<ItemReportProvider> getItems() {
        List<ItemReportProvider> items = Lists.newArrayList();

        for (int i = 0; i < getItemsCount(); i++) {
            ItemReportProvider item = factory.getItemReportProvider(i);
            items.add(item);
        }

        return items;
    }

    public int getItemsCount() {
        return dataSupplier.getItemsCount();
    }

    @Override
    protected VariableProviderSocket getVariableProvider() {
        return sessionSupplier.getAssessmentSessionDataSocket().getVariableProviderSocket();
    }

}
