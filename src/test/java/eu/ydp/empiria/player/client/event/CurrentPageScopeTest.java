package eu.ydp.empiria.player.client.event;

import eu.ydp.empiria.player.client.controller.Page;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CurrentPageScopeTest {

    @Test
    public void scopeCompareTest() {
        Page page = mock(Page.class);
        when(page.getCurrentPageNumber()).thenReturn(1);

        CurrentPageScope currentPageScope = new CurrentPageScope(page);
        CurrentPageScope pageScope = new CurrentPageScope(1);

        assertTrue(currentPageScope.equals(pageScope));
        assertTrue(pageScope.equals(currentPageScope));
        assertEquals(currentPageScope.compareTo(pageScope), 0);

        when(page.getCurrentPageNumber()).thenReturn(2);
        currentPageScope = new CurrentPageScope(page);
        assertEquals(pageScope.compareTo(currentPageScope), -1);
        assertEquals(currentPageScope.compareTo(pageScope), 1);
    }
}
