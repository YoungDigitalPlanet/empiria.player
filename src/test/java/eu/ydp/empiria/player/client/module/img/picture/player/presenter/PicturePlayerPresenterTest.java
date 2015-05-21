package eu.ydp.empiria.player.client.module.img.picture.player.presenter;

import static org.mockito.Mockito.*;

import com.google.gwt.event.dom.client.ClickHandler;
import eu.ydp.empiria.player.client.module.img.picture.player.structure.*;
import eu.ydp.empiria.player.client.module.img.picture.player.view.PicturePlayerView;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PicturePlayerPresenterTest {

	@InjectMocks
	private PicturePlayerPresenter testObj;
	@Mock
	private PicturePlayerView view;
	@Mock
	private PicturePlayerFullscreenController fullscreenController;

	private PicturePlayerBean bean = new PicturePlayerBean();
	private PicturePlayerTitleBean titleBean = new PicturePlayerTitleBean();

	private String src = "src";
	private String srcFullscreen = "src_f";
	private String title = "title";

	@Before
	public void setUp() throws Exception {
		bean.setSrc(src);
		bean.setSrcFullScreen(srcFullscreen);
		titleBean.setTitleName(title);
		bean.setTitleBean(titleBean);
	}

	@Test
	public void shouldInitFullPicturePlayer() {
		// when
		testObj.init(bean);

		// then
		verify(view).setImage(title, src);
		verify(view).addFullscreenButton();
	}

	@Test
	public void shouldNotInitFullscreen_whenModuleIsTemplate() {
		// given
		testObj.setTemplate(true);

		// when
		testObj.init(bean);

		// then
		verify(view).setImage(title, src);
		verify(view, never()).addFullscreenButton();
	}

	@Test
	public void shouldNotInitFullscreen_whenSrcFullscreenIsNull() {
		// given
		bean.setSrcFullScreen(null);

		// when
		testObj.init(bean);

		// then
		verify(view).setImage(title, src);
		verify(view, never()).addFullscreenButton();
	}

	@Test
	public void shouldNotInitFullscreen_whenSrcFullscreenIsEmpty() {
		// given
		bean.setSrcFullScreen("");

		// when
		testObj.init(bean);

		// then
		verify(view).setImage(title, src);
		verify(view, never()).addFullscreenButton();
	}

	@Test
	public void shouldSetEmptyTitle_whenTitleNotSet() {
		// given
		bean.setTitleBean(null);

		// when
		testObj.init(bean);

		// then
		verify(view).setImage("", src);
		verify(view).addFullscreenButton();
	}

	@Test
	public void shouldOpenFullscreen() {
		// given
		testObj.init(bean);

		// when
		testObj.openFullscreen();

		// then
		verify(fullscreenController).openFullScreen(bean);

	}
}