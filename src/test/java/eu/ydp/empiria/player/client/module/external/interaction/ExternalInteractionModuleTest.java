package eu.ydp.empiria.player.client.module.external.interaction;

import eu.ydp.empiria.player.client.module.external.common.ExternalPaths;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ExternalInteractionModuleTest {

	@InjectMocks
	private ExternalInteractionModule testObj;
	@Mock
	private ExternalInteractionResponseModel externalInteractionResponseModel;
	@Mock
	private ExternalPaths externalPaths;

	@Test
	public void shouldRegisterAsExternalFolderNameProvider() {
		// when
		testObj.initalizeModule();

		// then
		verify(externalPaths).setExternalFolderNameProvider(testObj);
	}

	@Test
	public void shouldRegisterAsResponseModelChange() {
		// when
		testObj.initalizeModule();

		// then
		verify(externalInteractionResponseModel).setResponseModelChange(testObj);
	}
}
