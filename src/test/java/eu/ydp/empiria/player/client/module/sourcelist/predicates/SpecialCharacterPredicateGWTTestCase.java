package eu.ydp.empiria.player.client.module.sourcelist.predicates;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.XMLParser;
import eu.ydp.empiria.player.client.EmpiriaPlayerGWTTestCase;

public class SpecialCharacterPredicateGWTTestCase extends EmpiriaPlayerGWTTestCase {
    private SpecialCharacterPredicate testObj = new SpecialCharacterPredicate();

    public void testShouldReturnTrue_whenContainsEscapedLtCharacters() {
        // given
        String escapedText = "<div>escaped &lt; text</div>";
        Element element = parseXml(escapedText);

        // when
        boolean result = testObj.apply(element);

        // then
        assertTrue(result);
    }

    public void testShouldReturnTrue_whenContainsEscapedGtCharacters() {
        // given
        String escapedText = "<div>escaped &gt; text</div>";
        Element element = parseXml(escapedText);

        // when
        boolean result = testObj.apply(element);

        // then
        assertTrue(result);
    }

    public void testShouldReturnTrue_whenContainsEscapedAmpCharacters() {
        // given
        String escapedText = "<div>escaped &amp; text</div>";
        Element element = parseXml(escapedText);

        // when
        boolean result = testObj.apply(element);

        // then
        assertTrue(result);
    }

    public void testShouldReturnTrue_whenContainsEscapedQuotCharacters() {
        // given
        String escapedText = "<div>escaped &quot; text</div>";
        Element element = parseXml(escapedText);

        // when
        boolean result = testObj.apply(element);

        // then
        assertTrue(result);
    }

    public void testShouldReturnTrue_whenContainsEscapedAposCharacters() {
        // given
        String escapedText = "<div>escaped &apos; text</div>";
        Element element = parseXml(escapedText);

        // when
        boolean result = testObj.apply(element);

        // then
        assertTrue(result);
    }

    private Element parseXml(String content) {
        Document parse = XMLParser.parse(content);
        return parse.getDocumentElement();
    }
}
