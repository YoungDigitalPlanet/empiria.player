package eu.ydp.empiria.player.client.view.player.styles;

import com.google.common.base.Optional;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.NodeList;
import eu.ydp.empiria.player.client.controller.data.DataSourceManager;
import eu.ydp.empiria.player.client.util.events.internal.bus.EventsBus;
import eu.ydp.empiria.player.client.util.events.internal.player.PlayerEvent;
import eu.ydp.empiria.player.client.util.file.xml.XmlData;
import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ItemStylesContainerTest {

    private static final String IDENTIFIER_ATTRIBUTE = "identifier";
    private static final String CLASS_ATTRIBUTE = "class";

    @InjectMocks
    private ItemStylesContainer testObj;
    @Mock
    private EventsBus eventsBus;
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private DataSourceManager dataManager;
    @Mock
    private PlayerEvent playerEvent;
    @Mock
    private NodeList itemsList;
    @Mock
    private Element element;

    @Before
    public void init() {
        String assessmentItemTag = "assessmentItemRef";
        XmlData xmlData = mock(XmlData.class, RETURNS_DEEP_STUBS);
        when(dataManager.getAssessmentData().getData()).thenReturn(xmlData);
        when(xmlData.getDocument().getElementsByTagName(assessmentItemTag)).thenReturn(itemsList);
        when(itemsList.getLength()).thenReturn(1);
        when(itemsList.item(0)).thenReturn(element);
    }

    @Test
    public void shouldContainsStyleOfItem_whenStyleIsNotNullOrEmpty() {
        // given
        String identifier = "id";
        String style = "style";
        when(element.getAttribute(IDENTIFIER_ATTRIBUTE)).thenReturn(identifier);
        when(element.getAttribute(CLASS_ATTRIBUTE)).thenReturn(style);

        // wehn
        testObj.onPlayerEvent(playerEvent);
        Optional<String> result = testObj.getStyle(identifier);

        // then
        assertThat(result.isPresent()).isTrue();
        assertThat(result.get()).isEqualTo(style);
    }

    @Test
    public void shouldNotContainsStyleOfItem_whenStyleIsNull() {
        // given
        String identifier = "id";
        String style = null;
        when(element.getAttribute(IDENTIFIER_ATTRIBUTE)).thenReturn(identifier);
        when(element.getAttribute(CLASS_ATTRIBUTE)).thenReturn(style);

        // wehn
        testObj.onPlayerEvent(playerEvent);
        Optional<String> result = testObj.getStyle(identifier);

        // then
        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void shouldNotContainsStyleOfItem_whenStyleIsEmpty() {
        // given
        String identifier = "id";
        String style = "";
        when(element.getAttribute(IDENTIFIER_ATTRIBUTE)).thenReturn(identifier);
        when(element.getAttribute(CLASS_ATTRIBUTE)).thenReturn(style);

        // wehn
        testObj.onPlayerEvent(playerEvent);
        Optional<String> result = testObj.getStyle(identifier);

        // then
        assertThat(result.isPresent()).isFalse();
    }
}