package eu.ydp.empiria.player.client.module.slideshow.presenter;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.module.slideshow.structure.SlideshowBean;
import eu.ydp.empiria.player.client.module.slideshow.view.player.SlideshowPlayerView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class SlideshowPlayerPresenterTest {

	@InjectMocks
	private SlideshowPlayerPresenter testObj;
	@Mock
	private SlideshowPlayerView view;

	@Test
	public void shouldInit() {
		// given
		SlideshowBean slideshow = mock(SlideshowBean.class);
		String title = "title";
		when(slideshow.getTitle()).thenReturn(title);

		// when
		testObj.init(slideshow);

		// then
		verify(view).setTitle(title);
	}

	@Test
	public void shouldDelegatePager() {
		// given
		Widget widget = mock(Widget.class);
		when(view.asWidget()).thenReturn(widget);

		// when
		testObj.setPager(widget);

		// then
		verify(view).addPager(widget);
	}

	@Test
	public void shouldReturnViewAsWidget() {
		// given
		Widget widget = mock(Widget.class);
		when(view.asWidget()).thenReturn(widget);

		// when
		Widget result = testObj.getView();

		// then
		assertThat(result).isEqualTo(widget);
	}
}
