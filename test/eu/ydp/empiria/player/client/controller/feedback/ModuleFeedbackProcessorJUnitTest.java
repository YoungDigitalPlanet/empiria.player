package eu.ydp.empiria.player.client.controller.feedback;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Test;

import eu.ydp.empiria.player.client.controller.feedback.processor.FeedbackActionProcessor;
import eu.ydp.empiria.player.client.controller.feedback.processor.SoundActionProcessor;

public class ModuleFeedbackProcessorJUnitTest {
	
	@Test
	public void shouldReturnOnlySoundActionProcessor(){
		ModuleFeedbackProcessor feedbackProcessor = new ModuleFeedbackProcessor();
		List<FeedbackActionProcessor> processors = feedbackProcessor.getFeedbackProcessors(null);
		
		assertThat(processors.size(), is(equalTo(1)));
		assertThat(processors.get(0), is(instanceOf(SoundActionProcessor.class)));
	}
	
}
