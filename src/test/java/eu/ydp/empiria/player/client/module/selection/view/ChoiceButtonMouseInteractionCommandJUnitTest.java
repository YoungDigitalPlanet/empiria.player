package eu.ydp.empiria.player.client.module.selection.view;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.junit.GWTMockUtilities;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@SuppressWarnings("PMD")
public class ChoiceButtonMouseInteractionCommandJUnitTest {

    private final SelectionChoiceButton button = mock(SelectionChoiceButton.class);
    private ChoiceButtonMouseInteractionCommand instance;

    @BeforeClass
    public static void disarm() {
        GWTMockUtilities.disarm();
    }

    @AfterClass
    public static void rearm() {
        GWTMockUtilities.restore();
    }

    @Test
    public void testExecuteMouseOver() {
        instance = new ChoiceButtonMouseInteractionCommand(button, true);
        instance.execute(mock(NativeEvent.class));
        verify(button).setMouseOver(true);
    }

    @Test
    public void testExecuteMouseNotOver() {
        instance = new ChoiceButtonMouseInteractionCommand(button, false);
        instance.execute(mock(NativeEvent.class));
        verify(button).setMouseOver(false);
    }

}
