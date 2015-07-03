package eu.ydp.empiria.player.client.module.textentry;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.DropEvent;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.TextBox;
import eu.ydp.empiria.player.client.module.gap.GapBase.PresenterHandler;
import eu.ydp.empiria.player.client.util.dom.drag.DroppableObject;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TextBoxChangeHandlerJUnitTest {

    @Mock
    private DroppableObject<TextBox> droppableObject;
    @Mock
    private TextBox textBox;
    @Mock
    private PresenterHandler presenterHandler;

    @InjectMocks
    private TextBoxChangeHandler testObj;

    @BeforeClass
    public static void disarm() {
        GWTMockUtilities.disarm();
    }

    @AfterClass
    public static void rearm() {
        GWTMockUtilities.restore();
    }

    @Before
    public void before() {
        when(droppableObject.getOriginalWidget()).thenReturn(textBox);
    }

    @Test
    public void shouldDoNothingWhenPresenterIsNotGiven() {
        // when
        testObj.bind(droppableObject, null);

        // then
        verifyZeroInteractions(droppableObject);
    }

    @Test
    public void shouldRegisterHandlersWhenPresenterIsGiven() {
        // when
        testObj.bind(droppableObject, presenterHandler);

        // then
        verify(textBox).addBlurHandler(Matchers.eq(testObj));
        verify(droppableObject).addDropHandler(Matchers.eq(testObj));
    }

    @Test
    public void shouldHandleDropAsOnBlur() {
        // given
        DropEvent event = mock(DropEvent.class);

        // when
        testObj.bind(droppableObject, presenterHandler);
        testObj.onDrop(event);

        // then
        verify(presenterHandler).onBlur(Matchers.any(BlurEvent.class));
    }

    @Test
    public void shouldHandleOnBlurWithPresenter() {
        // given
        BlurEvent blurEvent = mock(BlurEvent.class);

        // when
        testObj.bind(droppableObject, presenterHandler);
        testObj.onBlur(blurEvent);

        // then
        verify(presenterHandler).onBlur(Matchers.eq(blurEvent));
        verifyNoMoreInteractions(presenterHandler);
    }

}
