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

import eu.ydp.empiria.player.client.AbstractTestBase;
import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.session.datasockets.AssessmentSessionDataSocket;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AssessmentReportProviderJUnitTest extends AbstractTestBase {

    @Test
    public void shouldReturnAssessmentReport() {
        AssessmentReportFactory factory = injector.getInstance(AssessmentReportFactory.class);
        DataSourceDataSupplier dataSupplier = getDataSupplier();
        SessionDataSupplier sessionSupplier = getSessionSupplier();
        AssessmentReportProvider provider = new AssessmentReportProvider(dataSupplier, sessionSupplier, factory);

        assertThat(provider.getTitle(), is(equalTo("Lesson 1")));
        assertThat(provider.getResult(), is(notNullValue()));
        assertThat(provider.getHints(), is(notNullValue()));
        assertThat(provider.getItemsCount(), is(equalTo(2)));
        assertThat(provider.getItems(), is(notNullValue()));
        assertThat(provider.getItems().size(), is(equalTo(2)));

        List<ItemReportProvider> items = provider.getItems();

        for (int i = 0; i < items.size(); i++) {
            ItemReportProvider itemReport = items.get(i);
            assertThat(itemReport.getIndex(), is(equalTo(i)));
        }
    }

    private SessionDataSupplier getSessionSupplier() {
        SessionDataSupplier sessionSupplier = mock(SessionDataSupplier.class);
        AssessmentSessionDataSocket assessmentSessionDataSocket = mock(AssessmentSessionDataSocket.class);
        VariableProviderSocket variableProviderSocket = mock(VariableProviderSocket.class);

        when(assessmentSessionDataSocket.getVariableProviderSocket()).thenReturn(variableProviderSocket);
        when(sessionSupplier.getAssessmentSessionDataSocket()).thenReturn(assessmentSessionDataSocket);

        return sessionSupplier;
    }

    private DataSourceDataSupplier getDataSupplier() {
        DataSourceDataSupplier dataSupplier = mock(DataSourceDataSupplier.class);

        when(dataSupplier.getAssessmentTitle()).thenReturn("Lesson 1");
        when(dataSupplier.getItemsCount()).thenReturn(2);

        return dataSupplier;
    }

}
