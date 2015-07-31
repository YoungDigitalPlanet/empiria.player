package eu.ydp.empiria.player.client.module.video.structure;

import com.google.gwt.core.client.GWT;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;
import eu.ydp.empiria.player.client.module.model.media.MimeType;

import java.util.List;

public class VideoModuleJAXBParserFactoryGWTTestCase extends EmpiriaPlayerGWTTestCase {

    private final String XML_SINGLE_SOURCE = "<video poster=\"POSTER.png\" width=\"222\" height=\"666\"><source src=\"FILE.mp4\" type=\"video/mp4\" /></video>";
    private final String XML_MULTIPLE_SOURCES = "<video poster=\"POSTER.png\" width=\"222\" height=\"666\"><source src=\"FILE.mp4\" type=\"video/mp4\" /><source src=\"FILE.ogg\" type=\"video/ogg\" /></video>";

    public void test_shouldSerializeWithSingleSource() {
        VideoBean videoBean = parse(XML_SINGLE_SOURCE);

        List<SourceBean> sources = videoBean.getSources();
        assertEquals(666, videoBean.getHeight());
        assertEquals(222, videoBean.getWidth());
        assertEquals("POSTER.png", videoBean.getPoster());
        assertEquals(1, sources.size());
        assertSourceEqual(sources.get(0), "FILE.mp4", MimeType.VIDEO_MP4);
    }

    public void test_shouldSerializeWithMultipleSources() {
        VideoBean videoBean = parse(XML_MULTIPLE_SOURCES);

        List<SourceBean> sources = videoBean.getSources();
        assertEquals(666, videoBean.getHeight());
        assertEquals(222, videoBean.getWidth());
        assertEquals("POSTER.png", videoBean.getPoster());
        assertEquals(2, sources.size());
        assertSourceEqual(sources.get(0), "FILE.mp4", MimeType.VIDEO_MP4);
        assertSourceEqual(sources.get(1), "FILE.ogg", MimeType.VIDEO_OGG);
    }

    private void assertSourceEqual(SourceBean sourceBean, String fileSrc, MimeType mimeType) {
        assertEquals(fileSrc, sourceBean.getSrc());
        assertEquals(mimeType, sourceBean.getMimeType());
    }

    private VideoBean parse(String xml) {
        VideoModuleJAXBParserFactory jaxbParserFactory = GWT.create(VideoModuleJAXBParserFactory.class);
        JAXBParser<VideoBean> jaxbParser = jaxbParserFactory.create();
        VideoBean videoBean = jaxbParser.parse(xml);
        return videoBean;
    }
}
