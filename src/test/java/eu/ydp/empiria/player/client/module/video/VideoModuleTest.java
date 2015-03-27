package eu.ydp.empiria.player.client.module.video;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.module.video.presenter.VideoPresenter;

@RunWith(MockitoJUnitRunner.class)
public class VideoModuleTest {

	@InjectMocks
	private VideoModule module;
	@Mock
	private VideoPresenter presenter;

	@Test
	public void shouldStartPresenterOnInit() {
		// given
		Element element = mock(Element.class);

		// when
		module.initModule(element);

		// then
		verify(presenter).start();
	}
}
