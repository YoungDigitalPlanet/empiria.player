package eu.ydp.empiria.player.client.module.slideshow.presenter;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.google.common.collect.Lists;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.module.slideshow.slides.SlidesController;
import eu.ydp.empiria.player.client.module.slideshow.structure.*;
import eu.ydp.empiria.player.client.module.slideshow.view.buttons.SlideshowButtonsView;
import eu.ydp.empiria.player.client.module.slideshow.view.player.SlideshowPlayerView;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class SlideshowPlayerPresenterTest {

	@InjectMocks
	private SlideshowPlayerPresenter testObj;
	@Mock
	private SlideshowPlayerView playerView;
	@Mock
	private SlidesController slidesController;
	@Mock
	private SlideshowButtonsView buttonsView;

	@Test
	public void shouldSetTitle_andSetSlides() {
		// given
		SlideshowBean slideshowBean = mock(SlideshowBean.class);
		String title = "title";
		when(slideshowBean.getTitle()).thenReturn(title);

		List<SlideBean> slides = Lists.newArrayList();
		when(slideshowBean.getSlideBeans()).thenReturn(slides);

		// when
		testObj.init(slideshowBean);

		// then
		verify(playerView).setTitle(title);
		verify(slidesController).setSlides(slides);
	}

	@Test
	public void shouldReturnPlayerWidget() {
		// given
		Widget widget = mock(Widget.class);
		when(playerView.asWidget()).thenReturn(widget);

		// when
		Widget result = testObj.asWidget();

		// then
		assertThat(result).isEqualTo(widget);
	}

	@Test
	public void shouldEnablePreviousButton() {
		// given
		boolean enabled = true;

		// when
		testObj.setEnabledPreviousButton(enabled);

		// then
		verify(buttonsView).setEnabledPreviousButton(true);
	}

	@Test
	public void shouldDisablePreviousButton() {
		// given
		boolean enabled = false;

		// when
		testObj.setEnabledPreviousButton(enabled);

		// then
		verify(buttonsView).setEnabledPreviousButton(false);
	}

	@Test
	public void shouldEnableNextButton() {
		// given
		boolean enabled = true;

		// when
		testObj.setEnabledNextButton(enabled);

		// then
		verify(buttonsView).setEnabledNextButton(true);
	}

	@Test
	public void shouldDisableNextButton() {
		// given
		boolean enabled = false;

		// when
		testObj.setEnabledNextButton(enabled);

		// then
		verify(buttonsView).setEnabledNextButton(false);
	}

	@Test
	public void shouldSetPlayButtonToPause() {
		// given
		boolean pause = true;

		// when
		testObj.setPlayButtonDown(pause);

		// then
		verify(buttonsView).setPlayButtonDown(true);
	}

	@Test
	public void shouldSetPlayButtonToPlay() {
		// given
		boolean pause = false;

		// when
		testObj.setPlayButtonDown(pause);

		// then
		verify(buttonsView).setPlayButtonDown(false);
	}

	@Test
	public void shouldShowNextSlide() {
		// given

		// when
		testObj.executeNext();

		// then
		verify(slidesController).showNextSlide();
	}

	@Test
	public void shouldShowPreviousSlide() {
		// given

		// when
		testObj.executePrevious();

		// then
		verify(slidesController).showPreviousSlide();
	}

	@Test
	public void shouldPlaySlideshow() {
		// given

		// when
		testObj.executePlay();

		// then
		verify(slidesController).playSlideshow();
	}

	@Test
	public void shouldPauseSlideshow() {
		// given

		// when
		testObj.executePause();

		// then
		verify(slidesController).pauseSlideshow();
	}

	@Test
	public void shouldStopSlideshow() {
		// given

		// when
		testObj.executeStop();

		// then
		verify(slidesController).stopSlideshow();
	}
}