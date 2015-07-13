package eu.ydp.empiria.player.client.controller.multiview.swipe;

import com.google.common.collect.Maps;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import eu.ydp.empiria.player.client.resources.EmpiriaStyleNameConstants;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.gwtutil.client.xml.XMLParser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class SwipeTypeProviderTest {

    @Mock
    private StyleSocket styleSocket;
    @Mock
    private XMLParser parser;
    @Mock
    private Document documentElement;
    @Mock
    private Element firstChild;

    @InjectMocks
    private SwipeTypeProvider instance;
    private Map<String, String> styleMap;

    @Before
    public void before() {
        doReturn(firstChild).when(documentElement).getFirstChild();
        doReturn(documentElement).when(parser).parse(anyString());
        doReturn(firstChild).when(documentElement).getDocumentElement();
        styleMap = Maps.newHashMap();
        doReturn(styleMap).when(styleSocket).getStyles(any(Element.class));
    }

    @Test
    public void getSwipeDisabled() throws Exception {
        styleMap.put(EmpiriaStyleNameConstants.EMPIRIA_SWIPE_DISABLE_ANIMATION, "true");
        assertThat(instance.get()).isEqualTo(SwipeType.DISABLED);
    }

    @Test
    public void getSwipeEnabled() throws Exception {
        assertThat(instance.get()).isEqualTo(SwipeType.DEFAULT);
    }

}
