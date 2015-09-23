package eu.ydp.empiria.player.client.module.info;

import com.google.common.collect.Lists;
import com.google.inject.Binder;
import com.google.inject.Module;
import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.GuiceModuleConfiguration;
import eu.ydp.empiria.player.client.controller.data.DataSourceDataSupplier;
import eu.ydp.empiria.player.client.controller.feedback.OutcomeCreator;
import eu.ydp.empiria.player.client.controller.session.datasockets.ItemSessionDataSocket;
import eu.ydp.empiria.player.client.controller.session.datasupplier.SessionDataSupplier;
import eu.ydp.empiria.player.client.controller.variables.VariableProviderSocket;
import eu.ydp.empiria.player.client.controller.variables.processor.item.FlowActivityVariablesProcessor;
import eu.ydp.empiria.player.client.module.info.InfoModuleContentTokenizer.Token;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static eu.ydp.empiria.player.client.controller.variables.processor.results.model.VariableName.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VariableInterpreterJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {

    private class CustomGinModule implements Module {
        @Override
        public void configure(Binder binder) {
            binder.bind(DataSourceDataSupplier.class).toInstance(sourceSupplier);
            binder.bind(SessionDataSupplier.class).toInstance(sessionSupplier);
        }
    }

    private VariableInterpreter interpreter;

    @Mock
    private DataSourceDataSupplier sourceSupplier;
    @Mock
    private SessionDataSupplier sessionSupplier;
    private final InfoModuleContentTokenizer contentTokenizer = new InfoModuleContentTokenizer();

    @Before
    public void initialize() {
        setUpAndOverrideMainModule(new GuiceModuleConfiguration(), new CustomGinModule());
        interpreter = injector.getInstance(VariableInterpreter.class);
    }

    @Test
    public void shouldReturnCorrectContentString() {
        ItemSessionDataSocket itemSessionDataSocketPage0 = createItemSessionDataSocketForFirstPage();
        ItemSessionDataSocket itemSessionDataSocketPage1 = createItemSessionDataSocketForSecondPage();

        when(sessionSupplier.getItemSessionDataSocket(0)).thenReturn(itemSessionDataSocketPage0);
        when(sessionSupplier.getItemSessionDataSocket(1)).thenReturn(itemSessionDataSocketPage1);

        when(sourceSupplier.getAssessmentTitle()).thenReturn("Lesson 1");
        when(sourceSupplier.getItemTitle(0)).thenReturn("Page 1");
        when(sourceSupplier.getItemTitle(1)).thenReturn("Page 2");
        when(sourceSupplier.getItemsCount()).thenReturn(1);

        List<ContentInfo> infos = Lists.newArrayList(ContentInfo.create("$[item.title]", "Page 1", 0), ContentInfo.create("$[item.title]", "Page 2", 1),
                ContentInfo.create("$[test.title]", "Lesson 1", 0), ContentInfo.create("$[item.index]", "1", 0), ContentInfo.create("$[item.index]", "2", 1),
                ContentInfo.create("$[item.page_num]", "1", 0), ContentInfo.create("$[item.page_count]", "1", 0), ContentInfo.create("$[item.todo]", "3", 0),
                ContentInfo.create("$[item.todo]", "5", 1), ContentInfo.create("$[item.done]", "0", 0), ContentInfo.create("$[item.done]", "4", 1),
                ContentInfo.create("$[item.checks]", "0", 0), ContentInfo.create("$[item.checks]", "6", 1), ContentInfo.create("$[item.mistakes]", "0", 0),
                ContentInfo.create("$[item.mistakes]", "3", 1), ContentInfo.create("$[item.show_answers]", "0", 0),
                ContentInfo.create("$[item.show_answers]", "2", 1), ContentInfo.create("$[item.reset]", "0", 0), ContentInfo.create("$[item.reset]", "10", 1),
                ContentInfo.create("$[item.result]", "0", 0), ContentInfo.create("$[item.result]", "80", 1), ContentInfo.create("$[test.todo]", "0", 0),
                ContentInfo.create("$[test.checks]", "0", 0), ContentInfo.create("$[test.mistakes]", "0", 0),
                ContentInfo.create("$[test.show_answers]", "0", 0), ContentInfo.create("$[test.reset]", "0", 0), ContentInfo.create("$[test.result]", "0", 0),
                ContentInfo.create("$[item.title], $[test.title] result is $[test.result]%", "Page 1, Lesson 1 result is 0%", 0));

        for (ContentInfo info : infos) {
            assertInfo(info);
        }
    }

    private ItemSessionDataSocket createItemSessionDataSocketForFirstPage() {
        OutcomeCreator outcomeCreator = new OutcomeCreator();
        ItemSessionDataSocket itemSessionDataSocket = mock(ItemSessionDataSocket.class);
        VariableProviderSocket itemVariableSocketPage = mock(VariableProviderSocket.class);

        when(itemVariableSocketPage.getVariableValue(TODO.toString())).thenReturn(outcomeCreator.createTodoOutcome(3));
        when(itemSessionDataSocket.getVariableProviderSocket()).thenReturn(itemVariableSocketPage);

        return itemSessionDataSocket;
    }

    private ItemSessionDataSocket createItemSessionDataSocketForSecondPage() {
        OutcomeCreator outcomeCreator = new OutcomeCreator();
        ItemSessionDataSocket itemSessionDataSocket = mock(ItemSessionDataSocket.class);
        VariableProviderSocket itemVariableSocketPage = mock(VariableProviderSocket.class);

        when(itemVariableSocketPage.getVariableValue(TODO.toString())).thenReturn(outcomeCreator.createTodoOutcome(5));
        when(itemVariableSocketPage.getVariableValue(DONE.toString())).thenReturn(outcomeCreator.createDoneOutcome(4));
        when(itemVariableSocketPage.getVariableValue(FlowActivityVariablesProcessor.CHECKS)).thenReturn(outcomeCreator.createChecksOutcome(6));
        when(itemVariableSocketPage.getVariableValue(MISTAKES.toString())).thenReturn(outcomeCreator.createMistakesOutcome(3));
        when(itemVariableSocketPage.getVariableValue(FlowActivityVariablesProcessor.SHOW_ANSWERS)).thenReturn(outcomeCreator.createShowAnswersOutcome(2));
        when(itemVariableSocketPage.getVariableValue(FlowActivityVariablesProcessor.RESET)).thenReturn(outcomeCreator.createResetOutcome(10));
        when(itemSessionDataSocket.getVariableProviderSocket()).thenReturn(itemVariableSocketPage);

        return itemSessionDataSocket;
    }

    private void assertInfo(ContentInfo info) {
        String template = "This is %1$s.";
        String content = String.format(template, info.getContentTag());
        String expectedValue = String.format(template, info.getExpectedValue());
        List<Token> allTokens = contentTokenizer.getAllTokens(content);
        assertThat(info.getContentTag() + " page: " + info.getRefItemIndex(), interpreter.replaceAllTags(allTokens, info.getRefItemIndex()),
                is(equalTo(expectedValue)));
    }

    private static class ContentInfo {

        private final String contentTag;
        private final String expectedValue;
        private final int refItemIndex;

        public static ContentInfo create(String content, String expectedValue, int refItemIndex) {
            return new ContentInfo(content, expectedValue, refItemIndex);
        }

        public ContentInfo(String contentTag, String expectedValue, int refItemIndex) {
            this.contentTag = contentTag;
            this.expectedValue = expectedValue;
            this.refItemIndex = refItemIndex;
        }

        public String getContentTag() {
            return contentTag;
        }

        public int getRefItemIndex() {
            return refItemIndex;
        }

        public String getExpectedValue() {
            return expectedValue;
        }

    }

}
