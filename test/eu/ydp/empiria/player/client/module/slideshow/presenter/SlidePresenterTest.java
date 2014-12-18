package eu.ydp.empiria.player.client.module.slideshow.presenter;

import static org.mockito.Mockito.*;

import eu.ydp.empiria.player.client.module.slideshow.structure.*;
import eu.ydp.empiria.player.client.module.slideshow.view.slide.SlideView;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SlidePresenterTest {

	@InjectMocks
	private SlidePresenter testObj;
	@Mock
	private SlideView view;

	@Test
	public void shouldSetTitle() {
		// given
		String title = "title";

		// when
		testObj.setTitle(title);

		// then
		verify(view).setSlideTitle(title);
	}

	@Test
	public void shouldSetNarration() {
		// given
		String narration = "narration";

		// when
		testObj.setNarration(narration);

		// then
		verify(view).setNarration(narration);
	}

	@Test
	public void shouldSetSrcImage() {
		// given
		String src = "src";
		SourceBean srcBean = new SourceBean();
		srcBean.setSrc(src);

		// when
		testObj.setImageSrc(srcBean);

		// then
		verify(view).setImage(src);
	}

	@Test
	public void shouldReplaceAll() {
		// given
		String title = "title";
		String narration = "narration";
		String src = "src";

		SourceBean srcBean = new SourceBean();
		srcBean.setSrc(src);

		SlideBean slide = new SlideBean();
		slide.setTitle(title);
		slide.setNarration(narration);
		slide.setSource(srcBean);

		// when
		testObj.replaceView(slide);

		// then
		verify(view).setSlideTitle(title);
		verify(view).setNarration(narration);
		verify(view).setImage(src);

	}
}
