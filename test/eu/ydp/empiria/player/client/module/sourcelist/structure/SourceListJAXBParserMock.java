package eu.ydp.empiria.player.client.module.sourcelist.structure;

import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;

import eu.ydp.empiria.player.client.jaxb.JAXBParserImpl;

@SuppressWarnings("PMDs")
public class SourceListJAXBParserMock implements SourceListJAXBParser {
	public static final String XML = "<sourceList id=\"dummy2\" moveElements=\"true\" shuffle=\"true\" sourcelistId=\"id1\">"
			+ "<simpleSourceListItem alt=\"psa\">psa</simpleSourceListItem>" + "<simpleSourceListItem alt=\"kota\">kota</simpleSourceListItem>"
			+ "<simpleSourceListItem alt=\"tygrysa\">tygrysa</simpleSourceListItem>" + "</sourceList>";

	public static final String XML_WITHOUT_MOVE_ELEMENTS = "<sourceList id=\"dummy2\" moveElements=\"false\" shuffle=\"true\" sourcelistId=\"id1\">"
			+ "<simpleSourceListItem alt=\"psa\">psa</simpleSourceListItem>" + "<simpleSourceListItem alt=\"kota\">kota</simpleSourceListItem>"
			+ "<simpleSourceListItem alt=\"tygrysa\">tygrysa</simpleSourceListItem>" + "</sourceList>";

	public static final String XML_WITHOUT_SHUFFLE = "<sourceList id=\"dummy2\" moveElements=\"true\" shuffle=\"false\" sourcelistId=\"id1\">"
			+ "<simpleSourceListItem alt=\"psa\">psa</simpleSourceListItem>" + "<simpleSourceListItem alt=\"kota\">kota</simpleSourceListItem>"
			+ "<simpleSourceListItem alt=\"tygrysa\">tygrysa</simpleSourceListItem>" + "</sourceList>";

	public static final String XML_WITH_MORE_ITEMS = "<sourceList id=\"dummy2\" moveElements=\"true\" shuffle=\"true\" sourcelistId=\"id1\">"
			+ "<simpleSourceListItem alt=\"psa\">psa</simpleSourceListItem>" + "<simpleSourceListItem alt=\"kota\">kota</simpleSourceListItem>"
			+ "<simpleSourceListItem alt=\"tygrysa\">tygrysa</simpleSourceListItem>" + "<simpleSourceListItem alt=\"psa\">psa1</simpleSourceListItem>"
			+ "<simpleSourceListItem alt=\"kota\">kota1</simpleSourceListItem>" + "<simpleSourceListItem alt=\"tygrysa\">tygrysa1</simpleSourceListItem>"
			+ "<simpleSourceListItem alt=\"psa\">psa2</simpleSourceListItem>" + "<simpleSourceListItem alt=\"kota\">kota2</simpleSourceListItem>"
			+ "<simpleSourceListItem alt=\"tygrysa\">tygrysa2</simpleSourceListItem>" + "</sourceList>";

	public static final String XML_TEXTS = "<sourceList id=\"dummy2\" moveElements=\"true\" shuffle=\"false\" sourcelistId=\"id1\">"
			+ "<simpleSourceListItem alt=\"psa\">psa</simpleSourceListItem>" + "<simpleSourceListItem alt=\"kota\">kota</simpleSourceListItem>"
			+ "<simpleSourceListItem alt=\"tygrysa\">tygrysa</simpleSourceListItem>" + "</sourceList>";

	public static final String XML_IMAGES = "<sourceList id=\"dummy2\" moveElements=\"true\" shuffle=\"false\" sourcelistId=\"id1\">"
			+ "<simpleSourceListItem alt=\"psa\"><img src='psa.png'/></simpleSourceListItem>"
			+ "<simpleSourceListItem alt=\"kota\"><img src='kota.png'/></simpleSourceListItem>"
			+ "<simpleSourceListItem alt=\"tygrysa\"><img src='tygrysa.png'/></simpleSourceListItem>" + "</sourceList>";

	public static final String XML_IMAGES_WITH_DIMENSION = "<sourceList id=\"dummy2\" moveElements=\"true\" shuffle=\"false\" imagesWidth=\"600\" imagesHeight=\"602\" sourcelistId=\"id1\">"
			+ "<simpleSourceListItem alt=\"psa\"><img src='psa.png'/></simpleSourceListItem>"
			+ "<simpleSourceListItem alt=\"kota\"><img src='kota.png'/></simpleSourceListItem>"
			+ "<simpleSourceListItem alt=\"tygrysa\"><img src='tygrysa.png'/></simpleSourceListItem>" + "</sourceList>";

	@Override
	public JAXBParser<SourceListBean> create() {
		return new JAXBParserImpl<SourceListBean>(SourceListJAXBParser.class);
	}

}
