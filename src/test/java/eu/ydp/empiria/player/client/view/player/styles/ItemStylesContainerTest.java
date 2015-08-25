package eu.ydp.empiria.player.client.view.player.styles;

import com.google.common.base.Optional;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import eu.ydp.empiria.player.client.controller.data.AssessmentDataSourceManager;
import eu.ydp.empiria.player.client.controller.data.DataSourceManager;
import eu.ydp.empiria.player.client.resources.PageStyleNameConstants;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ItemStylesContainerTest {

    @InjectMocks
    private ItemStylesContainer testObj;
    @Mock
    private EventsBus eventsBus;
    @Mock
    private AssessmentDataSourceManager dataSourceManager;
    @Mock
    private PageStyleNameConstants pageStyleNameConstants;
    @Mock
    private PlayerEvent playerEvent;

    private Map<String, String> pageItToStyle = new HashMap<>();
    private String identifier = "id";

    @Before
    public void init() {
        when(pageStyleNameConstants.QP_PAGE_TEMPLATE()).thenReturn("templ");
        when(dataSourceManager.getPageIdToStyleMap()).thenReturn(pageItToStyle);
    }

    @Test
    public void shouldContainStyleOfItem_whenStyleIsNotNullOrEmpty() {
        // given
        String style = "style";
        String expectedStyle = "templ-style";
        pageItToStyle.put(identifier, style);

        // wehn
        testObj.onPlayerEvent(playerEvent);
        Optional<String> result = testObj.getStyle(identifier);

        // then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo(expectedStyle);
    }

    @Test
    public void shouldNotContainStyleOfItem_whenStyleIsNull() {
        // given
        String style = null;
        pageItToStyle.put(identifier, style);

        // wehn
        testObj.onPlayerEvent(playerEvent);
        Optional<String> result = testObj.getStyle(identifier);

        // then
        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void shouldNotContainStyleOfItem_whenStyleIsEmpty() {
        // given
        String style = "";
        pageItToStyle.put(identifier, style);

        // wehn
        testObj.onPlayerEvent(playerEvent);
        Optional<String> result = testObj.getStyle(identifier);

        // then
        assertThat(result.isPresent()).isFalse();
    }
}