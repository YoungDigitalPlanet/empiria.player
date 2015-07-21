package eu.ydp.empiria.player.client.module.sourcelist.structure;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import eu.ydp.gwtutil.xml.XMLParser;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;


public class ComplexTextCheckerTest {

    private ComplexTextChecker testObj = new ComplexTextChecker();

    @Test
    public void shouldReturnFalse_whenIsSimpleText() {
        // given
        String simpleText = "<div>simpleText</div>";
        Element element = parseXml(simpleText);

        // when
        boolean result = testObj.hasComplexText(element);

        // then
        assertThat(result).isFalse();
    }

    @Test
    public void shouldReturnTrue_whenContainsBoldText() {
        // given
        String boldText = "<div><b>bold</b> text</div>";
        Element element = parseXml(boldText);

        // when
        boolean result = testObj.hasComplexText(element);

        // then
        assertThat(result).isTrue();
    }

    @Test
    public void shouldReturnTrue_whenContainsItalicText() {
        // given
        String italicText = "<div><i>italic</i> text</div>";
        Element element = parseXml(italicText);

        // when
        boolean result = testObj.hasComplexText(element);

        // then
        assertThat(result).isTrue();
    }

    @Test
    public void shouldReturnTrue_whenContainsUnderlineText() {
        // given
        String underlineText = "<div><u>underline</u> text</div>";
        Element element = parseXml(underlineText);

        // when
        boolean result = testObj.hasComplexText(element);

        // then
        assertThat(result).isTrue();
    }

    @Test
    public void shouldReturnTrue_whenContainsSupText() {
        // given
        String supText = "<div><sup>sup</sup> text</div>";
        Element element = parseXml(supText);

        // when
        boolean result = testObj.hasComplexText(element);

        // then
        assertThat(result).isTrue();
    }

    @Test
    public void shouldReturnTrue_whenContainsSubText() {
        // given
        String subText = "<div><sub>sub</sub> text</div>";
        Element element = parseXml(subText);

        // when
        boolean result = testObj.hasComplexText(element);

        // then
        assertThat(result).isTrue();
    }

    @Test
    public void shouldReturnTrue_whenContainsInlineMathJaxText() {
        // given
        String inlineMathJax = "<div><inlineMathJax>sub</inlineMathJax> text</div>";
        Element element = parseXml(inlineMathJax);

        // when
        boolean result = testObj.hasComplexText(element);

        // then
        assertThat(result).isTrue();
    }

    private Element parseXml(String content) {
        Document parse = XMLParser.parse(content);
        return parse.getDocumentElement();
    }
}