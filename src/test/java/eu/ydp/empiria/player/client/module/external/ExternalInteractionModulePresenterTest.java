package eu.ydp.empiria.player.client.module.external;

import static org.mockito.Mockito.*;

import eu.ydp.empiria.player.client.module.external.structure.ExternalInteractionModuleBean;
import eu.ydp.empiria.player.client.module.external.view.ExternalInteractionView;
import eu.ydp.empiria.player.client.resources.EmpiriaPaths;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ExternalInteractionModulePresenterTest {

	@InjectMocks
	private ExternalInteractionModulePresenter testObj;
	@Mock
	private ExternalInteractionModuleBean externalInteractionModuleBean;
	@Mock
	private ExternalInteractionView externalInteractionView;
	@Mock
	private EmpiriaPaths empiriaPaths;

	@Test
	public void shouldInitializeView() {
		// given
		final String filename = "external.html";
		final String expectedURL = "media/external.html";
		when(externalInteractionModuleBean.getSrc()).thenReturn(filename);
		when(empiriaPaths.getMediaFilePath(filename)).thenReturn(expectedURL);

		testObj.setBean(externalInteractionModuleBean);

		// when
		testObj.bindView();

		// then
		verify(externalInteractionView).setUrl(expectedURL);
	}
}
