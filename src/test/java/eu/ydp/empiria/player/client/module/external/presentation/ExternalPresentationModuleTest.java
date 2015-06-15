package eu.ydp.empiria.player.client.module.external.presentation;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockitoTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(GwtMockitoTestRunner.class)
public class ExternalPresentationModuleTest {

	@InjectMocks
	private ExternalPresentationModule testObj;
	@Mock
	private Widget viewMock;
	@Mock
	private ExternalPresentationPresenter presenter;

	@Test
	public void shouldReturnViewFromPresenter() {
		// given
		when(presenter.getView()).thenReturn(viewMock);

		// when
		Widget view = testObj.getView();

		// then
		assertThat(view).isEqualTo(viewMock);
	}
}
