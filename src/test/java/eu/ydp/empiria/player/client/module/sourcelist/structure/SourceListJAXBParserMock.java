package eu.ydp.empiria.player.client.module.sourcelist.structure;

import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;
import eu.ydp.empiria.player.client.jaxb.JAXBParserImpl;

@SuppressWarnings("PMDs")
public class SourceListJAXBParserMock implements SourceListJAXBParser {
    public static final String XML = "<sourceList id=\"dummy2\" moveElements=\"true\" shuffle=\"true\" sourcelistId=\"id1\">"
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

    public static final String SOURCELIST_WITH_MATH = "<sourceList id=\"dummy2_1\" moveElements=\"true\" shuffle=\"false\" sourcelistId=\"defaultSourceListId\">\n" +
            "\t\t\t\t<simpleSourceListItem alt=\"1 psa\"><inlineMathJax><script type=\"math/mml\">\n" +
            "        <math><mrow><mi class=\"identifier\" mathvariant=\"italic\">E</mi><mo>=</mo><mi class=\"identifiers\" mathvariant=\"italic\">mc2</mi></mrow></math>\n" +
            "    </script></inlineMathJax> <inlineMathJax class=\"chem\"><script type=\"math/mml\">\n" +
            "        <math><mrow><ms lquote=\"\" mathvariant=\"normal\" rquote=\"\">E</ms><mo>=</mo><ms lquote=\"\" mathvariant=\"normal\" rquote=\"\">mc2</ms></mrow></math>\n" +
            "    </script></inlineMathJax></simpleSourceListItem>\n" +
            "\t\t\t\t<simpleSourceListItem alt=\"2 kota\">2 kota</simpleSourceListItem>\n" +
            "\t\t\t\t<simpleSourceListItem alt=\"3 tygrysa\">3 tygrysa</simpleSourceListItem>\n" +
            "\t</sourceList>";

    public static final String SOURCELIST_WITH_FORMATTED_TEXT = "<sourceList id=\"dummy2_1\" moveElements=\"true\" shuffle=\"false\" sourcelistId=\"defaultSourceListId\">\n" +
            "\t\t\t\t<simpleSourceListItem alt=\"2 kota\">2 <i>kota</i></simpleSourceListItem>\n" +
            "\t\t\t\t<simpleSourceListItem alt=\"3 tygrysa\">3 <b>tygrysa</b></simpleSourceListItem>\n" +
            "\t</sourceList>";

    @Override
    public JAXBParser<SourceListBean> create() {
        return new JAXBParserImpl<>(SourceListJAXBParser.class);
    }

}
