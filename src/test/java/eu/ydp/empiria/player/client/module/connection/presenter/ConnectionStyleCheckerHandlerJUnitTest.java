package eu.ydp.empiria.player.client.module.connection.presenter;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwtmockito.GwtMockitoTestRunner;
import eu.ydp.empiria.player.client.module.connection.exception.CssStyleException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;

import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class ConnectionStyleCheckerHandlerJUnitTest {

    private IsWidget view;
    private ConnectionStyleChecker connectionStyleChecker;
    private ConnectionStyleCheckerHandler instance;
    private Element element;

    @Before
    public void before() {
        view = mock(IsWidget.class);
        Widget widget = cretateWidgetAndElementMock();
        doReturn(widget).when(view).asWidget();

        connectionStyleChecker = mock(ConnectionStyleChecker.class);
        instance = new ConnectionStyleCheckerHandler(view, connectionStyleChecker);
    }

    private Widget cretateWidgetAndElementMock() {
        Widget widget = mock(Widget.class);
        element = mock(Element.class);
        doReturn(element).when(widget).getElement();
        return widget;
    }

    @Test
    public void testOnAttachOrDetachCorrectStyles() {
        doNothing().when(connectionStyleChecker).areStylesCorrectThrowsExceptionWhenNot(Matchers.any(IsWidget.class));
        instance.onAttachOrDetach(null);
        verifyZeroInteractions(view);

    }

    @Test
    public void testOnAttachOrDetachNotCorrectStyles() {
        String message = "error";
        doThrow(new CssStyleException(message)).when(connectionStyleChecker).areStylesCorrectThrowsExceptionWhenNot(Matchers.any(IsWidget.class));
        instance.onAttachOrDetach(null);
        verify(element).setInnerText(Matchers.eq(message));
    }

}
