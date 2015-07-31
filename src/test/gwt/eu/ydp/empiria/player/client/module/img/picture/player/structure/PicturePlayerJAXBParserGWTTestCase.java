package eu.ydp.empiria.player.client.module.img.picture.player.structure;

import com.google.gwt.core.client.GWT;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;

public class PicturePlayerJAXBParserGWTTestCase extends EmpiriaPlayerGWTTestCase {

    private PicturePlayerBean picturePlayerExpected = new PicturePlayerBean();
    private PicturePlayerTitleBean titleExpected = new PicturePlayerTitleBean();

    public PicturePlayerJAXBParserGWTTestCase() {
        titleExpected.setTitleName("title");

        picturePlayerExpected.setFullscreenMode("mode");
        picturePlayerExpected.setSrcFullScreen("src_f.jpg");
        picturePlayerExpected.setSrc("src.jpg");
        picturePlayerExpected.setTitleBean(titleExpected);
    }

    public void testFullPicturePlayer() {
        // when
        PicturePlayerBean picturePlayerResult = parse(PicturePlayerJAXBParserMock.FULL_PICTURE_PLAYER);

        // then
        assertPicturePlayerEquals(picturePlayerExpected, picturePlayerResult);
    }

    public void testPicturePlayer_withoutTitle() {
        // given
        picturePlayerExpected.setTitleBean(null);

        // when
        PicturePlayerBean picturePlayerResult = parse(PicturePlayerJAXBParserMock.PICTURE_PLAYER_WITHOUT_TITLE);

        // then
        assertPicturePlayerEquals(picturePlayerExpected, picturePlayerResult);

    }

    public void testPicturePlayer_withoutFullscreenMode() {
        // given
        picturePlayerExpected.setFullscreenMode(null);

        // when
        PicturePlayerBean picturePlayerResult = parse(PicturePlayerJAXBParserMock.PICTURE_PLAYER_WITHOUT_FULLSCREENMODE);

        // then
        assertPicturePlayerEquals(picturePlayerExpected, picturePlayerResult);
    }

    public void testPicturePlayer_withoutFullscreen() {
        // given
        picturePlayerExpected.setFullscreenMode(null);
        picturePlayerExpected.setSrcFullScreen(null);

        // when
        PicturePlayerBean picturePlayerResult = parse(PicturePlayerJAXBParserMock.PICTURE_PLAYER_WITHOUT_SRC_FULLSCREEN);

        // then
        assertPicturePlayerEquals(picturePlayerExpected, picturePlayerResult);

    }

    private void assertPicturePlayerEquals(PicturePlayerBean expected, PicturePlayerBean result) {
        assertEquals(expected.getFullscreenMode(), result.getFullscreenMode());
        assertEquals(expected.getSrcFullScreen(), result.getSrcFullScreen());
        assertEquals(expected.getSrc(), result.getSrc());
        if (result.hasTitle()) {
            assertTitleEquals(expected.getTitleBean(), result.getTitleBean());
        }
    }

    private void assertTitleEquals(PicturePlayerTitleBean result, PicturePlayerTitleBean expected) {
        assertEquals(expected.getTitle(), result.getTitle());
    }

    private PicturePlayerBean parse(String xml) {
        PicturePlayerJAXBParser jaxbParserFactory = GWT.create(PicturePlayerJAXBParser.class);
        JAXBParser<PicturePlayerBean> jaxbParser = jaxbParserFactory.create();
        PicturePlayerBean picturePlayerBean = jaxbParser.parse(xml);
        return picturePlayerBean;
    }
}
