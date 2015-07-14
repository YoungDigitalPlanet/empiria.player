package eu.ydp.empiria.player.client.module.tutor.actions.popup;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Provider;
import eu.ydp.empiria.player.client.resources.StyleNameConstants;
import eu.ydp.gwtutil.client.event.factory.Command;
import eu.ydp.gwtutil.client.event.factory.UserInteractionHandlerFactory;
import eu.ydp.gwtutil.client.proxy.RootPanelDelegate;
import eu.ydp.gwtutil.junit.mock.GWTConstantsMock;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TutorPopupViewImplTest {
    @Mock
    private Provider<TutorPopupViewPersonaView> personasViewProvider;
    @Mock
    private TutorPopupViewWidget popupViewWidget;
    @Mock
    private RootPanelDelegate rootPanelDelegate;
    @Mock
    private final StyleNameConstants styleNameConstants = GWTConstantsMock.mockAllStringMethods(mock(StyleNameConstants.class), StyleNameConstants.class);
    @Mock
    private RootPanel rootPanel;
    @Mock
    private UserInteractionHandlerFactory userInteractionHandlerFactory;
    @Captor
    private ArgumentCaptor<Command> commandCaptor;

    @InjectMocks
    private TutorPopupViewImpl instance;

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
        TutorPopupViewPersonaView personaView = mock(TutorPopupViewPersonaView.class);
        doReturn(personaView).when(personasViewProvider).get();
        doReturn(rootPanel).when(rootPanelDelegate).getRootPanel();
    }

    @Test
    public void addPersona() throws Exception {
        String avatarUrl = "http://dummy.com";
        PersonaViewDto personaViewDto = new PersonaViewDto(0, avatarUrl);
        instance.addPersona(personaViewDto);
        verify(personasViewProvider.get()).setAvatarUrl(eq(avatarUrl));
        verify(popupViewWidget).addWidget(eq(personasViewProvider.get()));
    }

    @Test(expected = NullPointerException.class)
    public void setSelectedWithoutInitialization() throws Exception {
        instance.setSelected(3);
    }

    @Test
    public void setSelected() {
        String avatarUrl = "http://dummy.com";
        PersonaViewDto personaViewDto = new PersonaViewDto(0, avatarUrl);
        instance.addPersona(personaViewDto);
        Widget widget = mock(Widget.class);
        doReturn(widget).when(popupViewWidget).getWidget(anyInt());
        instance.setSelected(0);
        verify(widget).setStyleName(eq(styleNameConstants.QP_TUTOR_POPUP_SELECTED_PERSONA()));

    }

    @Test
    public void show() throws Exception {
        instance.show();
        verify(rootPanel).add(eq(popupViewWidget));
    }

    @Test
    public void hide() throws Exception {
        instance.hide();
        verify(rootPanel).remove(eq(popupViewWidget));
    }

    @Test
    public void addClickHandlerToPersona() throws Exception {
        String avatarUrl = "http://dummy.com";
        PersonaViewDto personaViewDto = new PersonaViewDto(0, avatarUrl);
        instance.addPersona(personaViewDto);
        Command clickCommand = mock(Command.class);
        Widget widget = mock(Widget.class);
        doReturn(widget).when(popupViewWidget).getWidget(eq(0));

        // test
        instance.addClickHandlerToPersona(clickCommand, 0);
        verify(userInteractionHandlerFactory).applyUserClickHandler(eq(clickCommand), eq(widget));
    }

    @Test
    public void postConstruct() throws Exception {
        instance.postConstruct();
        verify(userInteractionHandlerFactory).applyUserClickHandler(commandCaptor.capture(), eq(popupViewWidget));
        commandCaptor.getValue().execute(null);
        verify(rootPanel).remove(eq(popupViewWidget));
    }

}
