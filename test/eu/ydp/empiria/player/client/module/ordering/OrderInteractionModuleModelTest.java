package eu.ydp.empiria.player.client.module.ordering;

import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eu.ydp.empiria.player.client.controller.variables.objects.response.Response;

@RunWith(MockitoJUnitRunner.class)
public class OrderInteractionModuleModelTest {
	
	@InjectMocks
	private OrderInteractionModuleModel testObj;
	
	@Mock
	private Response response;
	

	@Test
	public void testReset() {
		// when
		testObj.reset();
		
		// then
		verify(response).reset();
	}

}
