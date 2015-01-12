package eu.ydp.empiria.player.client.module.slideshow.structure;


import com.google.gwt.core.client.GWT;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;
import eu.ydp.empiria.player.client.AbstractEmpiriaPlayerGWTTestCase;
import java.util.List;

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
		firstSlideExpected.setStartTime(0);
		firstSlideExpected.setSource(firstSourceBean);

		secondSourceBean = new SourceBean();
		secondSourceBean.setSrc("source2");

		secondSlideExpected = new SlideBean();
		secondSlideExpected.setTitle("slide title2");
		secondSlideExpected.setNarration("narration2");
		secondSlideExpected.setStartTime(100);
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
	}

	private void assertSlidesEquals(SlideBean slideExpected, SlideBean slideActual) {
		assertEquals(slideExpected.getTitle(), slideActual.getTitle());
		assertEquals(slideExpected.getNarration(), slideActual.getNarration());
		assertEquals(slideExpected.getStartTime(), slideActual.getStartTime());

		assertSourceEqual(slideExpected.getSource(), slideActual.getSource());
	}

	private void assertSourceEqual(SourceBean sourceExpected, SourceBean sourceActual) {
		assertEquals(sourceExpected.getSrc(), sourceActual.getSrc());
	}

	private SlideshowPlayerBean parse(String xml) {
		SlideshowJAXBParser jaxbParserFactory = GWT.create(SlideshowJAXBParser.class);
		JAXBParser<SlideshowPlayerBean> jaxbParser = jaxbParserFactory.create();
		SlideshowPlayerBean slBean = jaxbParser.parse(xml);
		return slBean;
	}
}
