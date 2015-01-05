package eu.ydp.empiria.player.client.module.slideshow.structure;

import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;
import eu.ydp.empiria.player.client.jaxb.JAXBParserImpl;

public class SlideshowJAXBParserMock implements SlideshowJAXBParser {

	public static final String FULL_SLIDESHOW = "<slideshowPlayer id=\"dummy1_1\"><slideshow><title>title</title>"
			+ "<slide startTime=\"0\"><slideTitle>slide title1</slideTitle>"
			+ "<narration>narration1</narration> <source src=\"source1\"/></slide>"
			+ "<slide startTime=\"100\"><slideTitle>slide title2</slideTitle>"
			+ "<narration>narration2</narration> <source src=\"source2\"/></slide>"
			+ "</slideshow></slideshowPlayer>";

	public static final String SLIDESHOW_WITHOUT_NARRATION = "<slideshowPlayer id=\"dummy1_1\"><slideshow><title>title</title>"
			+ "<slide startTime=\"0\"><slideTitle>slide title1</slideTitle>"
			+ "<source src=\"source1\"/></slide>"
			+ "</slideshow></slideshowPlayer>";

	public static final String FULL_SLIDESHOW_WITHOUT_TITLE = "<slideshowPlayer id=\"dummy1_1\"><slideshow><title/>"
			+ "<slide startTime=\"0\"><slideTitle>slide title1</slideTitle>"
			+ "<narration>narration1</narration> <source src=\"source1\"/></slide>"
			+ "<slide startTime=\"100\"><slideTitle>slide title2</slideTitle>"
			+ "<narration>narration2</narration> <source src=\"source2\"/></slide>"
			+ "</slideshow></slideshowPlayer>";

	@Override
	public JAXBParser<SlideshowPlayerBean> create() {
		return new JAXBParserImpl<SlideshowPlayerBean>(SlideshowJAXBParser.class);
	}
}
