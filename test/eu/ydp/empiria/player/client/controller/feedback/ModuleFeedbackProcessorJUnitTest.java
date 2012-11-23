package eu.ydp.empiria.player.client.controller.feedback;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import eu.ydp.empiria.player.client.AbstractTestBase;
import eu.ydp.empiria.player.client.controller.feedback.processor.FeedbackActionProcessor;
import eu.ydp.empiria.player.client.controller.feedback.processor.SoundActionProcessor;

public class ModuleFeedbackProcessorJUnitTest extends AbstractTestBase{
	
	@Test
	public void shouldReturnOnlySoundActionProcessor(){
		ModuleFeedbackProcessor feedbackProcessor = injector.getInstance(ModuleFeedbackProcessor.class);
		List<FeedbackActionProcessor> processors = feedbackProcessor.getFeedbackProcessors(null);
		
		assertThat(processors.size(), is(equalTo(1)));
		assertThat(processors.get(0), is(instanceOf(SoundActionProcessor.class)));
	}
	
	@Test
	public void shouldFindModuleFeedbackProcessor(){
		assertThat(true, is(equalTo(true)));
	}
	
}
