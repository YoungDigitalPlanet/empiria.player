package eu.ydp.empiria.player.client.module.test.reset;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockitoTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class TestResetButtonModuleTest {

    private TestResetButtonModule testObj;

    @Mock
    private TestResetButtonPresenter presenter;

    @Before
    public void setUp() {
        testObj = new TestResetButtonModule(presenter);
    }

    @Test
    public void shouldGetView() {
        // given
        Widget view = mock(Widget.class);

        when(presenter.getView()).thenReturn(view);

        // when
        Widget result = testObj.getView();

        // then
        assertThat(result).isEqualTo(view);
    }

    @Test
    public void shouldLock() {
        // when
        testObj.lock(true);

        // then
        verify(presenter).lock();
    }

    @Test
    public void shouldUnlock() {
        // when
        testObj.lock(false);

        // then
        verify(presenter).unlock();
    }

    @Test
    public void shouldEnablePreviewMode() {
        // when
        testObj.enablePreviewMode();

        // then
        verify(presenter).enablePreviewMode();
    }
}
