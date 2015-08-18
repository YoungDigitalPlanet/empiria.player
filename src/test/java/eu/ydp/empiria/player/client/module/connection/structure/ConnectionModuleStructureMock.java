package eu.ydp.empiria.player.client.module.connection.structure;

import static org.mockito.Mockito.*;

import com.google.gwt.xml.client.Document;
import com.peterfranza.gwt.jaxb.client.parser.JAXBParser;
import eu.ydp.empiria.player.client.jaxb.JAXBParserImpl;
import eu.ydp.gwtutil.xml.XMLParser;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class ConnectionModuleStructureMock extends ConnectionModuleStructure {

    public final static String CONNECTION_XML = "<matchInteraction id=\"dummy1\" responseIdentifier=\"CONNECTION_RESPONSE_1\" shuffle=\"true\">"
            + "<simpleMatchSet>" + "<simpleAssociableChoice fixed=\"false\" identifier=\"CONNECTION_RESPONSE_1_0\" matchMax=\"2\">"
            + "a	</simpleAssociableChoice>" + "<simpleAssociableChoice fixed=\"false\" identifier=\"CONNECTION_RESPONSE_1_3\" matchMax=\"2\">"
            + "b	</simpleAssociableChoice>" + "</simpleMatchSet>" + "<simpleMatchSet>"
            + "<simpleAssociableChoice fixed=\"false\" identifier=\"CONNECTION_RESPONSE_1_4\" matchMax=\"2\">" + "c	</simpleAssociableChoice>"
            + "<simpleAssociableChoice fixed=\"false\" identifier=\"CONNECTION_RESPONSE_1_1\" matchMax=\"2\">" + "d	</simpleAssociableChoice>"
            + "</simpleMatchSet>" + "</matchInteraction>";

    @Override
    public ConnectionModuleJAXBParser getParserFactory() {
        return mockParserFactory();
    }

    private ConnectionModuleJAXBParser mockParserFactory() {
        return new ConnectionModuleJAXBParser() {
            @Override
            public JAXBParser<MatchInteractionBean> create() {
                return new JAXBParserImpl<MatchInteractionBean>(ConnectionModuleJAXBParser.class);
            }
        };
    }

    @Override
    protected eu.ydp.gwtutil.client.xml.XMLParser getXMLParser() {
        eu.ydp.gwtutil.client.xml.XMLParser xmlParser = mock(eu.ydp.gwtutil.client.xml.XMLParser.class);
        when(xmlParser.parse(Matchers.anyString())).thenAnswer(new Answer<Document>() {
            @Override
            public Document answer(InvocationOnMock invocation) throws Throwable {
                return XMLParser.parse((String) invocation.getArguments()[0]);
            }
        });
        return xmlParser;
    }

}
