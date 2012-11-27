package eu.ydp.empiria.player.client.module.sourcelist.structure;

import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;

import eu.ydp.empiria.player.client.jaxb.JAXBParserImpl;

@SuppressWarnings("PMDs")
public class SourceListJAXBParserMock implements SourceListJAXBParser {
	public static final String XML = "<sourceList id=\"dummy2\" moveElements=\"true\" shuffle=\"true\">"+
			"<simpleSourceListItem alt=\"psa\">psa</simpleSourceListItem>"+
			"<simpleSourceListItem alt=\"kota\">kota</simpleSourceListItem>"+
			"<simpleSourceListItem alt=\"tygrysa\">tygrysa</simpleSourceListItem>"+
						"</sourceList>";

	public static final String XML_WITHOUT_MOVE_ELEMENTS = "<sourceList id=\"dummy2\" moveElements=\"false\" shuffle=\"true\">"+
			"<simpleSourceListItem alt=\"psa\">psa</simpleSourceListItem>"+
			"<simpleSourceListItem alt=\"kota\">kota</simpleSourceListItem>"+
			"<simpleSourceListItem alt=\"tygrysa\">tygrysa</simpleSourceListItem>"+
						"</sourceList>";

	public static final String XML_WITHOUT_SHUFFLE = "<sourceList id=\"dummy2\" moveElements=\"true\" shuffle=\"false\">"+
			"<simpleSourceListItem alt=\"psa\">psa</simpleSourceListItem>"+
			"<simpleSourceListItem alt=\"kota\">kota</simpleSourceListItem>"+
			"<simpleSourceListItem alt=\"tygrysa\">tygrysa</simpleSourceListItem>"+
						"</sourceList>";

	public static final String XML_WITH_MORE_ITEMS = "<sourceList id=\"dummy2\" moveElements=\"true\" shuffle=\"true\">"+
			"<simpleSourceListItem alt=\"psa\">psa</simpleSourceListItem>"+
			"<simpleSourceListItem alt=\"kota\">kota</simpleSourceListItem>"+
			"<simpleSourceListItem alt=\"tygrysa\">tygrysa</simpleSourceListItem>"+
			"<simpleSourceListItem alt=\"psa\">psa1</simpleSourceListItem>"+
			"<simpleSourceListItem alt=\"kota\">kota1</simpleSourceListItem>"+
			"<simpleSourceListItem alt=\"tygrysa\">tygrysa1</simpleSourceListItem>"+
			"<simpleSourceListItem alt=\"psa\">psa2</simpleSourceListItem>"+
			"<simpleSourceListItem alt=\"kota\">kota2</simpleSourceListItem>"+
			"<simpleSourceListItem alt=\"tygrysa\">tygrysa2</simpleSourceListItem>"+
						"</sourceList>";
	@Override
	public JAXBParser<SourceListBean> create() {
		return new JAXBParserImpl<SourceListBean>(SourceListJAXBParser.class);
	}

}
