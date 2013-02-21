package eu.ydp.empiria.player.client.module.connection.presenter;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.dom.client.Style;
import com.google.gwt.junit.GWTMockUtilities;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.xml.client.Element;
import com.google.inject.Binder;
import com.google.inject.Module;

import eu.ydp.empiria.player.client.AbstractTestBaseWithoutAutoInjectorInit;
import eu.ydp.empiria.player.client.GuiceModuleConfiguration;
import eu.ydp.empiria.player.client.gin.factory.ConnectionModuleFactory;
import eu.ydp.empiria.player.client.module.connection.exception.CssStyleException;
import eu.ydp.empiria.player.client.style.StyleSocket;
import eu.ydp.empiria.player.client.util.style.CssHelper;
import eu.ydp.gwtutil.junit.runners.ExMockRunner;
import eu.ydp.gwtutil.junit.runners.PrepareForTest;

@SuppressWarnings("PMD")
@RunWith(ExMockRunner.class)
@PrepareForTest({ NodeList.class, Node.class, Style.class, CssHelper.class })
public class ConnectionStyleCheckerJUnitTest extends AbstractTestBaseWithoutAutoInjectorInit {

	private class CustomGinModule implements Module {
		@Override
		public void configure(Binder binder) {
			binder.bind(CssHelper.class).toInstance(cssHelper);
		}
	}

	private final CssHelper cssHelper = spy(new CssHelper());
	private StyleSocket styleSocket;
	private ConnectionStyleChecker instance;
	private Map<String, String> styles = new HashMap<String, String>();

	@Before
	public void before() {
		GuiceModuleConfiguration configuration = new GuiceModuleConfiguration();
		setUpAndOverrideMainModule(configuration, new CustomGinModule());
		styles.clear();
		styleSocket = mock(StyleSocket.class);
		doReturn(styles).when(styleSocket).getStyles(Mockito.any(Element.class));
		instance = injector.getInstance(ConnectionModuleFactory.class).getConnectionStyleChacker(styleSocket);
		injector.getMembersInjector(ConnectionStyleChecker.class).injectMembers(instance);

	}

	@BeforeClass
	public static void disarm() {
		GWTMockUtilities.disarm();
	}

	@AfterClass
	public static void rearm() {
		GWTMockUtilities.restore();
	}

	@Test
	public void testIsStylesAreCorrect() {
		styles = new HashMap<String, String>();
		styles.put("display", "inline");
		styles.put("width", "20px");
		instance.areStylesCorrectThrowsExceptionWhenNot(null);
	}

	@Test(expected = CssStyleException.class)
	public void testIsStylesAreNotCorrect() {
		styles.put("display", "table-cell");
		styles.put("width", "20px");

		instance.areStylesCorrectThrowsExceptionWhenNot(null);
	}

	@Test
	public void testIsStylesAreCorrect_NativeStyles() {
		instance = spy(instance);
		com.google.gwt.user.client.Element element = mock(com.google.gwt.user.client.Element.class);
		Widget widget = createWidgetMock(element);
		IsWidget isWidget = createIsWidgetMock(widget);
		createStyleMock("inline");
		createNodeListMock(element);

		instance.areStylesCorrectThrowsExceptionWhenNot(isWidget);
	}

	@Test(expected = CssStyleException.class)
	public void testIsStylesAreNotCorrect_NativeStyles() {
		instance = spy(instance);
		com.google.gwt.user.client.Element element = mock(com.google.gwt.user.client.Element.class);
		Widget widget = createWidgetMock(element);
		IsWidget isWidget = createIsWidgetMock(widget);
		createStyleMock("table-cell");
		createNodeListMock(element);

		instance.areStylesCorrectThrowsExceptionWhenNot(isWidget);
	}

	private Widget createWidgetMock(com.google.gwt.user.client.Element element) {
		Widget widget = mock(Widget.class);
		doReturn(element).when(widget).getElement();
		return widget;
	}

	private IsWidget createIsWidgetMock(Widget widget) {
		IsWidget isWidget = mock(IsWidget.class);
		doReturn(widget).when(isWidget).asWidget();
		return isWidget;
	}

	private void createStyleMock(String displayStyle) {
		Style style = mock(Style.class);
		doReturn(displayStyle).when(style).getProperty(Mockito.anyString());
		doReturn(style).when(cssHelper).getComputedStyle(Mockito.any(JavaScriptObject.class));
	}

	private void createNodeListMock(com.google.gwt.user.client.Element element) {
		NodeList<?> nodeList = mock(NodeList.class);
		doReturn(0).when(nodeList).getLength();
		doReturn(nodeList).when(element).getChildNodes();
	}

}
