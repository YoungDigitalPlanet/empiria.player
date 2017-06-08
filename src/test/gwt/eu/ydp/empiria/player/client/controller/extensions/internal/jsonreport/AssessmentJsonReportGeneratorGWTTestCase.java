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

package eu.ydp.empiria.player.client.controller.extensions.internal.jsonreport;

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.controller.extensions.internal.jsonreport.mock.*;
import eu.ydp.empiria.player.client.controller.report.*;
import eu.ydp.empiria.player.client.controller.session.datasockets.AssessmentSessionDataSocket;
import eu.ydp.empiria.player.client.controller.session.datasockets.ItemSessionDataSocket;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;

import java.util.List;

public class AssessmentJsonReportGeneratorGWTTestCase extends EmpiriaPlayerGWTTestCase {

    private final AssessmentReportFactoryMock factory = new AssessmentReportFactoryMock();
    private final SessionDataSupplierMock sessionSupplier = factory.getSessionSupplier();
    private final DataSourceDataSupplierMock dataSupplier = factory.getDataSupplier();

    public void testGeneratingJsonReport() {
        AssessmentReportProvider reportProvider = new AssessmentReportProvider(dataSupplier, sessionSupplier, factory);

        dataSupplier.setAssessmentTitle("Lesson 1");
        dataSupplier.setItemTitles(Lists.newArrayList("Page 1", "Page 2"));
        sessionSupplier.setAssessmentSessionDataSocket(getAssessmentSessionDataSocket());
        sessionSupplier.setItemSessionDataSocketList(getItemSessionDataSocketList());

        AssessmentJsonReportGenerator generator = new AssessmentJsonReportGenerator(dataSupplier, sessionSupplier, reportProvider);
        AssessmentJsonReport report = generator.generate();

        String expectedReport = "{\"title\":\"Lesson 1\", " + "\"result\":{\"done\":1, \"todo\":4, \"errors\":2, \"result\":25}, "
                + "\"hints\":{\"checks\":10, \"mistakes\":11, \"reset\":12, \"showAnswers\":13}, " + "\"items\":[" + "{\"title\":\"Page 1\", \"index\":0, "
                + "\"hints\":{\"checks\":111, \"mistakes\":112, \"reset\":113, \"showAnswers\":114}, "
                + "\"result\":{\"done\":102, \"todo\":101, \"errors\":103, \"result\":100}}," + "{\"title\":\"Page 2\", \"index\":1, "
                + "\"hints\":{\"checks\":115, \"mistakes\":116, \"reset\":117, \"showAnswers\":118}, "
                + "\"result\":{\"done\":105, \"todo\":104, \"errors\":106, \"result\":100}}]}";

        assertEquals(expectedReport, report.getJSONString());
    }

    private List<ItemSessionDataSocket> getItemSessionDataSocketList() {
        VariableProviderSocketMock variableProvider0 = new VariableProviderSocketMock();
        VariableProviderSocketMock variableProvider1 = new VariableProviderSocketMock();

        variableProvider0.setResultValues(101, 102, 103);
        variableProvider0.setHintValues(111, 112, 113, 114);
        variableProvider1.setResultValues(104, 105, 106);
        variableProvider1.setHintValues(115, 116, 117, 118);

        return Lists.newArrayList(getItemSessionDataSocket(variableProvider0), getItemSessionDataSocket(variableProvider1));
    }

    private VariableProviderSocket getAssessmentVariableProvider() {
        VariableProviderSocketMock variableProvider = new VariableProviderSocketMock();
        variableProvider.setResultValues(4, 1, 2);
        variableProvider.setHintValues(10, 11, 12, 13);
        return variableProvider;
    }

    private AssessmentSessionDataSocket getAssessmentSessionDataSocket() {
        AssessmentSessionDataSocketMock assessmentSessionDataSocket = new AssessmentSessionDataSocketMock();
        assessmentSessionDataSocket.setVariableProvider(getAssessmentVariableProvider());
        return assessmentSessionDataSocket;
    }

    private ItemSessionDataSocket getItemSessionDataSocket(VariableProviderSocket variableProvider) {
        ItemSessionDataSocketMock itemSessionDataSocket = new ItemSessionDataSocketMock();
        itemSessionDataSocket.setVariableProvider(variableProvider);
        return itemSessionDataSocket;
    }
}
