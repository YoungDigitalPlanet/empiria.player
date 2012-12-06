package eu.ydp.empiria.player.client.controller.feedback;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import eu.ydp.empiria.player.client.AbstractTestBase;
import eu.ydp.empiria.player.client.controller.feedback.processor.FeedbackActionProcessor;
import eu.ydp.empiria.player.client.controller.feedback.processor.SoundActionProcessor;
import eu.ydp.empiria.player.client.module.HasChildren;
import eu.ydp.empiria.player.client.module.IModule;
import eu.ydp.empiria.player.client.module.TextActionProcessor;

public class ModuleFeedbackProcessorJUnitTest extends AbstractTestBase{
	
	private ModuleFeedbackProcessor feedbackProcessor;
	
	@Before
	public void init(){
		feedbackProcessor = injector.getInstance(ModuleFeedbackProcessor.class);
	}
	
	@Test
	public void shouldReturnOnlySoundActionProcessor(){
		List<FeedbackActionProcessor> processors = feedbackProcessor.getFeedbackProcessors(null);
		
		assertThat(processors.size(), is(equalTo(1)));
		assertThat(processors.get(0), is(instanceOf(SoundActionProcessor.class)));
	}
	
	@Test
	public void shouldFindModuleFeedbackProcessor(){
		HasChildren parentModule = mock(HasChildren.class);
		IModule module = mock(IModule.class);
		TextActionProcessor processorModule = mock(TextActionProcessor.class);
		
		when(module.getParentModule()).thenReturn(parentModule);
		when(parentModule.getChildren()).thenReturn(Lists.newArrayList(
														mock(IModule.class),
														processorModule, 
														mock(IModule.class),
														module));
		
		List<FeedbackActionProcessor> processorModules = feedbackProcessor.getProcessorModules(module);
		
		assertThat(processorModules.size(), is(equalTo(1)));
		assertThat(processorModules.get(0), is((FeedbackActionProcessor)processorModule));
	}
	
	@Test
	public void shouldReturnEmptyProcessorListWhen_thereIsNoFeedbackProcessor(){
		HasChildren parentModule = mock(HasChildren.class);
		IModule module = mock(IModule.class);
		
		when(module.getParentModule()).thenReturn(parentModule);
		when(parentModule.getChildren()).thenReturn(Lists.newArrayList(
														mock(IModule.class),
														mock(IModule.class),
														module));
		
		List<FeedbackActionProcessor> processorModules = feedbackProcessor.getProcessorModules(module);
		
		assertThat(processorModules, is(notNullValue()));
		assertThat(processorModules.size(), is(equalTo(0)));
	}
	
	@Test
	public void shouldCreateNewInstanceOfActionCollector(){
		IModule module = mock(IModule.class);
		feedbackProcessor.process(module, null);
		
		FeedbackActionCollector collector = feedbackProcessor.feedbackActionCollector;
		feedbackProcessor.process(module, null);
		
		assertThat(feedbackProcessor.feedbackActionCollector, is(not(collector)));
	}
	
}
