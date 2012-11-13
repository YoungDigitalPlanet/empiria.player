package eu.ydp.empiria.player.client.controller.feedback;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import org.junit.Test;

import eu.ydp.empiria.player.client.module.IModule;

public class FeedbackActionCollectorJUnitTest {
	
	@Test
	public void shouldAddSourceModule(){
		IModule source = mock(IModule.class);
		FeedbackActionCollector collector = new FeedbackActionCollector(source);		
		
		assertThat(collector.getSource(), is(equalTo(source)));
	}
	
}
