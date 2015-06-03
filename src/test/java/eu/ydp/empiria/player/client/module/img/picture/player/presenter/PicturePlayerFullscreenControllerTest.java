package eu.ydp.empiria.player.client.module.img.picture.player.presenter;

import static org.mockito.Mockito.*;

import eu.ydp.empiria.player.client.module.UserAgentCheckerWrapper;
import eu.ydp.empiria.player.client.module.img.picture.player.lightbox.*;
import eu.ydp.empiria.player.client.module.img.picture.player.structure.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PicturePlayerFullscreenControllerTest {

	@InjectMocks
	private PicturePlayerFullscreenController testObj;
	@Mock
	private LightBoxProvider modeProvider;
	@Mock
	private LightBox lightBox;
	@Mock
	private PicturePlayerFullscreenDelay fullscreenDelay;
	@Mock
	private UserAgentCheckerWrapper userAgentCheckerWrapper;

	private PicturePlayerBean bean = new PicturePlayerBean();
	private PicturePlayerTitleBean titleBean = new PicturePlayerTitleBean();

	private String mode = "mode";
	private String srcFullscreen = "src_f";
	private String title = "title";

	@Before
	public void setUp() throws Exception {
		titleBean.setTitleName(title);
		bean.setSrcFullScreen(srcFullscreen);
		bean.setTitleBean(titleBean);
		bean.setFullscreenMode(mode);
		when(modeProvider.getFullscreen(mode)).thenReturn(lightBox);
	}

	@Test
	public void shouldOpenFullscreen_withoutDelay() {
		// given
		when(userAgentCheckerWrapper.isStackAndroidBrowser()).thenReturn(false);

		// when
		testObj.openFullscreen(bean);

		// then
		verify(modeProvider).getFullscreen(mode);
		verify(lightBox).openImage(srcFullscreen, title);
	}

	@Test
	public void shouldOpenFullscreen_withDelay() {
		// given
		when(userAgentCheckerWrapper.isStackAndroidBrowser()).thenReturn(true);

		// when
		testObj.openFullscreen(bean);

		// then
		verify(modeProvider).getFullscreen(mode);
		verify(fullscreenDelay).openImageWithDelay(lightBox, bean);
	}
}