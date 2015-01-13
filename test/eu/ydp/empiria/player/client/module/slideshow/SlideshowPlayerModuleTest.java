package eu.ydp.empiria.player.client.module.slideshow;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.google.common.collect.Lists;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.module.slideshow.presenter.SlideshowPlayerPresenter;
import eu.ydp.empiria.player.client.module.slideshow.slides.SlideshowController;
import eu.ydp.empiria.player.client.module.slideshow.structure.*;
import eu.ydp.gwtutil.client.service.json.IJSONService;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class SlideshowPlayerModuleTest {

	@InjectMocks
	private SlideshowPlayerModule testObj;
	@Mock
	private IJSONService ijsonService;
	@Mock
	private SlideshowPlayerPresenter presenter;
	@Mock
	private SlideshowController controller;
	@Mock
	private SlideshowModuleStructure moduleStructure;

	@Test
	public void shouldReturnWidget() {
		// given
		Widget widget = mock(Widget.class);
		when(presenter.getView()).thenReturn(widget);

		// when
		Widget result = testObj.getView();

		// then
		assertThat(result).isEqualTo(widget);
	}

	@Test
	public void shouldInitPresenter() {
		// given
		Element element = mock(Element.class);

		SlideshowBean slideshowBean = new SlideshowBean();
		List<SlideBean> slides = Lists.newArrayList();
		slideshowBean.setSlideBeans(slides);

		SlideshowPlayerBean bean = new SlideshowPlayerBean();
		bean.setSlideshowBean(slideshowBean);

		when(moduleStructure.getBean()).thenReturn(bean);

		// when
		testObj.initModule(element);

		// then
		verify(presenter).init(slideshowBean);
		verify(controller).init(slideshowBean.getSlideBeans());
	}
}
