package eu.ydp.empiria.player.client.event;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;

import eu.ydp.empiria.player.client.controller.Page;
import eu.ydp.empiria.player.client.util.events.scope.CurrentPageScope;
import eu.ydp.empiria.player.client.util.events.scope.EventScope;
import eu.ydp.empiria.player.client.util.events.scope.PageScope;

/**
 * Testy dla {@link EventScope}
 * 
 */
@SuppressWarnings("PMD")
public class EventScopeJUnitTest {

	@Test
	public void scopeCompareTest() {
		Page page = mock(Page.class);
		when(page.getCurrentPageNumber()).thenReturn(1);
		CurrentPageScope currentPageScope = new CurrentPageScope(page);
		PageScope pageScope = new PageScope(1);
		assertTrue("CurrentPageScope not equals PageScope", currentPageScope.equals(pageScope));
		assertTrue("CurrentPageScope not equals PageScope", pageScope.equals(currentPageScope));
		assertEquals("CurrentPageScope not equals PageScope", currentPageScope.compareTo(pageScope), 0);
		when(page.getCurrentPageNumber()).thenReturn(2);
		currentPageScope = new CurrentPageScope(page);
		assertEquals("PageScope < CurrentPageScope", pageScope.compareTo(currentPageScope), -1);
		assertEquals("CurrentPageScope  > PageScope", currentPageScope.compareTo(pageScope), 1);
	}
}
