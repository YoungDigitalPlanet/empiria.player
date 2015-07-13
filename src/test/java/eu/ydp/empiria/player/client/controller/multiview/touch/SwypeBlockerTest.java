package eu.ydp.empiria.player.client.controller.multiview.touch;

import com.google.gwt.event.logical.shared.CloseEvent;
import eu.ydp.empiria.player.client.controller.multiview.touch.SwypeBlocker.OpenCloseListener;
import eu.ydp.gwtutil.client.components.exlistbox.ExListBoxOpenCloseListener;
import eu.ydp.gwtutil.client.components.exlistbox.IsExListBox;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class SwypeBlockerTest {
    private SwypeBlocker swypeBlocker;

    @Mock
    private TouchController touchController;
    @Mock
    private IsExListBox exListBox;
    private OpenCloseListener openCloseListener;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        swypeBlocker = new SwypeBlocker(touchController);
        openCloseListener = swypeBlocker.new OpenCloseListener();
    }

    @After
    public void after() {
        verifyNoMoreInteractions(touchController);
    }

    @Test
    public void addBlockOnOpenCloseHandlerTest() {

        swypeBlocker.addBlockOnOpenCloseHandler(exListBox);

        verify(exListBox).addOpenCloseListener(any(ExListBoxOpenCloseListener.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void openCloseListener_onClose() {
        openCloseListener.onClose(mock(CloseEvent.class));
        verify(touchController).setSwypeLock(false);
    }

    @Test
    public void openCloseListener_onOpen() {
        openCloseListener.onOpen();
        verify(touchController).setSwypeLock(true);
    }
}
