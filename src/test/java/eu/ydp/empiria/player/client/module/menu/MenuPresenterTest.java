package eu.ydp.empiria.player.client.module.menu;

import com.google.gwt.dom.client.NativeEvent;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.module.menu.view.MenuStyleNameConstants;
import eu.ydp.empiria.player.client.module.menu.view.MenuView;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;

import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class MenuPresenterTest {

    @InjectMocks
    private MenuPresenter testObj;
    @Mock
    private MenuView view;
    @Mock
    private MenuStyleNameConstants styleNameConstants;
    @Mock
    private UserInteractionHandlerFactory userInteractionHandlerFactory;
    @Mock
    private NativeEvent nativeEvent;
    @Captor
    private ArgumentCaptor<Command> commandCaptor;
    private String qpMenuHidden = "qp-menu-hidden";

    @Before
    public void init(){
        when(styleNameConstants.QP_MENU_HIDDEN()).thenReturn(qpMenuHidden);
        verify(userInteractionHandlerFactory).createUserClickHandler(commandCaptor.capture());
    }

    @Test
    public void shouldShowMenu_onFirstClick(){
        // given
        Command command = commandCaptor.getValue();

        // when
        command.execute(nativeEvent);

        // then
        verify(view).removeStyleName(qpMenuHidden);
    }

    @Test
    public void shouldHideMenu_onSecondClick(){
        // given
        Command command = commandCaptor.getValue();

        // when
        command.execute(nativeEvent);
        command.execute(nativeEvent);

        // then
        verify(view).addStyleName(qpMenuHidden);
    }
}