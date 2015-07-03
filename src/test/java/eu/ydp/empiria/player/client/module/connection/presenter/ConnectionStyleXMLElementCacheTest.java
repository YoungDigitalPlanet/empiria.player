package eu.ydp.empiria.player.client.module.connection.presenter;

import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.module.components.multiplepair.MultiplePairModuleConnectType;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.gwtutil.client.xml.XMLParser;
import eu.ydp.gwtutil.junit.mock.GWTConstantsMock;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConnectionStyleXMLElementCacheTest {

    private ConnectionStyleXMLElementCache cache;
    private StyleNameConstants styleNames;
    private final Map<MultiplePairModuleConnectType, String> styles = new HashMap<MultiplePairModuleConnectType, String>();

    @Before
    public void before() {
        XMLParser parser = mock(XMLParser.class);
        styleNames = GWTConstantsMock.mockAllStringMethods(mock(StyleNameConstants.class), StyleNameConstants.class);
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
        cache = new ConnectionStyleXMLElementCache(styleNames, parser);
    }

    @Test
    public void testGet() {
        for (MultiplePairModuleConnectType type : MultiplePairModuleConnectType.values()) {
            Element element = cache.getOrCreateAndPut(type);
            assertEquals(styles.get(type), element.getAttribute("class"));
            assertEquals("connection", element.getNodeName());
        }
    }

}
