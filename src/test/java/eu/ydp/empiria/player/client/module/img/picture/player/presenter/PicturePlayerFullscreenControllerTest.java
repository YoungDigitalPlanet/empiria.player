package eu.ydp.empiria.player.client.module.img.picture.player.presenter;

import eu.ydp.empiria.player.client.module.UserAgentCheckerWrapper;
import eu.ydp.empiria.player.client.module.img.picture.player.lightbox.LightBox;
import eu.ydp.empiria.player.client.module.img.picture.player.lightbox.LightBoxProvider;
import eu.ydp.empiria.player.client.module.img.picture.player.structure.PicturePlayerBean;
import eu.ydp.empiria.player.client.module.img.picture.player.structure.PicturePlayerTitleBean;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
