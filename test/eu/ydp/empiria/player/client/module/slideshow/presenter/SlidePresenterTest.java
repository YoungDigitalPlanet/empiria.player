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
		testObj.replaceViewData(slide);

		// then
		verify(view).clearSlideTitle();
		verify(view).setSlideTitle(title);
		verify(view).clearNarration();
		verify(view).setNarration(narration);
		verify(view).setImage(src);
	}

	@Test
	public void shouldClearAndNotReplace() {
		// given
		String title = "";
		String narration = "";
		String src = "";

		SourceBean srcBean = new SourceBean();
		srcBean.setSrc(src);

		SlideBean slide = new SlideBean();
		slide.setTitle(title);
		slide.setNarration(narration);
		slide.setSource(srcBean);

		// when
		testObj.replaceViewData(slide);

		// then
		verify(view).clearSlideTitle();
		verify(view, never()).setSlideTitle(title);
		verify(view).clearNarration();
		verify(view, never()).setNarration(narration);
		verify(view, never()).setImage(src);
	}
}
