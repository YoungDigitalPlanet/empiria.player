package eu.ydp.empiria.player.client.module.slideshow.structure;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;

import eu.ydp.empiria.player.client.AbstractEmpiriaPlayerGWTTestCase;

public class SlideshowJAXBParserTest extends AbstractEmpiriaPlayerGWTTestCase {

	private final SourceBean firstSourceBean;
	private final SlideBean firstSlideExpected;
	private final SourceBean secondSourceBean;
	private final SlideBean secondSlideExpected;

	public SlideshowJAXBParserTest() {
		firstSourceBean = new SourceBean();
		firstSourceBean.setSrc("source1");

		firstSlideExpected = new SlideBean();
		firstSlideExpected.setTitle("slide title1");
		firstSlideExpected.setNarration("narration1");
		firstSlideExpected.setSource(firstSourceBean);

		secondSourceBean = new SourceBean();
		secondSourceBean.setSrc("source2");

		secondSlideExpected = new SlideBean();
		secondSlideExpected.setTitle("slide title2");
		secondSlideExpected.setNarration("narration2");
		secondSlideExpected.setSource(secondSourceBean);

	}

	public void testFullSlideshow() {
		// given

		// when
		SlideshowPlayerBean bean = parse(SlideshowJAXBParserMock.FULL_SLIDESHOW);

		// then
		SlideshowBean slideshow = bean.getSlideshowBean();
		String titleResult = slideshow.getTitle();
		assertEquals("title", titleResult);

		List<SlideBean> slides = slideshow.getSlideBeans();
		assertEquals(2, slides.size());

		SlideBean firstSlide = slides.get(0);
		assertSlidesEquals(firstSlideExpected, firstSlide);

		SlideBean secondSlide = slides.get(1);
		assertSlidesEquals(secondSlideExpected, secondSlide);

		assertTemplateIsNotPresent(bean);
	}

	public void testSlideshowWihoutNarration() {
		// given
		firstSlideExpected.setNarration("");

		// when
		SlideshowPlayerBean bean = parse(SlideshowJAXBParserMock.SLIDESHOW_WITHOUT_NARRATION);

		// then
		SlideshowBean slideshow = bean.getSlideshowBean();
		String titleResult = slideshow.getTitle();
		assertEquals("title", titleResult);

		List<SlideBean> slides = slideshow.getSlideBeans();
		assertEquals(1, slides.size());

		SlideBean firstSlide = slides.get(0);
		assertSlidesEquals(firstSlideExpected, firstSlide);

		assertTemplateIsNotPresent(bean);
	}

	public void testSlideshowWithoutTitle() {
		// given

		// when
		SlideshowPlayerBean bean = parse(SlideshowJAXBParserMock.FULL_SLIDESHOW_WITHOUT_TITLE);

		// then
		SlideshowBean slideshow = bean.getSlideshowBean();
		String titleResult = slideshow.getTitle();
		assertEquals("", titleResult);

		List<SlideBean> slides = slideshow.getSlideBeans();
		assertEquals(2, slides.size());

		SlideBean firstSlide = slides.get(0);
		assertSlidesEquals(firstSlideExpected, firstSlide);

		SlideBean secondSlide = slides.get(1);
		assertSlidesEquals(secondSlideExpected, secondSlide);

		assertTemplateIsNotPresent(bean);
	}

	public void testSlideshowWithTemplate() {
		SlideshowPlayerBean slideshowPlayer = parse(SlideshowJAXBParserMock.FULL_SLIDESHOW_WITH_TEMPLATE);
		SlideshowTemplate template = slideshowPlayer.getTemplate();

		assertTrue(slideshowPlayer.hasTemplate());
		assertTrue(template.hasSlideshowPager());
	}

	private void assertSlidesEquals(SlideBean slideExpected, SlideBean slideActual) {
		assertEquals(slideExpected.getTitle(), slideActual.getTitle());
		assertEquals(slideExpected.getNarration(), slideActual.getNarration());

		assertSourceEqual(slideExpected.getSource(), slideActual.getSource());
	}

	private void assertSourceEqual(SourceBean sourceExpected, SourceBean sourceActual) {
		assertEquals(sourceExpected.getSrc(), sourceActual.getSrc());
	}

	private SlideshowPlayerBean parse(String xml) {
		SlideshowJAXBParser jaxbParserFactory = GWT.create(SlideshowJAXBParser.class);
		JAXBParser<SlideshowPlayerBean> jaxbParser = jaxbParserFactory.create();
		SlideshowPlayerBean slideshowPlayer = jaxbParser.parse(xml);
		return slideshowPlayer;
	}

	private void assertTemplateIsNotPresent(SlideshowPlayerBean slideshowPlayer) {
		assertFalse(slideshowPlayer.hasTemplate());
	}
}
