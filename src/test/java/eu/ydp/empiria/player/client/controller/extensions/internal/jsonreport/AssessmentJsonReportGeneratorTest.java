package eu.ydp.empiria.player.client.controller.extensions.internal.jsonreport;

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.AbstractEmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.PlayerGinjectorFactory;
import eu.ydp.empiria.player.client.controller.extensions.internal.jsonreport.mock.*;
import eu.ydp.empiria.player.client.controller.report.AssessmentReportFactory;
import eu.ydp.empiria.player.client.controller.session.datasockets.AssessmentSessionDataSocket;
import eu.ydp.empiria.player.client.controller.session.datasockets.ItemSessionDataSocket;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;

import java.util.List;

public class AssessmentJsonReportGeneratorTest extends AbstractEmpiriaPlayerGWTTestCase {

    public void testGeneratingJsonReport() {
        AssessmentReportFactory factory = PlayerGinjectorFactory.getNewPlayerGinjectorForGWTTestCase().getAssessmentReportFactory();
        SessionDataSupplierMock sessionSupplier = new SessionDataSupplierMock();
        DataSourceDataSupplierMock dataSupplier = new DataSourceDataSupplierMock();

        dataSupplier.setAssessmentTitle("Lesson 1");
        dataSupplier.setItemTitles(Lists.newArrayList("Page 1", "Page 2"));
        sessionSupplier.setAssessmentSessionDataSocket(getAssessmentSessionDataSocket());
        sessionSupplier.setItemSessionDataSocketList(getItemSessionDataSocketList());

        AssessmentJsonReportGenerator generator = factory.getAssessmentJsonReportGenerator(dataSupplier, sessionSupplier);
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
