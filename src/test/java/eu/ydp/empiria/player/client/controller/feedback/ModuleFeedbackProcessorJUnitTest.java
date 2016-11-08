package eu.ydp.empiria.player.client.controller.feedback;

import com.google.common.collect.Lists;
import eu.ydp.empiria.player.client.AbstractTestBase;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.controller.feedback.processor.FeedbackActionProcessor;
import eu.ydp.empiria.player.client.controller.feedback.processor.SoundActionProcessor;
import eu.ydp.empiria.player.client.controller.variables.storage.item.ItemOutcomeStorageImpl;
import eu.ydp.empiria.player.client.gin.factory.FeedbackModuleFactory;
import eu.ydp.empiria.player.client.module.core.base.HasChildren;
import eu.ydp.empiria.player.client.module.core.base.IModule;
import eu.ydp.empiria.player.client.module.core.base.IUniqueModule;
import eu.ydp.empiria.player.client.controller.feedback.processor.TextActionProcessor;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ModuleFeedbackProcessorJUnitTest extends AbstractTestBase {

    private ModuleFeedbackProcessor feedbackProcessor;
    private FeedbackRegistry feedbackRegistry;

    @Before
    public void init() {
        InlineBodyGeneratorSocket inlineBodyGeneratorSocket = mock(InlineBodyGeneratorSocket.class);
        FeedbackModuleFactory feedbackModuleFactory = injector.getInstance(FeedbackModuleFactory.class);
        feedbackProcessor = feedbackModuleFactory.getModuleFeedbackProcessor(inlineBodyGeneratorSocket);
        feedbackRegistry = injector.getInstance(FeedbackRegistry.class);
    }

    @Test
    public void shouldReturnOnlySoundActionProcessor() {
        List<FeedbackActionProcessor> processors = feedbackProcessor.getFeedbackProcessors(null);

        assertThat(processors.size(), is(equalTo(1)));
        assertThat(processors.get(0), is(instanceOf(SoundActionProcessor.class)));
    }

    @Test
    public void shouldFindModuleFeedbackProcessor() {
        HasChildren parentModule = mock(HasChildren.class);
        IModule module = mock(IModule.class);
        TextActionProcessor processorModule = mock(TextActionProcessor.class);

        when(module.getParentModule()).thenReturn(parentModule);
        when(parentModule.getChildren()).thenReturn(Lists.newArrayList(mock(IModule.class), processorModule, mock(IModule.class), module));

        List<FeedbackActionProcessor> processorModules = feedbackProcessor.getProcessorModules(module);

        assertThat(processorModules.size(), is(equalTo(1)));
        assertThat(processorModules.get(0), is((FeedbackActionProcessor) processorModule));
    }

    @Test
    public void shouldReturnEmptyProcessorListWhen_thereIsNoFeedbackProcessor() {
        HasChildren parentModule = mock(HasChildren.class);
        IModule module = mock(IModule.class);

        when(module.getParentModule()).thenReturn(parentModule);
        when(parentModule.getChildren()).thenReturn(Lists.newArrayList(mock(IModule.class), mock(IModule.class), module));

        List<FeedbackActionProcessor> processorModules = feedbackProcessor.getProcessorModules(module);

        assertThat(processorModules, is(notNullValue()));
        assertThat(processorModules.size(), is(equalTo(0)));
    }

    @Test
    public void shouldCreateNewInstanceOfActionCollector() {
        when(feedbackRegistry.hasFeedbacks()).thenReturn(true);

        IUniqueModule module = mock(IUniqueModule.class);
        feedbackProcessor.processFeedbacks(null, module);

        FeedbackActionCollector collector = feedbackProcessor.feedbackActionCollector;
        feedbackProcessor.processFeedbacks(null, module);

        assertThat(feedbackProcessor.feedbackActionCollector, is(not(collector)));
    }

}
