package eu.ydp.empiria.player.client.gin.scopes.page;

import com.google.inject.Inject;
import eu.ydp.empiria.player.client.gin.factory.PageScopeFactory;
import eu.ydp.empiria.player.client.util.events.internal.scope.CurrentPageScope;
import eu.ydp.gwtutil.client.gin.scopes.AbstractCustomScope;
import eu.ydp.gwtutil.client.gin.scopes.CurrentScopeProvider;

public class CurrentPageScopeProvider implements CurrentScopeProvider {

    private PageScopeFactory pageScopeFactory;

    @Inject
    public CurrentPageScopeProvider(PageScopeFactory pageScopeFactory) {
        this.pageScopeFactory = pageScopeFactory;
    }

    @Override
    public AbstractCustomScope getCurrentScope() {
        CurrentPageScope currentPageScope = pageScopeFactory.getCurrentPageScope();
        int pageIndex = currentPageScope.getPageIndex();
        return new PageScope(pageIndex);
    }
}
