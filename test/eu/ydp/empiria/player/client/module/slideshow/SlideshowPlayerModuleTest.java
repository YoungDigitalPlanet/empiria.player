package eu.ydp.empiria.player.client.module.slideshow;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.module.slideshow.presenter.SlideshowPlayerPresenter;
import eu.ydp.empiria.player.client.module.slideshow.structure.*;
import eu.ydp.gwtutil.client.service.json.IJSONService;
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
	private SlideshowModuleStructure moduleStructure;

	@Test
	public void shouldReturnWidget() {
		// given
		Widget widget = mock(Widget.class);
		when(presenter.asWidget()).thenReturn(widget);

		// when
		Widget result = testObj.getView();

		// then
		assertThat(result).isEqualTo(widget);
	}

	@Test
	public void shouldInitPresenter() {
		// given
		Element element = mock(Element.class);
		SlideshowPlayerBean bean = new SlideshowPlayerBean();
		SlideshowBean slideshowBean = new SlideshowBean();
		bean.setSlideshowBean(slideshowBean);
		when(moduleStructure.getBean()).thenReturn(bean);

		// when
		testObj.initModule(element);

		// then
		verify(presenter).init(slideshowBean);
	}
}
