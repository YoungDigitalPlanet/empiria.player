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
import eu.ydp.empiria.player.client.controller.feedback.OutcomeCreator;
import eu.ydp.empiria.player.client.controller.session.datasockets.ItemSessionDataSocket;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import org.junit.Test;

import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName.TODO;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemReportProviderJUnitTest extends AbstractTestBase {

    private static final String PAGE2_TITLE = "Page 2";

    private static final String PAGE1_TITLE = "Page 1";

    @Test
    public void shouldReturnAssessmentReport() {
        AssessmentReportFactory factory = injector.getInstance(AssessmentReportFactory.class);
        DataSourceDataSupplier dataSupplier = getDataSupplier();
        SessionDataSupplier sessionSupplier = getSessionSupplier();

        String[] titles = new String[]{PAGE1_TITLE, PAGE2_TITLE};
        int[] todo = new int[]{4, 3};

        for (int i = 0; i < titles.length; i++) {
            ItemReportProvider provider = new ItemReportProvider(i, dataSupplier, sessionSupplier, factory);
            ResultInfo result = provider.getResult();

            assertThat(provider.getTitle(), is(equalTo(titles[i])));
            assertThat(result, is(notNullValue()));
            assertThat(provider.getHints(), is(notNullValue()));
            assertThat(provider.getIndex(), is(equalTo(i)));

            assertThat(result.getTodo(), is(equalTo(todo[i])));
        }
    }

    private SessionDataSupplier getSessionSupplier() {
        OutcomeCreator creator = new OutcomeCreator();
        SessionDataSupplier sessionSupplier = mock(SessionDataSupplier.class);
        ItemSessionDataSocket itemSessionDataSocket0 = mock(ItemSessionDataSocket.class);
        ItemSessionDataSocket itemSessionDataSocket1 = mock(ItemSessionDataSocket.class);
        VariableProviderSocket variableProviderSocket0 = mock(VariableProviderSocket.class);
        VariableProviderSocket variableProviderSocket1 = mock(VariableProviderSocket.class);

        when(sessionSupplier.getItemSessionDataSocket(0)).thenReturn(itemSessionDataSocket0);
        when(sessionSupplier.getItemSessionDataSocket(1)).thenReturn(itemSessionDataSocket1);

        when(itemSessionDataSocket0.getVariableProviderSocket()).thenReturn(variableProviderSocket0);
        when(itemSessionDataSocket1.getVariableProviderSocket()).thenReturn(variableProviderSocket1);

        when(variableProviderSocket0.getVariableValue(TODO.toString())).thenReturn(creator.createTodoOutcome(4));
        when(variableProviderSocket1.getVariableValue(TODO.toString())).thenReturn(creator.createTodoOutcome(3));

        return sessionSupplier;
    }

    private DataSourceDataSupplier getDataSupplier() {
        DataSourceDataSupplier dataSupplier = mock(DataSourceDataSupplier.class);

        when(dataSupplier.getItemTitle(0)).thenReturn(PAGE1_TITLE);
        when(dataSupplier.getItemTitle(1)).thenReturn(PAGE2_TITLE);

        return dataSupplier;
    }

}
