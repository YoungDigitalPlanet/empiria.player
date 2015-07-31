package eu.ydp.empiria.player.client.module.img.picture.player.presenter;

import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import eu.ydp.empiria.player.client.module.img.picture.player.structure.PicturePlayerBean;
import eu.ydp.empiria.player.client.module.img.picture.player.view.PicturePlayerView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PicturePlayerPresenterTest {

    @InjectMocks
    private PicturePlayerPresenter testObj;
    @Mock
    private PicturePlayerView view;
    @Mock
    private PicturePlayerFullscreenController fullscreenController;

    @Mock
    private InlineBodyGeneratorSocket inlineBodyGeneratorSocket;

    private PicturePlayerBean bean = new PicturePlayerBean();

    private String src = "src";
    private String srcFullscreen = "src_f";
    private String title = "title";

    @Before
    public void setUp() throws Exception {
        bean.setSrc(src);
        bean.setSrcFullScreen(srcFullscreen);
    }

    @Test
    public void shouldInitFullPicturePlayer() {
        // when
        testObj.init(bean, inlineBodyGeneratorSocket);

        // then
        verify(view).setImage(bean.getSrc());
        verify(view).addFullscreenButton();
    }

    @Test
    public void shouldNotInitFullscreen_whenModuleIsTemplate() {
        // given
        testObj.setTemplate(true);

        // when
        testObj.init(bean, inlineBodyGeneratorSocket);

        // then
        verify(view).setImage(bean.getSrc());
        verify(view, never()).addFullscreenButton();
    }

    @Test
    public void shouldNotInitFullscreen_whenSrcFullscreenIsNull() {
        // given
        bean.setSrcFullScreen(null);

        // when
        testObj.init(bean, inlineBodyGeneratorSocket);

        // then
        verify(view).setImage(bean.getSrc());
        verify(view, never()).addFullscreenButton();
    }

    @Test
    public void shouldNotInitFullscreen_whenSrcFullscreenIsEmpty() {
        // given
        bean.setSrcFullScreen("");

        // when
        testObj.init(bean, inlineBodyGeneratorSocket);

        // then
        verify(view).setImage(bean.getSrc());
        verify(view, never()).addFullscreenButton();
    }


    @Test
    public void shouldOpenFullscreen() {
        // given
        testObj.init(bean, inlineBodyGeneratorSocket);

        // when
        testObj.openFullscreen();

        // then
        verify(fullscreenController).openFullscreen(bean, inlineBodyGeneratorSocket);

    }
}
