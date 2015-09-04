package eu.ydp.empiria.player.client.module.img.picture.player.structure;

import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.controller.body.InlineBodyGeneratorSocket;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(GwtMockitoTestRunner.class)
public class PictureAltProviderTest {

    @InjectMocks
    private PictureAltProvider testObj;
    @Mock
    private InlineBodyGeneratorSocket inlineBodyGeneratorSocket;
    @Mock
    private PictureTitleProvider pictureTitleProvider;
    @Mock
    private PicturePlayerBean bean;

    private String TEST_ALT = "testAlt";
    private String TEST_TITLE = "testTitle";

    @Test
    public void shouldReturnAlt_whenAltIsPresent() {
        // given
        when(bean.hasAlt()).thenReturn(true);
        when(bean.getAlt()).thenReturn(TEST_ALT);

        //when
        String result = testObj.getPictureAltString(bean);

        //then
        assertThat(result).isEqualTo(TEST_ALT);
    }

    @Test
    public void shouldReturnTitle_whenAltIsNotPresent() throws Exception {
        //given
        when(bean.hasAlt()).thenReturn(false);
        when(pictureTitleProvider.getPictureTitleString(bean)).thenReturn(TEST_TITLE);

        //when
        String result = testObj.getPictureAltString(bean);

        //then
        assertThat(result).isEqualTo(TEST_TITLE);
    }
}