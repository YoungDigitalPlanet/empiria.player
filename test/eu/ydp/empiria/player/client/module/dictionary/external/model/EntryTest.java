package eu.ydp.empiria.player.client.module.dictionary.external.model;

import org.junit.Test;

import com.google.gwt.xml.client.Element;

import eu.ydp.gwtutil.xml.XMLParser;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class EntryTest {

	private static final String POL = "used to refer to a single thing when you are mentioning it for the first time, and the listener does not already know about it";
	private static final String WORD_ELEMENT_WITH_NODE = "<word ang='a' angSound='dict_file_01.mp3' category='A_Miscellaneous' desc='Have you got a spare pen?' descrSound='dict_file_01s.mp3' post='article'><pol>"
			+ POL + "</pol></word>";
	private static final String WORD_ELEMENT_WITH_ATTRIBUTE = "<word ang='a' angSound='dict_file_01.mp3' category='A_Miscellaneous' desc='Have you got a spare pen?' descrSound='dict_file_01s.mp3' post='article' pol='"
			+ POL + "'/>";
	private static final String A = "a";
	private static final String A_V = "av";
	private static final String B = "b";
	private static final String B_V_1 = "bv1";
	private static final String B_V_2 = "bv2";
	private static final String TEST_ELEMENT = String.format("<word %1$s='%2$s' %3$s='%4$s'><%3$s>%5$s</%3$s></word>", A, A_V, B, B_V_1, B_V_2);

	@Test
	public void polFromNode() {
		// given
		Element element = XMLParser.parse(WORD_ELEMENT_WITH_NODE).getDocumentElement();

		// when
		Entry e = new Entry(element);

		// then
		assertThat(e.getPol(), equalTo(POL));
	}

	@Test
	public void polFromAttribute() {
		// given
		Element element = XMLParser.parse(WORD_ELEMENT_WITH_ATTRIBUTE).getDocumentElement();

		// when
		Entry e = new Entry(element);

		// then
		assertThat(e.getPol(), equalTo(POL));
	}

	@Test
	public void fetchValueFromAttr() {
		// given
		Element element = XMLParser.parse(TEST_ELEMENT).getDocumentElement();

		// when
		Entry e = new Entry(element);
		String value = e.fetchValue(element, A, false);

		// then
		assertThat(value, equalTo(A_V));
	}

	@Test
	public void fetchValueFromAttrWithInvalidFlag() {
		// given
		Element element = XMLParser.parse(TEST_ELEMENT).getDocumentElement();

		// when
		Entry e = new Entry(element);
		String value = e.fetchValue(element, A, true);

		// then
		assertThat(value, equalTo(A_V));
	}

	@Test
	public void fetchValueFromAttrNotNode() {
		// given
		Element element = XMLParser.parse(TEST_ELEMENT).getDocumentElement();

		// when
		Entry e = new Entry(element);
		String value = e.fetchValue(element, B, false);

		// then
		assertThat(value, equalTo(B_V_1));
	}

	@Test
	public void fetchValueFromNode() {
		// given
		Element element = XMLParser.parse(TEST_ELEMENT).getDocumentElement();

		// when
		Entry e = new Entry(element);
		String value = e.fetchValue(element, B, true);

		// then
		assertThat(value, equalTo(B_V_2));
	}

}
