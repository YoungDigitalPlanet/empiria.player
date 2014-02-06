package eu.ydp.empiria.player.client.module.connection.presenter;

import static org.mockito.Mockito.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mockito;

import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

import eu.ydp.empiria.player.client.module.connection.exception.CssStyleException;
import eu.ydp.gwtutil.junit.runners.ExMockRunner;
import eu.ydp.gwtutil.junit.runners.PrepareForTest;

@RunWith(ExMockRunner.class)
@PrepareForTest(Element.class)
@SuppressWarnings("PMD")
public class ConnectionStyleCheckerHandlerJUnitTest {

	private IsWidget view;
	private ConnectionStyleChecker connectionStyleChecker;
	private ConnectionStyleCheckerHandler instance;
	private Element element;

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
