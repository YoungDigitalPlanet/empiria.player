package eu.ydp.empiria.player.client.module.slideshow.presenter;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.module.slideshow.view.pager.button.SlideshowPagerButtonView;
import eu.ydp.gwtutil.client.event.factory.Command;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class SlideshowPagerButtonPresenterTest {

    @InjectMocks
    private SlideshowPagerButtonPresenter testObj;
    @Mock
    SlideshowPagerButtonView view;

    @Test
    public void shouldDelegateCommand() {
        // given
        Command command = mock(Command.class);

        // when
        testObj.setClickCommand(command);

        // then
        verify(view).setOnClickCommand(command);
    }

    @Test
    public void shouldDelegateActivatePagerButton() {
        // given

        // when
        testObj.activatePagerButton();

        // then
        verify(view).activatePagerButton();
    }

    @Test
    public void shouldDelegateDeactivatePagerButton() {
        // given

        // when
        testObj.deactivatePagerButton();

        // then
        verify(view).deactivatePagerButton();
    }

    @Test
    public void shouldReturnViewAsWidget() {
        // given
        Widget widget = mock(Widget.class);
        when(view.asWidget()).thenReturn(widget);

        // when
        Widget result = testObj.getView();

        // then
        assertThat(result).isEqualTo(widget);
    }

}
