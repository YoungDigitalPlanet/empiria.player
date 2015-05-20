package eu.ydp.empiria.player.client.module.img.picture.player.presenter;

import static org.mockito.Mockito.*;

import eu.ydp.empiria.player.client.lightbox.*;
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
	private FullScreenModeProvider modeProvider;
	@Mock
	private FullScreen fullScreen;

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
		when(modeProvider.getFullscreen(mode)).thenReturn(fullScreen);
	}

	@Test
	public void shouldOpenFullscreen() {
		// when
		testObj.openFullScreen(bean);

		// then
		verify(modeProvider).getFullscreen(mode);
		verify(fullScreen).openImage(srcFullscreen, title);
	}
}