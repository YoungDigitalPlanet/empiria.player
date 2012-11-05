package eu.ydp.empiria.player.client.module.sourcelist.structure;

import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;

import eu.ydp.empiria.player.client.jaxb.JAXBParserImpl;

public class SourceListJAXBParserMock implements SourceListJAXBParser {
	public static final String XML = "<sourceList id=\"dummy2\" moveElements=\"true\" shuffle=\"true\">"+
			"<simpleSourceListItem alt=\"psa\">psa</simpleSourceListItem>"+
			"<simpleSourceListItem alt=\"kota\">kota</simpleSourceListItem>"+
			"<simpleSourceListItem alt=\"tygrysa\">tygrysa</simpleSourceListItem>"+
						"</sourceList>";
	@Override
	public JAXBParser<SourceListBean> create() {
		return new JAXBParserImpl<SourceListBean>(SourceListJAXBParser.class);
	}

}
