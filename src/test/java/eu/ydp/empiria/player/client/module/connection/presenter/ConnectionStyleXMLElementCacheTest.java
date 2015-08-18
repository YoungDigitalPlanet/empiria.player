package eu.ydp.empiria.player.client.module.connection.presenter;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleConnectType;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.gwtutil.client.xml.XMLParser;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JUnitParamsRunner.class)
public class ConnectionStyleXMLElementCacheTest {

    private ConnectionStyleXMLElementCache testObj;
    private StyleNameConstants styleNames;
    private final Map<MultiplePairModuleConnectType, String> styles = new HashMap<>();

    @Before
    public void before() {
        XMLParser parser = mock(XMLParser.class);
        styleNames = mock(StyleNameConstants.class);
        when(styleNames.QP_CONNECTION_WRONG()).thenReturn("qp-connection-wrong");
        when(styleNames.QP_CONNECTION_CORRECT()).thenReturn("qp-connection-correct");
        when(styleNames.QP_CONNECTION_DISABLED()).thenReturn("qp-connection-disabled");
        when(styleNames.QP_CONNECTION_NORMAL()).thenReturn("qp-connection-normal");

        styles.put(MultiplePairModuleConnectType.WRONG, styleNames.QP_CONNECTION_WRONG());
        styles.put(MultiplePairModuleConnectType.CORRECT, styleNames.QP_CONNECTION_CORRECT());
        styles.put(MultiplePairModuleConnectType.NONE, styleNames.QP_CONNECTION_DISABLED());
        styles.put(MultiplePairModuleConnectType.NORMAL, styleNames.QP_CONNECTION_NORMAL());
        when(parser.parse(Matchers.anyString())).then(new Answer<Document>() {
            @Override
            public Document answer(InvocationOnMock invocation) throws Throwable {
                return eu.ydp.gwtutil.xml.XMLParser.parse((String) invocation.getArguments()[0]);
            }
        });
        testObj = new ConnectionStyleXMLElementCache(styleNames, parser);
    }

    @Test
    @Parameters(method = "multiplePairModuleConnectTypeValues")
    public void testGet(MultiplePairModuleConnectType type) {
        Element element = testObj.getOrCreateAndPut(type);
        assertEquals(styles.get(type), element.getAttribute("class"));
        assertEquals("connection", element.getNodeName());
    }

    MultiplePairModuleConnectType[] multiplePairModuleConnectTypeValues() {
        return MultiplePairModuleConnectType.values();
    }

}
