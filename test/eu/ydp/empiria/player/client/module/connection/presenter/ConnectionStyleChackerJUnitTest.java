package eu.ydp.empiria.player.client.module.connection.presenter;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.gwt.xml.client.Element;

import eu.ydp.empiria.player.client.AbstractTestBase;
import eu.ydp.empiria.player.client.gin.factory.ConnectionModuleFactory;
import eu.ydp.empiria.player.client.module.connection.exception.CssStyleException;
import eu.ydp.empiria.player.client.style.StyleSocket;

@SuppressWarnings("PMD")
public class ConnectionStyleChackerJUnitTest extends AbstractTestBase {


	private StyleSocket styleSocket;
	private ConnectionStyleChacker instance ;
	private Map<String, String> styles = new HashMap<String, String>();

	@Before
	public void before() {
		styles.clear();
		styleSocket = mock(StyleSocket.class);
		doReturn(styles).when(styleSocket).getStyles(Mockito.any(Element.class));
		instance = injector.getInstance(ConnectionModuleFactory.class).getConnectionStyleChacker(styleSocket);
		injector.getMembersInjector(ConnectionStyleChacker.class).injectMembers(instance);
	}

	@Test
	public void testIsStylesAreCorrect() {
		styles = new HashMap<String, String>();
		styles.put("display", "inline");
		styles.put("width", "20px");
		instance.areStylesCorrectThrowsExceptionWhenNot();
	}

	@Test(expected=CssStyleException.class)
	public void testIsStylesAreNotCorrect() {
		styles.put("display", "table-cell");
		styles.put("width", "20px");

		instance.areStylesCorrectThrowsExceptionWhenNot();

	}

}
